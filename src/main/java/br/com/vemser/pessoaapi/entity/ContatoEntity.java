package br.com.vemser.pessoaapi.entity;

import br.com.vemser.pessoaapi.enums.TipoContato;
import lombok.*;

import javax.persistence.*;

@Entity(name = "CONTATO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoEntity {

    @Id
    @SequenceGenerator(name = "SEQ_CONTATO", sequenceName = "seq_contato", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTATO")
    private Integer idContato;

    @Column(name = "ID_PESSOA")
    private Integer idPessoa;

    @Column(name = "TIPO")
    private TipoContato tipoContato;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "DESCRICAO")
    private String descricao;
}
