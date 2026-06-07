package sinalizaai.sinalizaai_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sinalizaai.sinalizaai_back.domain.cliente.ClienteRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails cliente = repository.findByEmail(email);

        if(cliente == null) {
            throw new UsernameNotFoundException("Cliente não encontrado: " + email);
        }

        return cliente;
    }
}
