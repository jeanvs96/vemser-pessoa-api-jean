package br.com.vemser.pessoaapi.repository;

import br.com.vemser.pessoaapi.entity.Endereco;
import br.com.vemser.pessoaapi.enums.TipoEndereco;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EnderecoRepository {
    private static List<Endereco> listaEndereco = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public EnderecoRepository() {
        listaEndereco.add(new Endereco(COUNTER.incrementAndGet(), 1, TipoEndereco.COMERCIAL, "Rua X", 1, "", "12345678910", "SP", "SP", "BR"));
        listaEndereco.add(new Endereco(COUNTER.incrementAndGet(), 2, TipoEndereco.RESIDENCIAL, "Rua Y", 2, "", "12345678910", "SP", "SP", "BR"));
        listaEndereco.add(new Endereco(COUNTER.incrementAndGet(), 3, TipoEndereco.COMERCIAL, "Rua Z", 3, "", "12345678910", "SP", "SP", "BR"));
    }

    public List<Endereco> list() {
        return listaEndereco;
    }

    public Endereco create(Endereco endereco) {
        endereco.setIdEndereco(COUNTER.incrementAndGet());
        listaEndereco.add(endereco);
        return endereco;
    }

    public Endereco update(Endereco enderecoRecuperado, Endereco enderecoAtualizar) {
        enderecoRecuperado.setIdPessoa(enderecoAtualizar.getIdPessoa());
        enderecoRecuperado.setTipo(enderecoAtualizar.getTipo());
        enderecoRecuperado.setLogradouro(enderecoAtualizar.getLogradouro());
        enderecoRecuperado.setNumero(enderecoAtualizar.getNumero());
        enderecoRecuperado.setComplemento(enderecoAtualizar.getComplemento());
        enderecoRecuperado.setCep(enderecoAtualizar.getCep());
        enderecoRecuperado.setCidade(enderecoAtualizar.getCidade());
        enderecoRecuperado.setEstado(enderecoAtualizar.getEstado());
        enderecoRecuperado.setPais(enderecoAtualizar.getPais());
        return enderecoRecuperado;
    }

    public void delete(Endereco endereco) {
        listaEndereco.remove(endereco);
    }
}
