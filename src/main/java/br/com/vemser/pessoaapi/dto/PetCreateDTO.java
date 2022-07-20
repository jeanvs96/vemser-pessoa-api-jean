package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoPet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PetCreateDTO {

    @Schema(description = "Nome do pet")
    private String nome;

    @Schema(description = "Tipo do pet")
    private TipoPet tipoPet;

    @Schema(description = "ID da pessoa à quem o Pet pertence")
    private Integer idPessoa;
}
