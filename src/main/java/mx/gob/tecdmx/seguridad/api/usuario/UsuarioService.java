/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.tecdmx.seguridad.api.usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mx.gob.tecdmx.seguridad.api.login.ServiceLogin;
import mx.gob.tecdmx.seguridad.api.menu.PerfilDTO;
import mx.gob.tecdmx.seguridad.api.menu.ServiceMenu;
import mx.gob.tecdmx.seguridad.entity.IDUsuariosModulos;
import mx.gob.tecdmx.seguridad.entity.SegCatEstadoUsuario;
import mx.gob.tecdmx.seguridad.entity.SegLogUsuario;
import mx.gob.tecdmx.seguridad.entity.SegModulos;
import mx.gob.tecdmx.seguridad.entity.SegRoles;
import mx.gob.tecdmx.seguridad.entity.SegRolesUsuarios;
import mx.gob.tecdmx.seguridad.entity.SegUsuarioEstadoUsuario;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;
import mx.gob.tecdmx.seguridad.entity.SegUsuariosModulos;
import mx.gob.tecdmx.seguridad.repository.SegCatEstadoUsuarioRepository;
import mx.gob.tecdmx.seguridad.repository.SegLogSesionRepository;
import mx.gob.tecdmx.seguridad.repository.SegLogUsuarioRepository;
import mx.gob.tecdmx.seguridad.repository.SegModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesUsuariosRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuarioEstadoUsuarioRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegUsuariosRepository;
import mx.gob.tecdmx.seguridad.security.config.UsuarioSecurityDTO;
import mx.gob.tecdmx.seguridad.utils.DTOResponse;

@Service
public class UsuarioService {

	@Autowired
	private SegUsuariosRepository SegUsuariosRepository;

	@Autowired
	SegLogUsuarioRepository segLogUsuarioRepository;

	@Autowired
	SegCatEstadoUsuarioRepository segCatEstadoUsuarioRepository;

	@Autowired
	SegUsuarioEstadoUsuarioRepository SegUsuarioEstadoUsuarioRepository;

	@Autowired
	SegRolesRepository SegRolesRepository;

	@Autowired
	SegLogSesionRepository SegLogSesionRepository;

	@Autowired
	SegRolesUsuariosRepository SegRolesUsuariosRepository;

	@Autowired
	SegUsuariosModulosRepository segUsuariosModulosRepository;

	@Autowired
	SegModulosRepository segModulosRepository;

	@Autowired
	ServiceLogin serviceLogin;

	@Autowired
	ServiceMenu menuService;

	public DTOResponse createUserNoAuth(DTOUsuario userDTO, DTOResponse response) {

		// verifica que el correo a utilizar no se encuentre en la bd de usuarios
		Optional<SegUsuarios> usuarioEmail = SegUsuariosRepository.findBysEmail(userDTO.getEmail());
		SegUsuarios usuarioStored = null;
		Optional<SegCatEstadoUsuario> statusUsuario = segCatEstadoUsuarioRepository.findByDescripcion("Activa");
		if (!usuarioEmail.isPresent()) {

			// Tabla usuario
			SegUsuarios usuario = new SegUsuarios();
			usuario.setsUsuario(userDTO.getUsuario());
			usuario.setsContrasenia(serviceLogin.encryptPassword(userDTO.getContrasenia()));
			usuario.setsDescUsuario(null);
			usuario.setsEmail(userDTO.getEmail());
			usuario.setnIdEstadoUsuario(statusUsuario.get());
			usuario.setsToken(null);
			usuarioStored = SegUsuariosRepository.save(usuario);

			// Tabla usuario-estadoUsuario
			SegUsuarioEstadoUsuario bitacoraEstatusUsuario = new SegUsuarioEstadoUsuario();
			bitacoraEstatusUsuario.setIdUsuario(usuarioStored);
			bitacoraEstatusUsuario.setIdEstadoUsuario(statusUsuario.get());
			bitacoraEstatusUsuario.setFingerprintDispositivo(null);
			bitacoraEstatusUsuario.setFechaStatus(new Date());
			// NOTA: No se setea la sesión ya que no hay autenticación
			SegUsuarioEstadoUsuarioRepository.save(bitacoraEstatusUsuario);

		} else {
			usuarioStored = usuarioEmail.get();
			Optional<SegModulos> modulo = segModulosRepository.findByDescModulo(userDTO.getSistema());

			IDUsuariosModulos usuModId = new IDUsuariosModulos();
			usuModId.setNIdModulo(modulo.get().getId());
			usuModId.setNIdUsuario(usuarioEmail.get().getnIdUsuario());

			Optional<SegUsuariosModulos> usuMod = segUsuariosModulosRepository.findById(usuModId);
			if (usuMod.isPresent()) {
				response.setMessage("Este correo ya ha sido utilizado en este sistema");
				response.setStatus("Fail");
				response.setData(null);
				return response;
			}

		}
		// Tabla Roles-Usuarios
		Optional<SegRoles> rol = SegRolesRepository.findByEtiquetaRol("SA");
		SegRolesUsuarios rolesUsuarios = new SegRolesUsuarios();
		rolesUsuarios.setIdRol(rol.get());
		rolesUsuarios.setIdUsuario(usuarioStored);
		// NOTA: No se setea la sesión ya que no hay autenticación
		// NOTA: No se setea el empleado puesto area, ya que no tenemos este dato para
		// este ejecicio
		SegRolesUsuariosRepository.save(rolesUsuarios);

		Optional<SegModulos> modulo = segModulosRepository.findByDescModulo(userDTO.getSistema());

		// Tabla usuario-modulos
		SegUsuariosModulos usuariosModulos = new SegUsuariosModulos();
		usuariosModulos.setNIdUsuario(usuarioStored.getnIdUsuario());
		usuariosModulos.setNIdModulo(modulo.get().getId());
		usuariosModulos.setFechaAlta(new Date());
		usuariosModulos.setFechaBaja(null);
		// Nota: No guardo la sesión
		segUsuariosModulosRepository.save(usuariosModulos);

		// almacena el Log del usuario
		SegLogUsuario userLog = new SegLogUsuario();
		userLog.setIdUsuario(usuarioStored);
//				userLog.setN_session_id(sesionExist.get().getId());
		userLog.setDSistema(new Date());
		userLog.setBitacora("creado");
		segLogUsuarioRepository.save(userLog);
		
		DTOUsuario dtoUser = new DTOUsuario();
		dtoUser.setIdUsuario(usuarioStored.getnIdUsuario());
		dtoUser.setEmail(usuarioStored.getsEmail());
		dtoUser.setUsuario(usuarioStored.getsUsuario());
		dtoUser.setCodigoRol(rol.get().getEtiquetaRol());
		dtoUser.setEstatusCuenta(statusUsuario.get().getDescripcion());
		dtoUser.setSistema(userDTO.getSistema());

		response.setData(dtoUser);
		response.setMessage("El usuario se han creado correctamente");
		response.setStatus("Success");
		return response;

	}

