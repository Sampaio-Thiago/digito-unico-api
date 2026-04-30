package com.inter.digitounico.controller;

import com.inter.digitounico.dto.CalculoRequest;
import com.inter.digitounico.dto.CalculoResponse;
import com.inter.digitounico.entity.Resultado;
import com.inter.digitounico.service.CalculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calculos")
@Tag(name = "Cálculos", description = "Operações de cálculo de dígito único")
public class CalculoController {

    @Autowired
    private CalculoService calculoService;

    @PostMapping("/digito-unico")
    @Operation(summary = "Calcular dígito único", 
               description = "Calcula o dígito único de um número n concatenado k vezes")
    public ResponseEntity<CalculoResponse> calcularDigitoUnico(@Valid @RequestBody CalculoRequest request) {
        try {
            CalculoResponse response = calculoService.calcularDigitoUnico(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar cálculos de um usuário",
               description = "Retorna todos os cálculos realizados por um usuário específico")
    public ResponseEntity<List<Resultado>> buscarCalculosUsuario(@PathVariable Long usuarioId) {
        try {
            List<Resultado> resultados = calculoService.buscarCalculosUsuario(usuarioId);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
