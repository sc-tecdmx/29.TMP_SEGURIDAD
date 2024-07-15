/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.security.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioSecurityDTO extends User {

	private Integer idUsuario;
	private String email;
	private String idSession;
	private String sys;
    
    public UsuarioSecurityDTO(Integer idUsuario, String username, String email, String idSession, String sys){
        super(username, "", new ArrayList<GrantedAuthority>());
        this.idUsuario = idUsuario;
        this.email = email;
        this.idSession = idSession;
        this.sys = sys;
    }

    public UsuarioSecurityDTO(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer idUsuario, String email, String idSession, String sys) {
        super(username, password, authorities);
        this.idUsuario = idUsuario;
        this.email = email;
        this.idSession = idSession;
        this.sys = sys;
    }
    
}
