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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        PessoaEntity pessoa = pessoaService.listByIdPessoa(idPessoa);
//        enderecoCreateDTO.setIdPessoa(pessoa.getIdPessoa());
        log.info("Adicionando endereco...");

        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(
                enderecoRepository.save(enderecoCreateDtoToEndereco(enderecoCreateDTO)));

        log.info("Endereço adicionado");
        emailService.sendEmailAdicionarEndereco(pessoa);

        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoAtualizarDTO) throws RegraDeNegocioException {
//        PessoaEntity pessoa = pessoaService.listByIdPessoa(enderecoAtualizarDTO.getIdPessoa());
        EnderecoDTO enderecoDTORecuperado = listByIdEndereco(idEndereco);


//        log.info("Atualizando endereco de " + pessoa.getNome());
        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(
                enderecoRepository.save(enderecoCreateDtoToEndereco(enderecoAtualizarDTO)));
        log.info("Endereço atualizado");

//        emailService.sendEmailAtualizarEndereco(pessoa);

        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
//        PessoaEntity pessoa = pessoaService.listByIdPessoa(recuperarByIdEndereco(idEndereco).getIdPessoa());

        log.warn("Removendo endereço...");
        enderecoRepository.delete(enderecoDTOToEnderecoEntity(listByIdEndereco(idEndereco)));
        log.info("Endereço removido");

//        emailService.sendEmailRemoverEndereco(pessoa);
    }

    public EnderecoDTO listByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        return enderecoToEnderecoDTO(enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado")));

    }

//    public List<EnderecoDTO> listByIdPessoa(Integer idPessoa) {
//        return enderecoRepository.list().stream()
//                .filter(endereco -> endereco.getIdPessoa().equals(idPessoa))
//                .map(this::enderecoToEnderecoDTO)
//                .toList();
//    }

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
