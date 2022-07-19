package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PessoaService {

    private PessoaRepository pessoaRepository;
    private EmailService emailService;
    private ObjectMapper objectMapper;

    public List<PessoaDTO> list() {
        return pessoaRepository.findAll()
                .stream()
                .map(this::entityToPessoaDTO).toList();
    }

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando pessoa...");
        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaRepository.save(pessoaCreateDtoToPessoaEntity(pessoaCreateDTO)));
        log.info(pessoaDTO.getNome() + " adicionado(a) ao banco de dados");

        emailService.sendEmailCriarPessoa(pessoaDTO);

        return pessoaDTO;
    }

    public PessoaDTO update(Integer idPessoa, PessoaCreateDTO pessoaAtualizarDTO) throws RegraDeNegocioException {
        log.info("Atualizando pessoa...");
        PessoaEntity pessoaEntity = listByIdPessoa(idPessoa);
        pessoaEntity.setNome(pessoaAtualizarDTO.getNome());
        pessoaEntity.setDataNascimento(pessoaAtualizarDTO.getDataNascimento());
        pessoaEntity.setCpf(pessoaAtualizarDTO.getCpf());
        pessoaEntity.setEmail(pessoaAtualizarDTO.getEmail());
        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaRepository.save(pessoaEntity));

        log.info("Dados de " + pessoaDTO.getNome() + " atualizados no banco de dados");
        emailService.sendEmailAlterarPessoa(pessoaDTO);
        return pessoaDTO;

    }

    public void delete(Integer idPessoa) throws RegraDeNegocioException {
        PessoaEntity pessoaDeletar = listByIdPessoa(idPessoa);
        log.warn("Deletando...");
        pessoaRepository.delete(pessoaDeletar);
        log.info(pessoaDeletar.getNome() + " removida do banco de dados");

        emailService.sendEmailDeletarPessoa(pessoaDeletar);
    }

    public List<PessoaDTO> listByName(String nome) {
        return pessoaRepository.findAll()
                .stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .toList().stream().map(this::entityToPessoaDTO).toList();
    }

    public PessoaEntity listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RegraDeNegocioException("Pessoa não encontrada"));
    }


    public PessoaEntity pessoaCreateDtoToPessoaEntity(PessoaCreateDTO pessoaCreateDTO) {
        return objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
    }

    public PessoaDTO entityToPessoaDTO(PessoaEntity pessoaEntity) {
        return objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
    }
}
