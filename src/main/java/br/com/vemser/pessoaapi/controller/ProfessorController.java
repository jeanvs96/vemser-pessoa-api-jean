package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.entity.ProfessorEntity;
import br.com.vemser.pessoaapi.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorRepository professorRepository;

    @GetMapping
    public List<ProfessorEntity> list() {
        return professorRepository.findAll();
    }

    @PostMapping
    public ProfessorEntity post(@RequestBody ProfessorEntity professorEntity) {
        return professorRepository.save(professorEntity);
    }
}
