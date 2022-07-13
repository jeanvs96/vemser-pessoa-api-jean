package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entity.Contato;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.ContatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    public List<ContatoDTO> list() {
        return contatoRepository.list().stream().map(this::contatoToContatoDto).toList();
    }

    public ContatoDTO create(ContatoCreateDTO contatoCreateDTO, Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(idPessoa);
        contatoCreateDTO.setIdPessoa(idPessoa);

        log.info("Adicionando contato à pessoa: " + pessoa.getNome());
        ContatoDTO contatoDTO = contatoToContatoDto(
                contatoRepository.create(contatoCreateDtoToContato(contatoCreateDTO)));
        log.info("Contato adicionado");

        emailService.sendEmailAdicionarContato(pessoa);

        return contatoDTO;
    }

    public ContatoDTO update(Integer idContato, ContatoCreateDTO contatoAtualizarDTO) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(contatoAtualizarDTO.getIdPessoa());

        log.info("Atualizando contato de " + pessoa.getNome());
        ContatoDTO contatoDTO = contatoToContatoDto(
                contatoRepository.update(contatoByIdContato(idContato), contatoCreateDtoToContato(contatoAtualizarDTO)));
        log.info("Contato atualizado");

        emailService.sendEmailAtualizarContato(pessoa);

        return contatoDTO;
    }

    public void delete(Integer idContato) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(contatoByIdContato(idContato).getIdPessoa());

        log.warn("Deletando contato...");
        contatoRepository.delete(contatoByIdContato(idContato));
        log.info("Contato deletado");

        emailService.sendEmailRemoverContato(pessoa);
    }

    public List<ContatoDTO> listByIdPessoa(Integer idPessoa) {
        return contatoRepository.list().stream()
                .filter(contato -> contato.getIdPessoa().equals(idPessoa))
                .map(this::contatoToContatoDto).toList();
    }


    public Contato contatoByIdContato(Integer idContato) throws RegraDeNegocioException {
        return contatoRepository.list().stream()
                .filter(contato -> contato.getIdContato().equals(idContato))
                .findFirst().orElseThrow(() -> new RegraDeNegocioException("O contato informado não existe"));
    }

    public Contato contatoCreateDtoToContato(ContatoCreateDTO contatoCreateDTO) {
        return objectMapper.convertValue(contatoCreateDTO, Contato.class);
    }

    public ContatoDTO contatoToContatoDto(Contato contato) {
        return objectMapper.convertValue(contato, ContatoDTO.class);
    }
}
