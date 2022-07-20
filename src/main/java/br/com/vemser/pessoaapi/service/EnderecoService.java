package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;

import br.com.vemser.pessoaapi.entity.EnderecoEntity;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;


    public List<EnderecoDTO> list() {
        return enderecoRepository.findAll().stream()
                .map(this::enderecoToEnderecoDTO)
                .toList();
    }

    public EnderecoDTO create(EnderecoCreateDTO enderecoCreateDTO, Integer idPessoa) throws RegraDeNegocioException {
        log.info("Adicionando endereco...");

        PessoaEntity pessoa = pessoaService.listByIdPessoa(idPessoa);
        Set<PessoaEntity> pessoaEntities = new HashSet<>();
        pessoaEntities.add(pessoa);
        EnderecoEntity enderecoEntity = enderecoCreateDtoToEndereco(enderecoCreateDTO);
        enderecoEntity.setPessoaEntities(pessoaEntities);
        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(enderecoRepository.save(enderecoEntity));

        log.info("Endereço adicionado");
        emailService.sendEmailAdicionarEndereco(pessoa);

        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoAtualizarDTO) throws RegraDeNegocioException {
        EnderecoEntity enderecoRecuperado = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        log.info("Atualizando endereco  " + idEndereco);
        EnderecoEntity enderecoEntity = enderecoCreateDtoToEndereco(enderecoAtualizarDTO);
        enderecoEntity.setIdEndereco(idEndereco);
        enderecoEntity.setPessoaEntities(enderecoRecuperado.getPessoaEntities());
        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(enderecoRepository.save(enderecoEntity));
        log.info("Endereço atualizado");


        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {

        log.warn("Removendo endereço...");
        enderecoRepository.delete(enderecoDTOToEnderecoEntity(listByIdEndereco(idEndereco)));
        log.info("Endereço removido");

    }

    public EnderecoDTO listByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        return enderecoToEnderecoDTO(enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado")));

    }

    public List<EnderecoDTO> listByIdPessoa(Integer idPessoa) {
        return pessoaService.listPessoaAndEndereco(idPessoa).get(0).getEnderecoDTOS();
    }

    public EnderecoEntity enderecoCreateDtoToEndereco (EnderecoCreateDTO enderecoCreateDTO){
        return objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);
    }

    public EnderecoDTO enderecoToEnderecoDTO(EnderecoEntity endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public EnderecoEntity enderecoDTOToEnderecoEntity(EnderecoDTO enderecoDTO) {
        return objectMapper.convertValue(enderecoDTO, EnderecoEntity.class);
    }
}
