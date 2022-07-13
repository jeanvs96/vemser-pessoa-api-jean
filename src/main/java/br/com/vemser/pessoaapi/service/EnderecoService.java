package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;

import br.com.vemser.pessoaapi.entity.Endereco;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;


    public List<EnderecoDTO> list() {
        return enderecoRepository.list().stream()
                .map(this::enderecoToEnderecoDTO)
                .toList();
    }

    public EnderecoDTO create(EnderecoCreateDTO enderecoCreateDTO, Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(idPessoa);
        enderecoCreateDTO.setIdPessoa(pessoa.getIdPessoa());
        log.info("Adicionando endereco...");

        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(
                enderecoRepository.create(enderecoCreateDtoToEndereco(enderecoCreateDTO)));

        log.info("Endereço adicionado");
        emailService.sendEmailAdicionarEndereco(pessoa);

        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoAtualizarDTO) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(enderecoAtualizarDTO.getIdPessoa());

        log.info("Atualizando endereco de " + pessoa.getNome());
        EnderecoDTO enderecoDTO = enderecoToEnderecoDTO(
                enderecoRepository.update(recuperarByIdEndereco(idEndereco), enderecoCreateDtoToEndereco(enderecoAtualizarDTO)));
        log.info("Endereço atualizado");

        emailService.sendEmailAtualizarEndereco(pessoa);

        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(recuperarByIdEndereco(idEndereco).getIdPessoa());

        log.warn("Removendo endereço...");
        enderecoRepository.delete(recuperarByIdEndereco(idEndereco));
        log.info("Endereço removido");

        emailService.sendEmailRemoverEndereco(pessoa);
    }

    public EnderecoDTO listByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        return enderecoToEnderecoDTO(recuperarByIdEndereco(idEndereco));
    }

    public List<EnderecoDTO> listByIdPessoa(Integer idPessoa) {
        return enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdPessoa().equals(idPessoa))
                .map(this::enderecoToEnderecoDTO)
                .toList();
    }

    public Endereco recuperarByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
         return enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));
    }

    public Endereco enderecoCreateDtoToEndereco (EnderecoCreateDTO enderecoCreateDTO){
        return objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
    }

    public EnderecoDTO enderecoToEnderecoDTO(Endereco endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }
}
