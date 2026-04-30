package com.inter.digitounico.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

class CriptografiaServiceTest {

    private CriptografiaService criptografiaService;
    private KeyPair parChaves;

    @BeforeEach
    void setUp() throws Exception {
        criptografiaService = new CriptografiaService();
        parChaves = criptografiaService.gerarParChaves();
    }

    @Test
    void testGerarParChaves() throws Exception {
        KeyPair novoParChaves = criptografiaService.gerarParChaves();

        assertNotNull(novoParChaves);
        assertNotNull(novoParChaves.getPublic());
        assertNotNull(novoParChaves.getPrivate());
        assertEquals("RSA", novoParChaves.getPublic().getAlgorithm());
        assertEquals("RSA", novoParChaves.getPrivate().getAlgorithm());
    }

    @Test
    void testConversaoChavePublica() throws Exception {
        String chavePublicaString = criptografiaService.chavePublicaParaString(parChaves.getPublic());

        assertNotNull(chavePublicaString);
        assertFalse(chavePublicaString.isEmpty());

        var chavePublicaReconstruida = criptografiaService.stringParaChavePublica(chavePublicaString);

        assertEquals(parChaves.getPublic(), chavePublicaReconstruida);
    }

    @Test
    void testConversaoChavePrivada() throws Exception {
        String chavePrivadaString = criptografiaService.chavePrivadaParaString(parChaves.getPrivate());

        assertNotNull(chavePrivadaString);
        assertFalse(chavePrivadaString.isEmpty());

        var chavePrivadaReconstruida = criptografiaService.stringParaChavePrivada(chavePrivadaString);

        assertEquals(parChaves.getPrivate(), chavePrivadaReconstruida);
    }

    @Test
    void testCriptografiaDescriptografia() throws Exception {
        String textoOriginal = "Teste de criptografia RSA";
        String chavePublicaString = criptografiaService.chavePublicaParaString(parChaves.getPublic());
        String chavePrivadaString = criptografiaService.chavePrivadaParaString(parChaves.getPrivate());

        // Criptografa com chave pública
        String textoCriptografado = criptografiaService.criptografar(textoOriginal, chavePublicaString);

        assertNotNull(textoCriptografado);
        assertNotEquals(textoOriginal, textoCriptografado);

        // Descriptografa com chave privada
        String textoDescriptografado = criptografiaService.descriptografar(textoCriptografado, chavePrivadaString);

        assertEquals(textoOriginal, textoDescriptografado);
    }

    @Test
    void testCriptografiaCaracteresEspeciais() throws Exception {
        String textoOriginal = "Texto com acentuação: José, ção, não!";
        String chavePublicaString = criptografiaService.chavePublicaParaString(parChaves.getPublic());
        String chavePrivadaString = criptografiaService.chavePrivadaParaString(parChaves.getPrivate());

        String textoCriptografado = criptografiaService.criptografar(textoOriginal, chavePublicaString);
        String textoDescriptografado = criptografiaService.descriptografar(textoCriptografado, chavePrivadaString);

        assertEquals(textoOriginal, textoDescriptografado);
    }
}
