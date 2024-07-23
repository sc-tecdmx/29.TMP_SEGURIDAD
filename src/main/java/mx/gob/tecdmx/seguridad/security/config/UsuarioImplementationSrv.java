/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.security.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.gob.tecdmx.seguridad.entity.SegUsuarios;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosRepository;

@Service
public class UsuarioImplementationSrv implements UserDetailsService {
	
	@Autowired
	private SegUsuariosRepository segUsuariosRepository;
	
	

	@Override
	public UserDetails loadUserByUsername(String emailAndIdSession) throws UsernameNotFoundException {
		String email = emailAndIdSession.split(":")[0];
		String session = emailAndIdSession.split(":")[1];
		int idSession = Integer.parseInt(session);
		String sys = emailAndIdSession.split(":")[2];
		Optional<SegUsuarios> credentials = segUsuariosRepository.findBysEmail(email);
		if (!credentials.isPresent()) {
			credentials = segUsuariosRepository.findBysUsuario(email);
			if (!credentials.isPresent()) {
//				throw new UsernameNotFoundException(email);
				return null;
			}
		}

		SegUsuarios user = credentials.get();

		List<GrantedAuthority> roles = new ArrayList();
		// Agregar aqui el rol o roles para esta aplicación.
		// roles.add(new SimpleGrantedAuthority("ADMIN"));
		UserDetails userDetail = new UsuarioSecurityDTO(user.getsUsuario(), user.getsContrasenia(), roles, user.getnIdUsuario(),
				user.getsEmail(), idSession, sys);

		return userDetail;
	}
	

}
