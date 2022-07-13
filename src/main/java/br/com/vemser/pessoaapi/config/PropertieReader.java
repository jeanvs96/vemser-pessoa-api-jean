package br.com.vemser.pessoaapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertieReader {

    @Value("${ambiente}")
    private String ambiente;
}
