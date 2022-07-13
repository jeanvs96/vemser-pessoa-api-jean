package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContatoDTO extends ContatoCreateDTO {

    @Schema(description = "ID exclusivo do contato")
    private Integer idContato;
}
