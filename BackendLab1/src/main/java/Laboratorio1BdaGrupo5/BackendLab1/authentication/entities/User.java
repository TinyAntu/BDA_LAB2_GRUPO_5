package Laboratorio1BdaGrupo5.BackendLab1.authentication.entities;

import Laboratorio1BdaGrupo5.BackendLab1.models.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String name;
    private String email;
    private String password;
    private static String ROLE = "ROLE_CLIENTE";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public static User clienteToUser(Cliente cliente) {
        return new User(cliente.getId_cliente().longValue(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword());
    }

    public Map<String, Object> generateExtraClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("name", name);
        return claims;
    }

}
