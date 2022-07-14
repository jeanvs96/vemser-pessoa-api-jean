package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.dto.PessoaDadosPessoaisDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
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

    public PessoaDadosPessoaisDTO create(PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO) throws RegraDeNegocioException {
        pessoaDadosPessoaisDTO.setIdPessoa(PessoaDadosPessoaisToPessoaDTO(pessoaDadosPessoaisDTO).getIdPessoa());
        PessoaDadosPessoaisToDadosPessoaisDTO(pessoaDadosPessoaisDTO);

        return pessoaDadosPessoaisDTO;
    }

    public PessoaDTO PessoaDadosPessoaisToPessoaDTO(PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO) throws RegraDeNegocioException {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();

        pessoaCreateDTO.setNome(pessoaDadosPessoaisDTO.getNome());
        pessoaCreateDTO.setCpf(pessoaDadosPessoaisDTO.getCpf());
        pessoaCreateDTO.setDataNascimento(pessoaDadosPessoaisDTO.getDataNascimento());
        pessoaCreateDTO.setEmail(pessoaDadosPessoaisDTO.getEmail());

        return pessoaService.create(pessoaCreateDTO);
    }

    public List<PessoaDadosPessoaisDTO> list() {
        return listBuilder();
    }

    public void PessoaDadosPessoaisToDadosPessoaisDTO(PessoaDadosPessoaisDTO pessoaDadosPessoaisDTO) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();

        dadosPessoaisDTO.setNome(pessoaDadosPessoaisDTO.getNome());
        dadosPessoaisDTO.setCpf(pessoaDadosPessoaisDTO.getCpf());
        dadosPessoaisDTO.setCnh(pessoaDadosPessoaisDTO.getCnh());
        dadosPessoaisDTO.setNomeMae(pessoaDadosPessoaisDTO.getNomeMae());
        dadosPessoaisDTO.setNomePai(pessoaDadosPessoaisDTO.getNomePai());
        dadosPessoaisDTO.setRg(pessoaDadosPessoaisDTO.getRg());
        dadosPessoaisDTO.setSexo(pessoaDadosPessoaisDTO.getSexo());
        dadosPessoaisDTO.setTituloEleitor(pessoaDadosPessoaisDTO.getTituloEleitor());

        dadosPessoaisService.post(dadosPessoaisDTO);
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
                    pessoaDadosPessoaisDTO.get(j).setCnh(dadosPessoaisDTO.get(j).getCnh());
                    pessoaDadosPessoaisDTO.get(j).setNomeMae(dadosPessoaisDTO.get(j).getNomeMae());
                    pessoaDadosPessoaisDTO.get(j).setNomePai(dadosPessoaisDTO.get(j).getNomePai());
                    pessoaDadosPessoaisDTO.get(j).setRg(dadosPessoaisDTO.get(j).getRg());
                    pessoaDadosPessoaisDTO.get(j).setSexo(dadosPessoaisDTO.get(j).getSexo());
                    pessoaDadosPessoaisDTO.get(j).setTituloEleitor(dadosPessoaisDTO.get(j).getTituloEleitor());
                }
            }
        }
        return pessoaDadosPessoaisDTO;
    }
}
