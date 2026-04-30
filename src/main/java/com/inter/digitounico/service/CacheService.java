package com.inter.digitounico.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CacheService {

    private static final int MAX_SIZE = 10;

    // LinkedHashMap mantém ordem de inserção para implementar LRU
    private final Map<String, Integer> cache = new LinkedHashMap<String, Integer>(MAX_SIZE + 1, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            return size() > MAX_SIZE;
        }
    };

    /**
     * Gera chave única para o cache baseada nos parâmetros
     */
    private String gerarChave(String n, int k) {
        return n + "_" + k;
    }

    /**
     * Busca resultado no cache
     */
    public Integer buscarNoCache(String n, int k) {
        String chave = gerarChave(n, k);
        return cache.get(chave);
    }

    /**
     * Armazena resultado no cache
     */
    public void armazenarNoCache(String n, int k, int resultado) {
        String chave = gerarChave(n, k);
        cache.put(chave, resultado);
    }

    /**
     * Verifica se existe no cache
     */
    public boolean existeNoCache(String n, int k) {
        String chave = gerarChave(n, k);
        return cache.containsKey(chave);
    }

    /**
     * Limpa o cache
     */
    public void limparCache() {
        cache.clear();
    }

    /**
     * Retorna tamanho atual do cache
     */
    public int tamanhoCache() {
        return cache.size();
    }
}
