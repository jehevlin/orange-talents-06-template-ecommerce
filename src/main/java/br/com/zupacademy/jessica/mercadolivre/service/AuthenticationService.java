package br.com.zupacademy.jessica.mercadolivre.service;

import br.com.zupacademy.jessica.mercadolivre.model.Usuario;
import br.com.zupacademy.jessica.mercadolivre.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class AuthenticationService implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public AuthenticationService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(username);

        String password = "";

        if (usuario.isPresent()) {
            password = usuario.get().getSenha();
        }

        return new User(username, password, new ArrayList<>());
    }
}