	public DTOResponse userInfo(Authentication auth, DTOResponse response) {
		UsuarioSecurityDTO usuarioVO = (UsuarioSecurityDTO) auth.getDetails();
		Optional<SegUsuarios> usuario = SegUsuariosRepository.findBysEmail(usuarioVO.getEmail());
		if (usuario.isPresent()) {
			DTOUserInfo userInfo = new DTOUserInfo();
			userInfo.setUsuario(usuario.get().getsUsuario());
			userInfo.setEmail(usuario.get().getsEmail());
			userInfo.setIdUsuario(usuario.get().getnIdUsuario());

			List<PerfilDTO> perfiles = menuService.getMenu(auth);
			userInfo.setPerfiles(perfiles);
			response.setData(userInfo);
			response.setMessage("EL token es válido");
			response.setStatus("Success");
			return response;
		}
		response.setMessage("EL token es inválido");
		response.setStatus("Fail");
		return response;
	}
	
	public DTOResponse getUsuarios(DTOResponse response) {
//		DTOUserInfo userInfo = null;
//		List<DTOUserInfo> usuariosInfo = new ArrayList<DTOUserInfo>();
//		Iterable<SegUsuarios> usuarios = SegUsuariosRepository.findAll();
//		for (SegUsuarios usuario : usuarios) {
//			
//			Optional<InstEmpleado> empleado = instEmpleadoRepository.findByIdUsuario(usuario);
//			userInfo = new DTOUserInfo();
//
//			userInfo.setIdUsuario(usuario.getnIdUsuario());
//			userInfo.setNombre(empleado.get().getNombre());
//			userInfo.setApellido1(empleado.get().getApellido1());
//			userInfo.setApellido2(empleado.get().getApellido2());
//			userInfo.setIdEmpleado(empleado.get().getId());
//			userInfo.setUsuario(usuario.getsUsuario());
//			userInfo.setEmail(usuario.getsEmail());
//			
//			Optional<SegCatEstadoUsuario> idEstado = segCatEstadoUsuarioRepository.findById(usuario.getnIdEstadoUsuario().getN_id_estado_usuario());
//			
//			userInfo.setStatusCuenta(idEstado.get().getDescripcion());
//
//			ResponseBodyMenu menu = new ResponseBodyMenu();
//			List<PerfilDTO> perfiles = serviceMenu.getMenu(menu, usuario);
//			userInfo.setPerfiles(perfiles);
//			
////			userInfo.setRol(menu.getRol());
////			userInfo.setAplicacion(menu.getAplicacion());
////			menu.setMenu(null);
//
//			usuariosInfo.add(userInfo);
//		}
//
//		response.setMessage("Información de los Usuarios");
//		response.setStatus("Success");
//		response.setData(usuariosInfo);
		return response;
	}


}
