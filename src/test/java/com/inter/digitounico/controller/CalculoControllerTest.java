package com.inter.digitounico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inter.digitounico.dto.CalculoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CalculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCalcularDigitoUnico_Sucesso() throws Exception {
        CalculoRequest request = new CalculoRequest("9875", 4, null);

        mockMvc.perform(post("/api/calculos/digito-unico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.digitoUnico").value(8))
                .andExpect(jsonPath("$.n").value("9875"))
                .andExpect(jsonPath("$.k").value(4))
                .andExpect(jsonPath("$.fromCache").value(false));
    }

    @Test
    void testCalcularDigitoUnico_SegundaVezCache() throws Exception {
        CalculoRequest request = new CalculoRequest("123", 2, null);

        // Primeira chamada
        mockMvc.perform(post("/api/calculos/digito-unico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCache").value(false));

        // Segunda chamada - deve vir do cache
        mockMvc.perform(post("/api/calculos/digito-unico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCache").value(true));
    }

    @Test
    void testCalcularDigitoUnico_ParametrosInvalidos() throws Exception {
        CalculoRequest request = new CalculoRequest("", 0, null);

        mockMvc.perform(post("/api/calculos/digito-unico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testBuscarCalculosUsuario_UsuarioInexistente() throws Exception {
        mockMvc.perform(get("/api/calculos/usuario/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
