package com.bxacosta.controllers;

import com.bxacosta.models.Usuario;
import com.bxacosta.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> save(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        Optional<Usuario> optionalUsuario = usuarioService.findByUsername(username);
        if (optionalUsuario.isPresent()) {
            return new ResponseEntity<>(optionalUsuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
