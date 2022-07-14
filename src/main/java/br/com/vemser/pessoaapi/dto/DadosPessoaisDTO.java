package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoSexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DadosPessoaisDTO {

    @Schema(description = "Nome da Pessoa")
    private String nome;

    @Schema(description = "Número do CPF da pessoa")
    private String cpf;

    @Schema(description = "Número de CNH da pessoa")
    private String cnh;

    @Schema(description = "Nome da mãe da pessoa")
    private String nomeMae;

    @Schema(description = "Nome do pai da pessoa")
    private String nomePai;

    @Schema(description = "Número do RG da pessoa")
    private String rg;

    @Schema(description = "Sexo da pessoa")
    private TipoSexo sexo;

    @Schema(description = "Número do título de eleitor da pessoa")
    private String tituloEleitor;
}
