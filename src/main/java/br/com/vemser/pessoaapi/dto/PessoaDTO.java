package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.entity.PetEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PessoaDTO extends PessoaCreateDTO{

    @Schema(description = "ID exclusivo da pessoa")
    private Integer idPessoa;

    @Schema(description = "Endereços da pessoa")
    private List<EnderecoDTO> enderecoDTOS;

    @Schema(description = "Endereços da pessoa")
    private List<ContatoDTO> contatoDTOS;

    @Schema(description = "Endereços da pessoa")
    private PetDTO petDTO;
}
