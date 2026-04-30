package com.inter.digitounico.service;

import org.springframework.stereotype.Service;

@Service
public class DigitoUnicoService {

    /**
     * Calcula o dígito único de um número concatenado k vezes
     * @param n string representando o número
     * @param k número de concatenações
     * @return dígito único calculado
     */
    public int calcularDigitoUnico(String n, int k) {
        // Calcula a soma dos dígitos de n
        int somaDigitos = calcularSomaDigitos(n);

        // Multiplica pela quantidade de concatenações
        long somaTotal = (long) somaDigitos * k;

        // Calcula o dígito único da soma total
        return calcularDigitoUnico(somaTotal);
    }

    /**
     * Calcula a soma dos dígitos de uma string numérica
     */
    private int calcularSomaDigitos(String numero) {
        int soma = 0;
        for (char c : numero.toCharArray()) {
            soma += Character.getNumericValue(c);
        }
        return soma;
    }

    /**
     * Calcula o dígito único de um número
     */
    private int calcularDigitoUnico(long numero) {
        while (numero >= 10) {
            numero = calcularSomaDigitos(String.valueOf(numero));
        }
        return (int) numero;
    }
}
