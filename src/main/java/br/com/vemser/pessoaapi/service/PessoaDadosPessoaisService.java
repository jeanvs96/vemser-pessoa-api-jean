package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.*;
import br.com.vemser.pessoaapi.entity.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaDadosPessoaisService {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @Autowired
    private ObjectMapper objectMapper;

    public List<PessoaDadosPessoaisDTO> list() {
        return listBuilder();
    }

    public PessoaDadosPessoaisDTO create(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        PessoaDTO pessoaDTO = pessoaService.create(
                convertToPessoaDTO(pessoaDadosPessoaisCreateDTO));

        dadosPessoaisService.post(
                convertToDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO));

        PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO = CreateDTOToPessoaDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO);
        pessoaDadosPessoaisDTO.setIdPessoa(pessoaDTO.getIdPessoa());

        return pessoaDadosPessoaisDTO;
    }

    public PessoaDadosPessoaisDTO update(Integer idPessoa, PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) throws RegraDeNegocioException {
        PessoaCreateDTO pessoaCreateDTO = convertToPessoaDTO(pessoaDadosPessoaisCreateDTO);
        pessoaService.update(idPessoa, pessoaCreateDTO);
        DadosPessoaisDTO dadosPessoaisDTO = convertToDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO);
        dadosPessoaisService.put(pessoaDadosPessoaisCreateDTO.getCpf(), dadosPessoaisDTO);

        PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO = CreateDTOToPessoaDadosPessoaisDTO(pessoaDadosPessoaisCreateDTO);
        pessoaDadosPessoaisDTO.setIdPessoa(idPessoa);

        return pessoaDadosPessoaisDTO;
    }

    public void delete(Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.listByIdPessoa(idPessoa);
        pessoaService.delete(idPessoa);
        for (DadosPessoaisDTO dadosPessoaisDTO : dadosPessoaisService.getAll()) {
            if (pessoa.getCpf().equals(dadosPessoaisDTO.getCpf())){
                dadosPessoaisService.delete(pessoa.getCpf());
            }
        }
    }

    public PessoaCreateDTO convertToPessoaDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();

        pessoaCreateDTO.setNome(pessoaDadosPessoaisCreateDTO.getNome());
        pessoaCreateDTO.setCpf(pessoaDadosPessoaisCreateDTO.getCpf());
        pessoaCreateDTO.setDataNascimento(pessoaDadosPessoaisCreateDTO.getDataNascimento());
        pessoaCreateDTO.setEmail(pessoaDadosPessoaisCreateDTO.getEmail());

        return pessoaCreateDTO;
    }

    public DadosPessoaisDTO convertToDadosPessoaisDTO(PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();

        dadosPessoaisDTO.setNome(pessoaDadosPessoaisCreateDTO.getNome());
        dadosPessoaisDTO.setCpf(pessoaDadosPessoaisCreateDTO.getCpf());
        dadosPessoaisDTO.setCnh(pessoaDadosPessoaisCreateDTO.getCnh());
        dadosPessoaisDTO.setNomeMae(pessoaDadosPessoaisCreateDTO.getNomeMae());
        dadosPessoaisDTO.setNomePai(pessoaDadosPessoaisCreateDTO.getNomePai());
        dadosPessoaisDTO.setRg(pessoaDadosPessoaisCreateDTO.getRg());
        dadosPessoaisDTO.setSexo(pessoaDadosPessoaisCreateDTO.getSexo());
        dadosPessoaisDTO.setTituloEleitor(pessoaDadosPessoaisCreateDTO.getTituloEleitor());

        return dadosPessoaisDTO;
    }

    public List<PessoaDadosPessoaisDTO> listBuilder (){
        List<PessoaDadosPessoaisDTO> pessoaDadosPessoaisDTO = new ArrayList<>();
        List<PessoaDTO> pessoasDTO = pessoaService.list();
        List<DadosPessoaisDTO> dadosPessoaisDTO = dadosPessoaisService.getAll();

        for (int i = 0; i < pessoasDTO.size(); i++) {
            pessoaDadosPessoaisDTO.add(new PessoaDadosPessoaisDTO());
            pessoaDadosPessoaisDTO.get(i).setIdPessoa(pessoasDTO.get(i).getIdPessoa());
            pessoaDadosPessoaisDTO.get(i).setNome(pessoasDTO.get(i).getNome());
            pessoaDadosPessoaisDTO.get(i).setCpf(pessoasDTO.get(i).getCpf());
            pessoaDadosPessoaisDTO.get(i).setDataNascimento(pessoasDTO.get(i).getDataNascimento());
            pessoaDadosPessoaisDTO.get(i).setEmail(pessoasDTO.get(i).getEmail());
        }

        for (int i = 0; i < pessoaDadosPessoaisDTO.size(); i++) {
            for (int j = 0; j < dadosPessoaisDTO.size(); j++) {
                if (pessoaDadosPessoaisDTO.get(i).getCpf().equals(dadosPessoaisDTO.get(j).getCpf())) {
                    pessoaDadosPessoaisDTO.get(i).setCnh(dadosPessoaisDTO.get(j).getCnh());
                    pessoaDadosPessoaisDTO.get(i).setNomeMae(dadosPessoaisDTO.get(j).getNomeMae());
                    pessoaDadosPessoaisDTO.get(i).setNomePai(dadosPessoaisDTO.get(j).getNomePai());
                    pessoaDadosPessoaisDTO.get(i).setRg(dadosPessoaisDTO.get(j).getRg());
                    pessoaDadosPessoaisDTO.get(i).setSexo(dadosPessoaisDTO.get(j).getSexo());
                    pessoaDadosPessoaisDTO.get(i).setTituloEleitor(dadosPessoaisDTO.get(j).getTituloEleitor());
                }
            }
        }
        return pessoaDadosPessoaisDTO;
    }

    public PessoaDadosPessoaisDTO CreateDTOToPessoaDadosPessoaisDTO (PessoaDadosPessoaisCreateDTO pessoaDadosPessoaisCreateDTO){
        return objectMapper.convertValue(pessoaDadosPessoaisCreateDTO, PessoaDadosPessoaisDTO.class);
    }
}
