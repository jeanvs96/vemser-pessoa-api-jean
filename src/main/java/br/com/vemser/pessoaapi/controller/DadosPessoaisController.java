package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.service.DadosPessoaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dadospessoais")
public class DadosPessoaisController {

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @Operation(summary = "Listar conjuntos de dados pessoais", description = "Lista os conjuntos de dados pessoais")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de dados pessoais"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<DadosPessoaisDTO>> getAll(){
        return new ResponseEntity<>(dadosPessoaisService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Criar conjunto de dados pessoais", description = "Insere um conjunto de dados pessoais no DB")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o conjunto de dados pessoais criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    public ResponseEntity<DadosPessoaisDTO> post(@RequestBody DadosPessoaisDTO dadosPessoaisDTO){
        return new ResponseEntity<>(dadosPessoaisService.post(dadosPessoaisDTO), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar conjunto de dados pessoais", description = "Atualiza um conjunto de dados pessoais, localizando-os pelo CPF")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o conjunto de dados pessoais atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{cpf}")
    public ResponseEntity<DadosPessoaisDTO> put(@PathVariable("cpf") String cpf, @RequestBody DadosPessoaisDTO dadosPessoaisDTO){
        return new ResponseEntity<>(dadosPessoaisService.put(cpf, dadosPessoaisDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar conjunto de dados pessoais", description = "Deleta um conjunto de dados pessoais, localizando-os pelo CPF")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna somente o Status Code da requisição HTTP"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{cpf}")
    public void Delete(@PathVariable("cpf") String cpf){
        dadosPessoaisService.delete(cpf);
    }

    @Operation(summary = "Listar conjunto de dados pessoais", description = "Lista um conjunto de dados pessoais, localizando-o pelo CPF")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o conjunto de dados pessoais"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{cpf}")
    public ResponseEntity<DadosPessoaisDTO> get(@PathVariable("cpf") String cpf){
        return new ResponseEntity<>(dadosPessoaisService.get(cpf), HttpStatus.OK);
    }
}
