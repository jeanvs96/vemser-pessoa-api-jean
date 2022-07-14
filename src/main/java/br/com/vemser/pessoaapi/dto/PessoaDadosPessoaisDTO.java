package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PessoaDadosPessoaisDTO extends PessoaDadosPessoaisCreateDTO{

    @Schema(description = "ID exclusivo da pessoa")
    private Integer idPessoa;
}
