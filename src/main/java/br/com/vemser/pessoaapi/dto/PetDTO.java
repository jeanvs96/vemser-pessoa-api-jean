package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PetDTO extends PetCreateDTO{

    @Schema(description = "ID exclusivo do Pet")
    private Integer idPet;
}
