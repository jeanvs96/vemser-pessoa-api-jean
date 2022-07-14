package br.com.vemser.pessoaapi.controller;


import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaDadosPessoaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa-dados-pessoais")
public class PessoaDadosPessoaisController {

    @Autowired
    PessoaDadosPessoaisService pessoaDadosPessoaisService;

    @GetMapping
    public ResponseEntity<List<PessoaDadosPessoaisDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.list(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PessoaDadosPessoaisDTO> create(@RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.create(pessoaDadosPessoaisCreateDTO), HttpStatus.OK);
    }

    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDadosPessoaisDTO> update(@PathVariable("idPessoa") Integer idPessoa, @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.update(idPessoa, pessoaDadosPessoaisCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{idPessoa}")
    public void delete(@PathVariable ("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaDadosPessoaisService.delete(idPessoa);
    }
}
