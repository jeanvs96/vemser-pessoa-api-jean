package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.config.Response;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Validated
@AllArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @Operation(summary = "Listar pessoas", description = "Lista todas as pessoas do banco de dados")
    @Response
    @GetMapping
    public ResponseEntity<List<PessoaDTO>> list() {
        return new ResponseEntity<>(pessoaService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar pessoas e enderecos",
            description = "Lista todas as pessoas do banco de dados, com seus respectivos endereços. " +
                    "Caso seja especificada uma pessoa (por Query Param), traz somente as informações referentes à ela")
    @Response
    @GetMapping("/listar-com-enderecos")
    public ResponseEntity<List<PessoaDTO>> listPessoaAndEnderecos(@RequestParam(required = false) Integer idPessoa) {
        return new ResponseEntity<>(pessoaService.listPessoaAndEndereco(idPessoa), HttpStatus.OK);
    }

    @Response
    @Operation(summary = "Listar pessoas e contatos",
            description = "Lista todas as pessoas do banco de dados, com seus respectivos contatos. " +
                    "Caso seja especificada uma pessoa (por Query Param), traz somente as informações referentes à ela")
    @GetMapping("/listar-com-contatos")
    public ResponseEntity<List<PessoaDTO>> listPessoaAndContatos(@RequestParam(required = false) Integer idPessoa) {
        return new ResponseEntity<>(pessoaService.listPessoaAndContato(idPessoa), HttpStatus.OK);
    }

    @Response
    @Operation(summary = "Listar pessoas e pets",
            description = "Lista todas as pessoas do banco de dados, com seus respectivos pets. " +
                    "Caso seja especificada uma pessoa (por Query Param), traz somente as informações referentes à ela")
    @GetMapping("/listar-com-pet")
    public ResponseEntity<List<PessoaDTO>> listPessoaAndPet(@RequestParam(required = false) Integer idPessoa) {
        return new ResponseEntity<>(pessoaService.listPessoaAndPet(idPessoa), HttpStatus.OK);
    }

    @Operation(summary = "Listar pessoa por cpf", description = "Lista uma pessoa por cpf")
    @Response
    @GetMapping("bycpf/{cpf}")
    public ResponseEntity<PessoaDTO> listByCpf(@PathVariable ("cpf") String cpf){
        return new ResponseEntity<>(pessoaService.listByCpf(cpf), HttpStatus.OK);
    }

    @Operation(summary = "Listar pessoas por nome", description = "Lista todas as pessoas do banco com o nome informado")
    @Response
    @GetMapping("/bynome/{by-nome}")
    public ResponseEntity<List<PessoaDTO>> listByContainsNome(@PathVariable ("by-nome") String nome) {
        return new ResponseEntity<>(pessoaService.listByContainsNome(nome), HttpStatus.OK);
    }


    @Operation(summary = "Adicionar pessoa", description = "Insere pessoa no banco de dados")
    @Response
    @PostMapping
    public ResponseEntity<PessoaDTO> create(@Valid @RequestBody PessoaCreateDTO pessoa) throws RegraDeNegocioException {
        return new ResponseEntity(pessoaService.create(pessoa), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar pessoa",
            description = "Atualiza pessoa existente no banco de dados, localizando-a pelo ID")
    @Response
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaDTO> update(
            @PathVariable("idPessoa") Integer id, @Valid @RequestBody PessoaCreateDTO pessoaAtualizarDTO)
            throws RegraDeNegocioException {
        return new ResponseEntity(pessoaService.update(id, pessoaAtualizarDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deletar pessoa",
            description = "Deleta pessoa existente no banco de dados, localizando-a pelo ID")
    @Response
    @DeleteMapping("/{idPessoa}")
    public void delete(@PathVariable("idPessoa") Integer id) throws RegraDeNegocioException {
        pessoaService.delete(id);
    }
}
