package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.*;
import br.com.vemser.pessoaapi.entity.ContatoEntity;
import br.com.vemser.pessoaapi.entity.EnderecoEntity;
import br.com.vemser.pessoaapi.entity.PessoaEntity;
import br.com.vemser.pessoaapi.entity.PetEntity;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<PessoaDTO> listPessoaAndEndereco(Integer idPessoa) {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(pessoaEntity -> {
                        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaEntity);
                        pessoaDTO.setEnderecoDTOS(pessoaEntity.getEnderecoEntities().stream()
                                .map(this::enderecoToEnderecoDTO)
                                .toList());
                        return pessoaDTO;
                    }).toList();
        } else {
            return pessoaRepository.findById(idPessoa)
                    .map(pessoaEntity -> {
                        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaEntity);
                        pessoaDTO.setEnderecoDTOS(pessoaEntity.getEnderecoEntities().stream()
                                .map(this::enderecoToEnderecoDTO).toList());
                        return pessoaDTO;
                    }).stream().toList();
        }
    }

    public List<PessoaDTO> listPessoaAndContato(Integer idPessoa) {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(pessoaEntity -> {
                        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaEntity);
                        pessoaDTO.setContatoDTOS(pessoaEntity.getContatoEntities().stream()
                                .map(this::contatoToContatoDto).toList());
                        return pessoaDTO;
                    }).toList();
        } else {
            return pessoaRepository.findById(idPessoa)
                    .map(pessoaEntity -> {
                        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaEntity);
                        pessoaDTO.setContatoDTOS(pessoaEntity.getContatoEntities().stream()
                                .map(this::contatoToContatoDto).toList());
                        return pessoaDTO;
                    }).stream().toList();
        }
    }

    public List<PessoaDTO> listPessoaAndPet(Integer idPessoa) {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(pessoaEntity -> {
                        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaEntity);
                        pessoaDTO.setPetDTO(petToPetDTO(pessoaEntity.getPetEntity()));
                        return pessoaDTO;
                    }).toList();
        } else {
            return pessoaRepository.findById(idPessoa)
                    .map(pessoaEntity -> {
                        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaEntity);
                        pessoaDTO.setPetDTO(petToPetDTO(pessoaEntity.getPetEntity()));
                        return pessoaDTO;
                    }).stream().toList();
        }
    }

    public List<PessoaDTO> listByContainsNome(String nome) {
        return pessoaRepository.findAllByNomeContainsIgnoreCase(nome).stream()
                .map(this::entityToPessoaDTO)
                .toList();
    }

    public PessoaDTO listByCpf(String cpf) {
        return entityToPessoaDTO(pessoaRepository.findByCpf(cpf));
    }

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando pessoa...");
        PessoaDTO pessoaDTO = entityToPessoaDTO(pessoaRepository.save(pessoaCreateDtoToPessoaEntity(pessoaCreateDTO)));
        log.info(pessoaDTO.getNome() + " adicionado(a) ao banco de dados");

        emailService.sendEmailCriarPessoa(pessoaDTO);

        return pessoaDTO;
    }

    public PessoaDTO update(Integer idPessoa, PessoaCreateDTO pessoaAtualizarDTO) throws RegraDeNegocioException {
        PessoaEntity pessoaRecuperada = listByIdPessoa(idPessoa);

        log.info("Atualizando pessoa...");
        PessoaEntity pessoaEntity = pessoaCreateDtoToPessoaEntity(pessoaAtualizarDTO);
        pessoaEntity.setIdPessoa(idPessoa);
        pessoaEntity.setEnderecoEntities(pessoaRecuperada.getEnderecoEntities());
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

    public EnderecoDTO enderecoToEnderecoDTO(EnderecoEntity endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public ContatoDTO contatoToContatoDto(ContatoEntity contato) {
        return objectMapper.convertValue(contato, ContatoDTO.class);
    }

    public PetDTO petToPetDTO(PetEntity petEntity) {
        return objectMapper.convertValue(petEntity, PetDTO.class);
    }
}
