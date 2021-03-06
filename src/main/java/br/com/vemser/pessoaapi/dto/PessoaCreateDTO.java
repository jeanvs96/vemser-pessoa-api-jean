package br.com.vemser.pessoaapi.dto;

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
public class PessoaCreateDTO {

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

    @Schema(description = "Email")
    @NotBlank
    private String email;
}
