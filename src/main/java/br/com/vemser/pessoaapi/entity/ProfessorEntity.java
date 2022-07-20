package br.com.vemser.pessoaapi.entity;

import br.com.vemser.pessoaapi.entity.pk.ProfessorPk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PROFESSOR")
public class ProfessorEntity {

    @EmbeddedId
    private ProfessorPk professorPk;

    @Column(name = "nome")
    private String nome;

    @Column(name = "salario")
    private Double salario;
}
