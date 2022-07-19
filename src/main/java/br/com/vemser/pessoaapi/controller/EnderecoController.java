package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.EnderecoService;
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
@RequestMapping("/endereco")
@Validated
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Listar endereços", description = "Lista todos os endereços do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> list() {
        return new ResponseEntity<>(enderecoService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar endereço por ID", description = "Lista um endereço através de seu ID único")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço referenciado pelo ID"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> listByIdEndereco(@PathVariable("idEndereco") Integer idEndereco) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.listByIdEndereco(idEndereco), HttpStatus.OK);
    }

//    @Operation(summary = "Listar endereços de uma pessoa",
//            description = "Lista todos os endereços de uma pessoa, referenciados pelo ID da pessoa")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna a lista de endereços que fazem referência ao ID da pessoa"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/{idPessoa}/pessoa")
//    public ResponseEntity<List<EnderecoDTO>> listByIdPessoa(@PathVariable("idPessoa") Integer idPessoa) {
//        return new ResponseEntity<>(enderecoService.listByIdPessoa(idPessoa), HttpStatus.OK);
//    }

//    @Operation(summary = "Adicionar endereço", description = "Adiciona um endereço, vinculando-o à uma pessoa existente")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do endereço adicionado, com seu novo ID"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @PostMapping("/{idPessoa}")
//    public ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO, @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
//        return new ResponseEntity<>(enderecoService.create(enderecoCreateDTO, idPessoa), HttpStatus.OK);
//    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço, localizando-o por seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do endereço atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> update(@PathVariable("idEndereco") Integer idEndereco, @Valid @RequestBody EnderecoCreateDTO enderecoAtualizarDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.update(idEndereco, enderecoAtualizarDTO), HttpStatus.OK);
    }

    @Operation(summary = "Remover endereço", description = "Remove um endereço, localizando-o por seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna somente o Status Code da requisição HTTP"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEndereco}")
    public void delete(@PathVariable("idEndereco") Integer idEndereco) throws RegraDeNegocioException {
        enderecoService.delete(idEndereco);
    }
}
