package com.inter.digitounico.controller;

import com.inter.digitounico.dto.ChavePrivadaRequest;
import com.inter.digitounico.dto.ChavePublicaRequest;
import com.inter.digitounico.dto.UsuarioDTO;
import com.inter.digitounico.entity.Usuario;
import com.inter.digitounico.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "CRUD de usuários com criptografia RSA")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/chave-publica")
    @Operation(summary = "Enviar chave pública para criptografia")
    public ResponseEntity<Usuario> enviarChavePublica(@Valid @RequestBody ChavePublicaRequest request) {
        try {
            Usuario usuario = usuarioService.definirChavePublica(request.getChavePublica());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.criarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.atualizarUsuario(id, usuarioDTO);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/descriptografar")
    @Operation(summary = "Descriptografar dados do usuário",
               description = "Retorna nome e email em texto claro usando a chave privada RSA do cliente. A chave privada deve estar em formato Base64 puro (DER/PKCS8).")
    public ResponseEntity<UsuarioDTO> descriptografarDados(@PathVariable Long id,
                                                           @Valid @RequestBody ChavePrivadaRequest request) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            UsuarioDTO dto = usuarioService.descriptografarDados(usuario, request.getChavePrivada());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
