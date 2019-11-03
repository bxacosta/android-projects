package com.bxacosta.services;

import com.bxacosta.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final InitApp initApp;
    private List<Usuario> usuarios;

    public UsuarioService(InitApp initApp) {
        this.initApp = initApp;
        this.usuarios = initApp.getUsuarios();
    }

    public Usuario save(Usuario usuario) {
        usuarios.add(usuario);
        return usuario;
    }

    public List<Usuario> findAll() {
        return usuarios;
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarios.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }
}
