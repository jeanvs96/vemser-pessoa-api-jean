package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoSexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PessoaDadosPessoaisCreateDTO {



    @Schema(description = "Nome da Pessoa")
    @NotBlank(message = "Informe o nome")
    private String nome;

    @Schema(description = "Data de nascimento")
    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Schema(description = "CPF")
    @NotBlank
    @Size(max = 11, min = 11, message = "O CPF deve conter somente 11 números.")
    private String cpf;

    @Schema(description = "Email da pessoa")
    @NotBlank
    private String email;

    @Schema(description = "Número de CNH da pessoa")
    @NotBlank
    private String cnh;

    @Schema(description = "Nome da mãe da pessoa")
    @NotBlank
    private String nomeMae;

    @Schema(description = "Nome do pai da pessoa")
    @NotBlank
    private String nomePai;

    @Schema(description = "Número do RG da pessoa")
    @NotBlank
    private String rg;

    @Schema(description = "Sexo da pessoa")
    @NotNull
    private TipoSexo sexo;

    @Schema(description = "Número do título de eleitor da pessoa")
    @NotBlank
    private String tituloEleitor;
}
