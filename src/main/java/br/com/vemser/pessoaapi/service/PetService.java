package br.com.vemser.pessoaapi.service;


import br.com.vemser.pessoaapi.dto.PetCreateDTO;
import br.com.vemser.pessoaapi.dto.PetDTO;
import br.com.vemser.pessoaapi.entity.PetEntity;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PetService {

    private final PetRepository petRepository;

    private final PessoaService pessoaService;

    private final ObjectMapper objectMapper;

    public List<PetDTO> list() {
        return petRepository.findAll()
                .stream()
                .map(this::entityToPetDTO).toList();
    }

    public PetDTO create(PetCreateDTO petCreateDTO) throws RegraDeNegocioException {
        log.info("Criando pet...");
        PetEntity petEntity = createDtoToEntity(petCreateDTO);
        petEntity.setPessoaEntity(pessoaService.listByIdPessoa(petEntity.getIdPessoa()));
        PetDTO petDTO = entityToPetDTO(petRepository.save(petEntity));
        log.info(petDTO.getNome() + " adicionado(a) ao banco de dados");

        return petDTO;
    }

    public PetDTO update(Integer idPet, PetCreateDTO petAtualizarDTO) throws RegraDeNegocioException {
        log.info("Atualizando pet...");

        PetEntity petEntity = createDtoToEntity(petAtualizarDTO);
        petEntity.setIdPet(idPet);
        petEntity.setPessoaEntity(pessoaService.listByIdPessoa(petAtualizarDTO.getIdPessoa()));
        PetDTO petDTO = entityToPetDTO(petRepository.save(petEntity));

        log.info("Dados de " + petDTO.getNome() + " atualizados no banco de dados");
        return petDTO;
    }

    public void delete(Integer idPet) throws RegraDeNegocioException {
        PetEntity petDeletar = listByIdPet(idPet);
        log.warn("Deletando...");
        petRepository.delete(petDeletar);
        log.info(petDeletar.getNome() + " removido do banco de dados");
    }

    public PetEntity listByIdPet(Integer idPet) throws RegraDeNegocioException {
        return petRepository.findById(idPet)
                .orElseThrow(() -> new RegraDeNegocioException("Pet não encontrado"));
    }
    public PetDTO entityToPetDTO(PetEntity petEntity) {
        return objectMapper.convertValue(petEntity, PetDTO.class);
    }

    public PetEntity createDtoToEntity(PetCreateDTO petCreateDTO) {
        return objectMapper.convertValue(petCreateDTO, PetEntity.class);
    }
}
