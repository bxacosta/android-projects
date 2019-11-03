package ec.edu.uce.servicios;

import java.util.concurrent.ExecutionException;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.rest.AsyncRestClient;
import ec.edu.uce.rest.RestClient;

public class UsuarioServiceRest implements UsuarioService {

    private RestClient rest;

    public UsuarioServiceRest() {
        rest = AsyncRestClient.getRest();
    }

    @Override
    public Usuario save(Usuario usuario) {
        try {
            return new AsyncRestClient<Usuario>().execute(rest.saveUsuario(usuario)).get();
        } catch (Exception e) {
            throw new CustomException("Error al guardar el usuario", e);
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        try {
            return new AsyncRestClient<Usuario>().execute(rest.findUsuarioByUsername(username)).get();
        } catch (Exception e) {
            throw new CustomException("Error al buscar el usuario", e);
        }
    }

    @Override
    public boolean login(String username, String password) {
        Usuario usuario = findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
