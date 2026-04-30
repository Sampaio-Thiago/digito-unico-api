package com.inter.digitounico.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class CriptografiaService {

    private static final String ALGORITMO = "RSA";
    private static final int TAMANHO_CHAVE = 2048;

    /**
     * Gera um par de chaves RSA
     */
    public KeyPair gerarParChaves() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITMO);
        keyGen.initialize(TAMANHO_CHAVE);
        return keyGen.generateKeyPair();
    }

    /**
     * Converte chave pública para string Base64
     */
    public String chavePublicaParaString(PublicKey chavePublica) {
        return Base64.getEncoder().encodeToString(chavePublica.getEncoded());
    }

    /**
     * Converte chave privada para string Base64
     */
    public String chavePrivadaParaString(PrivateKey chavePrivada) {
        return Base64.getEncoder().encodeToString(chavePrivada.getEncoded());
    }

    /**
     * Converte string Base64 para chave pública
     */
    public PublicKey stringParaChavePublica(String chavePublicaString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(chavePublicaString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITMO);
        return keyFactory.generatePublic(spec);
    }

    /**
     * Converte string Base64 para chave privada
     */
    public PrivateKey stringParaChavePrivada(String chavePrivadaString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(chavePrivadaString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITMO);
        return keyFactory.generatePrivate(spec);
    }

    /**
     * Criptografa texto usando chave pública
     */
    public String criptografar(String texto, String chavePublicaString) throws Exception {
        PublicKey chavePublica = stringParaChavePublica(chavePublicaString);
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, chavePublica);

        byte[] textoCriptografado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(textoCriptografado);
    }

    /**
     * Descriptografa texto usando chave privada
     */
    public String descriptografar(String textoCriptografado, String chavePrivadaString) throws Exception {
        PrivateKey chavePrivada = stringParaChavePrivada(chavePrivadaString);
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, chavePrivada);

        byte[] textoDescriptografado = cipher.doFinal(Base64.getDecoder().decode(textoCriptografado));
        return new String(textoDescriptografado, StandardCharsets.UTF_8);
    }
}
