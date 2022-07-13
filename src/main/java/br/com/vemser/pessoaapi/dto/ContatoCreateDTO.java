package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContatoCreateDTO {

    @Schema(description = "ID da pessoa à quem o contato pertence")
    private Integer idPessoa;

    @Schema(description = "Tipo do contato(COMERCIAL/RESIDENCIAL)")
    @NotNull(message = "Informe o tipo de contato")
    private TipoContato tipoContato;

    @Schema(description = "Número de telefone do contato", maxLength = 13)
    @NotEmpty(message = "Informe o número")
    @Size(max = 13, message = "Informe um número válido")
    private String numero;

    @Schema(description = "Forma de utilizar o contato")
    @NotEmpty
    private String descricao;
}
