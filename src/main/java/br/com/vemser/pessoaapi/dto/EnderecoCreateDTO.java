package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoCreateDTO {

    @Schema(description = "ID da pessoa à quem o endereço pertence")
    private Integer idPessoa;

    @Schema(description = "Tipo de endereço (COMERCIAL/RESIDENCIAL)")
    @NotNull(message = "Informe o tipo de endereço (COMERCIAL/RESIDENCIAL)")
    private TipoEndereco tipo;

    @Schema(description = "Logradouro do endereço")
    @NotEmpty(message = "Informe um logradouro válido")
    @Size(max = 250, message = "Informe um logradouro com até 250 carácteres")
    private String logradouro;

    @Schema(description = "Número do endereço")
    @NotNull(message = "Informe um número válido")
    @Min(0)
    private Integer numero;

    @Schema(description = "Complemento (opcional) do endereço")
    private String complemento;

    @Schema(description = "CEP do endereço", maxLength = 8, minLength = 8)
    @NotEmpty(message = "Informe um CEP válido")
    @Size(max = 8, min = 8, message = "O CEP deve conter somente 8 números")
    private String cep;

    @Schema(description = "Cidade do endereço")
    @NotEmpty(message = "Informe uma cidade válida")
    @Size(max = 250, message = "Informe uma cidade com até 250 carácteres")
    private String cidade;

    @Schema(description = "Estado do endereço")
    @NotNull(message = "Informe um estado")
    private String estado;

    @Schema(description = "País do endereço")
    @NotNull(message = "Informe um país")
    private String pais;
}
