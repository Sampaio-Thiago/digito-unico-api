package com.inter.digitounico.service;

import com.inter.digitounico.dto.UsuarioDTO;
import com.inter.digitounico.entity.Usuario;
import com.inter.digitounico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CriptografiaService criptografiaService;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) throws Exception {
        // Se não há chave pública, gera uma nova
        if (usuarioDTO.getId() == null) {
            throw new IllegalArgumentException("Para criar usuário, é necessário primeiro enviar a chave pública");
        }

        // Busca usuário existente que deve ter apenas a chave pública
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuarioDTO.getId());
        if (usuarioExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Usuario usuario = usuarioExistente.get();

        // Criptografa os dados
        String nomeCriptografado = criptografiaService.criptografar(usuarioDTO.getNome(), usuario.getChavePublica());
        String emailCriptografado = criptografiaService.criptografar(usuarioDTO.getEmail(), usuario.getChavePublica());

        usuario.setNomeCriptografado(nomeCriptografado);
        usuario.setEmailCriptografado(emailCriptografado);

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getChavePublica() == null) {
            throw new IllegalArgumentException("Usuário não possui chave pública configurada");
        }

        // Criptografa os novos dados
        String nomeCriptografado = criptografiaService.criptografar(usuarioDTO.getNome(), usuario.getChavePublica());
        String emailCriptografado = criptografiaService.criptografar(usuarioDTO.getEmail(), usuario.getChavePublica());

        usuario.setNomeCriptografado(nomeCriptografado);
        usuario.setEmailCriptografado(emailCriptografado);

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario definirChavePublica(String chavePublica) {
        Usuario usuario = new Usuario();
        usuario.setChavePublica(chavePublica);
        return usuarioRepository.save(usuario);
    }

    public UsuarioDTO descriptografarDados(Usuario usuario, String chavePrivada) throws Exception {
        String nome = criptografiaService.descriptografar(usuario.getNomeCriptografado(), chavePrivada);
        String email = criptografiaService.descriptografar(usuario.getEmailCriptografado(), chavePrivada);

        return new UsuarioDTO(usuario.getId(), nome, email);
    }
}
