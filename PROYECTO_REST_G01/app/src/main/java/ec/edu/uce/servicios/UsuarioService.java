package ec.edu.uce.servicios;

import ec.edu.uce.modelo.Usuario;

public interface UsuarioService {

    Usuario save(Usuario usuario);
    Usuario findByUsername(String username);
    boolean login(String username, String password);
}
