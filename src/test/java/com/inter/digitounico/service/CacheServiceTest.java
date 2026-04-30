package com.inter.digitounico.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        cacheService = new CacheService();
    }

    @Test
    void testArmazenarEBuscarNoCache() {
        String n = "123";
        int k = 2;
        int resultado = 6;

        cacheService.armazenarNoCache(n, k, resultado);

        Integer resultadoCache = cacheService.buscarNoCache(n, k);

        assertEquals(resultado, resultadoCache);
        assertTrue(cacheService.existeNoCache(n, k));
    }

    @Test
    void testCacheVazio() {
        String n = "456";
        int k = 3;

        Integer resultado = cacheService.buscarNoCache(n, k);

        assertNull(resultado);
        assertFalse(cacheService.existeNoCache(n, k));
    }

    @Test
    void testLimiteCache() {
        // Adiciona 12 elementos (mais que o limite de 10)
        for (int i = 1; i <= 12; i++) {
            cacheService.armazenarNoCache(String.valueOf(i), 1, i);
        }

        // Verifica que o cache tem exatamente 10 elementos
        assertEquals(10, cacheService.tamanhoCache());

        // Os primeiros 2 elementos devem ter sido removidos
        assertFalse(cacheService.existeNoCache("1", 1));
        assertFalse(cacheService.existeNoCache("2", 1));

        // Os últimos 10 devem estar presentes
        assertTrue(cacheService.existeNoCache("3", 1));
        assertTrue(cacheService.existeNoCache("12", 1));
    }

    @Test
    void testLimparCache() {
        cacheService.armazenarNoCache("123", 1, 6);
        cacheService.armazenarNoCache("456", 2, 6);

        assertEquals(2, cacheService.tamanhoCache());

        cacheService.limparCache();

        assertEquals(0, cacheService.tamanhoCache());
        assertFalse(cacheService.existeNoCache("123", 1));
        assertFalse(cacheService.existeNoCache("456", 2));
    }
}
