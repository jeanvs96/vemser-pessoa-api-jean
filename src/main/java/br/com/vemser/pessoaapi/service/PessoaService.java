package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    public PessoaService() {
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.list().stream().map(this::pessoaToPessoaDTO).toList();
    }

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando pessoa...");
        PessoaDTO pessoaDTO = pessoaToPessoaDTO(pessoaRepository.create(pessoaCreateDtoToPessoa(pessoaCreateDTO)));
        log.info(pessoaDTO.getNome() + " adicionado(a) ao banco de dados");

        emailService.sendEmailCriarPessoa(pessoaDTO);

        return pessoaDTO;
    }

    public PessoaDTO update(Integer id, PessoaCreateDTO pessoaAtualizarDTO) throws RegraDeNegocioException {
        log.info("Atualizando pessoa...");
        PessoaDTO pessoaDTO = pessoaToPessoaDTO(
                pessoaRepository.update(listByIdPessoa(id), pessoaCreateDtoToPessoa(pessoaAtualizarDTO)));
        log.info("Dados de " + pessoaDTO.getNome() + " atualizados no banco de dados");

        emailService.sendEmailAlterarPessoa(pessoaDTO);

        return pessoaDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        Pessoa pessoaDeletar = listByIdPessoa(id);
        log.warn("Deletando...");
        pessoaRepository.delete(pessoaDeletar);
        log.info(pessoaDeletar.getNome() + " removida do banco de dados");

        emailService.sendEmailDeletarPessoa(pessoaDeletar);
    }

    public List<PessoaDTO> listByName(String nome) {
        return pessoaRepository.list()
                .stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .toList().stream().map(this::pessoaToPessoaDTO).toList();
    }

    public Pessoa listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.list().stream()
                .filter(pessoa -> pessoa.getIdPessoa().equals(idPessoa))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Pessoa não encontrada"));
    }


    public Pessoa pessoaCreateDtoToPessoa (PessoaCreateDTO pessoaCreateDTO){
        return objectMapper.convertValue(pessoaCreateDTO, Pessoa.class);
    }

    public PessoaDTO pessoaToPessoaDTO(Pessoa pessoa) {
        return objectMapper.convertValue(pessoa, PessoaDTO.class);
    }
}
