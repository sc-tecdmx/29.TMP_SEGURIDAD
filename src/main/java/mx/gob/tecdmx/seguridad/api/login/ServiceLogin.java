package mx.gob.tecdmx.seguridad.api.login;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mx.gob.tecdmx.seguridad.entity.SegLogSesion;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;
import mx.gob.tecdmx.seguridad.repository.SegLogSesionRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosRepository;
import mx.gob.tecdmx.seguridad.security.config.Constants;
import mx.gob.tecdmx.seguridad.security.config.UsuarioSecurityDTO;

@Service
public class ServiceLogin {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SegUsuariosRepository SegUsuariosRepository;

	@Autowired
	SegLogSesionRepository SegLogSesionRepository;

	public String encryptPassword(String password) {
		String newPassword = bCryptPasswordEncoder.encode(password);
		return newPassword;
	}

	public DTOResponseLogin login(DTOPayloadLogin payload, HttpServletResponse response) {
		DTOResponseLogin responseDto = new DTOResponseLogin();

		Optional<SegUsuarios> credentials = SegUsuariosRepository.findBysEmail(payload.getEmail());

		if (!credentials.isPresent()) {
			List<SegUsuarios> credentialList = SegUsuariosRepository.findAllBysUsuario(payload.getEmail());
			if (credentialList.size()<=0) {
				responseDto.setStatus("failed");
				responseDto.setMessage("El usuario o correo electrónico es incorrecto");
				return responseDto;
			}
		}

		// almacena el Log de la sesión
		SegLogSesion logSesión = new SegLogSesion();

		logSesión.setIdUsuario(credentials.get());
		logSesión.setFechaInicio(new Date());

		SegLogSesion lastSesionGuardada = null;
		Optional<SegLogSesion> lastSesionExist = SegLogSesionRepository.findTopByOrderByIdDesc();

		if (lastSesionExist.isPresent()) {
			logSesión.setChainSessionId(lastSesionExist.get());
			lastSesionGuardada = SegLogSesionRepository.save(logSesión);

		} else {
			lastSesionGuardada = SegLogSesionRepository.save(logSesión);
			logSesión.setChainSessionId(lastSesionGuardada);
			lastSesionGuardada = SegLogSesionRepository.save(logSesión);

		}

		boolean coincide = bCryptPasswordEncoder.matches(payload.getPassword(), credentials.get().getsContrasenia());
		if (coincide) {
			Map<String, Object> claims = new HashMap<>();
		    claims.put("iat", new Date());
		    claims.put("iss", Constants.ISSUER_INFO);
		    claims.put("sub", credentials.get().getsEmail());
		    claims.put("jti", lastSesionGuardada.getId());
		    claims.put("exp", new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME));
		    claims.put("sys", payload.getSys());
		    
			String token = Jwts.builder()
					
//					.setIssuedAt(new Date())
//					.setIssuer(Constants.ISSUER_INFO)
//					.setSubject(credentials.get().getsEmail())
//					.setId(lastSesionGuardada.getId() + "")
//					.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
					.setClaims(claims)
					.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
			response.addHeader("email", credentials.get().getsEmail());
			response.addHeader("nombre", credentials.get().getsUsuario());
			response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);

			responseDto.setStatus("success");
			responseDto.setMessage("Autenticación exitosa");
			responseDto.setToken(token);

		} else {
			responseDto.setStatus("failed");
			responseDto.setMessage("Autenticación fallida");
		}

		return responseDto;
	}

	public boolean logout(Authentication auth) {
		UsuarioSecurityDTO usuarioVO = (UsuarioSecurityDTO) auth.getDetails();
		Optional<SegLogSesion> sesionExist =SegLogSesionRepository.findById(Integer.parseInt(usuarioVO.getIdSession()));
		if(sesionExist.isPresent()) {
			
			 long tiempoTerminacion = System.currentTimeMillis();
			//almacena el Log de la sesión
			sesionExist.get().setFechaFin(new Date());
			sesionExist.get().setEndSesion(tiempoTerminacion);
			SegLogSesionRepository.save(sesionExist.get());
			
			return true;
		}
		
		
		return false;
		
	}
	
	public DTOResponseLogin updatePassword(DTOPayloadLogin payload) {
		DTOResponseLogin responseDto = new DTOResponseLogin();
		Optional<SegUsuarios> credentials = SegUsuariosRepository.findBysEmail(payload.getEmail());
		if (credentials.isPresent()) {
			credentials.get().setsContrasenia(bCryptPasswordEncoder.encode(payload.getPassword()));
			SegUsuariosRepository.save(credentials.get());
			responseDto.setStatus("La contraseña se ha actualizado satisfactoriamente");
		} else {
			responseDto.setStatus("El usuario ingresado no se encuentra registrado");
		}
		return responseDto;
	}


}
