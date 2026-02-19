package br.com.eicon.api.consultacredito.api.v1.controller.impl;

import br.com.eicon.api.consultacredito.api.v1.controller.CreditoController;
import br.com.eicon.api.consultacredito.api.v1.dto.CreditoResponseDto;
import br.com.eicon.api.consultacredito.domain.service.ConversorUtil;
import br.com.eicon.api.consultacredito.domain.service.CreditoService;
import br.com.eicon.api.consultacredito.jpa.entity.Credito;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/creditos")
@RequiredArgsConstructor
public class CreditoControllerImpl implements CreditoController {

    private final CreditoService creditoService;

    @Override
    @GetMapping("/")
    public ResponseEntity<Collection<CreditoResponseDto>> listarTodasNfe() {
        Collection<Credito> lista = creditoService.buscarTodas();
        Collection<CreditoResponseDto> retorno = lista.stream()
                .map(c -> ConversorUtil.converter(c, CreditoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retorno);
    }

    @Override
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<Collection<CreditoResponseDto>> buscarPorNfse(@PathVariable String numeroNfse) {
        Collection<Credito> lista = creditoService.buscarPorNumeroNfse(numeroNfse);
        Collection<CreditoResponseDto> retorno = lista.stream()
                .map(c -> ConversorUtil.converter(c, CreditoResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retorno);
    }

    @Override
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoResponseDto> buscarPorNumero(@PathVariable String numeroCredito) {
        Credito credito = creditoService.buscarPorNumeroCredito(numeroCredito);
        return ResponseEntity.ok(ConversorUtil.converter(credito, CreditoResponseDto.class));
    }
}
