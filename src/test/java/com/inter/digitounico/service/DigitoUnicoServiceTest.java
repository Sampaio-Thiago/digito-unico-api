package com.inter.digitounico.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DigitoUnicoServiceTest {

    @InjectMocks
    private DigitoUnicoService digitoUnicoService;

    @Test
    void testCalcularDigitoUnico_ExemploBasico() {
        // Teste do exemplo fornecido: n=9875, k=4
        String n = "9875";
        int k = 4;

        int resultado = digitoUnicoService.calcularDigitoUnico(n, k);

        assertEquals(8, resultado);
    }

    @Test
    void testCalcularDigitoUnico_NumeroSimples() {
        String n = "5";
        int k = 1;

        int resultado = digitoUnicoService.calcularDigitoUnico(n, k);

        assertEquals(5, resultado);
    }

    @Test
    void testCalcularDigitoUnico_MultiplasConcatenacoes() {
        String n = "12";
        int k = 3;
        // 12 concatenado 3 vezes = 121212
        // Soma dos dígitos de 12 = 1+2 = 3
        // 3 * 3 = 9
        // Dígito único de 9 = 9

        int resultado = digitoUnicoService.calcularDigitoUnico(n, k);

        assertEquals(9, resultado);
    }

    @Test
    void testCalcularDigitoUnico_NumeroGrande() {
        String n = "999";
        int k = 2;
        // Soma dos dígitos de 999 = 9+9+9 = 27
        // 27 * 2 = 54
        // Dígito único de 54 = 5+4 = 9

        int resultado = digitoUnicoService.calcularDigitoUnico(n, k);

        assertEquals(9, resultado);
    }

    @Test
    void testCalcularDigitoUnico_Zero() {
        String n = "0";
        int k = 5;

        int resultado = digitoUnicoService.calcularDigitoUnico(n, k);

        assertEquals(0, resultado);
    }
}
