package com.inter.digitounico.service;

import com.inter.digitounico.dto.CalculoRequest;
import com.inter.digitounico.dto.CalculoResponse;
import com.inter.digitounico.entity.Resultado;
import com.inter.digitounico.entity.Usuario;
import com.inter.digitounico.repository.ResultadoRepository;
import com.inter.digitounico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalculoService {

    @Autowired
    private DigitoUnicoService digitoUnicoService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CalculoResponse calcularDigitoUnico(CalculoRequest request) {
        // Verifica se existe no cache
        Integer resultadoCache = cacheService.buscarNoCache(request.getN(), request.getK());

        if (resultadoCache != null) {
            return new CalculoResponse(
                request.getN(),
                request.getK(),
                resultadoCache,
                LocalDateTime.now(),
                true
            );
        }

        // Calcula o dígito único
        int digitoUnico = digitoUnicoService.calcularDigitoUnico(request.getN(), request.getK());

        // Armazena no cache
        cacheService.armazenarNoCache(request.getN(), request.getK(), digitoUnico);

        // Se há usuário associado, salva o resultado
        if (request.getUsuarioId() != null) {
            Optional<Usuario> usuario = usuarioRepository.findById(request.getUsuarioId());
            if (usuario.isPresent()) {
                Resultado resultado = new Resultado(request.getN(), request.getK(), digitoUnico, usuario.get());
                resultadoRepository.save(resultado);
            }
        }

        return new CalculoResponse(
            request.getN(),
            request.getK(),
            digitoUnico,
            LocalDateTime.now(),
            false
        );
    }

    public List<Resultado> buscarCalculosUsuario(Long usuarioId) {
        return resultadoRepository.findByUsuarioIdOrderByDataCalculoDesc(usuarioId);
    }
}
