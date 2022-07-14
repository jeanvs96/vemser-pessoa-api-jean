package br.com.vemser.pessoaapi.controller;


import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaDadosPessoaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa-dados-pessoais")
@Validated
public class PessoaDadosPessoaisController {

    @Autowired
    private PessoaDadosPessoaisService pessoaDadosPessoaisService;

    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas, com todos os seus dados pessoais")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de pessoas, com seus dados pessoais"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<PessoaDadosPessoaisDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Criar pessoa", description = "Insere uma pessoa, com todos os seus dados pessoais no DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a pessoa criada, com seus dados pessoais"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<PessoaDadosPessoaisDTO> create(@Valid @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.create(pessoaDadosPessoaisCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar pessoa", description = "Atualiza uma pessoa e todos os seus dados pessoais, localizando-a pelo ID da pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a pessoa criada, com seus dados pessoais"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDadosPessoaisDTO> update(@PathVariable("idPessoa") Integer idPessoa, @Valid @RequestBody PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity(pessoaDadosPessoaisService.update(idPessoa, pessoaDadosPessoaisCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar pessoa", description = "Deleta uma pessoa e todos os seus dados pessoais, localizando-a pelo ID da pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna somente o Status Code da requisição HTTP"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPessoa}")
    public void delete(@PathVariable ("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaDadosPessoaisService.delete(idPessoa);
    }
}
