package mx.gob.tecdmx.seguridad.api.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mx.gob.tecdmx.seguridad.api.menu.DTOPermisos;
import mx.gob.tecdmx.seguridad.api.menu.PayloadMenu;
import mx.gob.tecdmx.seguridad.api.menu.ResponseBodyMenu;
import mx.gob.tecdmx.seguridad.api.menu.ServiceMenu;
import mx.gob.tecdmx.seguridad.entity.IDRolesModulos;
import mx.gob.tecdmx.seguridad.entity.SegModulos;
import mx.gob.tecdmx.seguridad.entity.SegRoles;
import mx.gob.tecdmx.seguridad.entity.SegRolesModulos;
import mx.gob.tecdmx.seguridad.repository.SegModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesModulosRepository;
import mx.gob.tecdmx.seguridad.repository.SegRolesRepository;
import mx.gob.tecdmx.seguridad.security.config.UsuarioSecurityDTO;

@Service
public class ServiceAdmin {

	@Autowired
	SegRolesRepository segRolesRepository;

	@Autowired
	SegRolesModulosRepository segRolesModulosRepository;

	@Autowired
	SegModulosRepository segModulosRepository;

	@Autowired
	ServiceMenu serviceMenu;

	public DTOResponseAdmin getPermisosByRol(DTOResponseAdmin response, int idRol, Authentication auth) {
		ResponseBodyMenu acceso = new ResponseBodyMenu();
		UsuarioSecurityDTO userSecurity = (UsuarioSecurityDTO) auth.getDetails();
		// obtengo el rol
		String nombreAplicativo = userSecurity.getSys();
		Optional<SegModulos> aplicacion = segModulosRepository.findByDescModulo(nombreAplicativo);
		if (aplicacion.isPresent()) {
			Optional<SegRoles> rol = segRolesRepository.findById(idRol);
			if (rol.isPresent()) {
				// obtengo la relacion rol-módulos
				List<SegRolesModulos> rolModulos = segRolesModulosRepository.findBySegRoles(rol.get());
				List<PayloadMenu> menu = new ArrayList<PayloadMenu>();
				for (SegRolesModulos rolMod : rolModulos) {
					if (rolMod.getSegModulos().getDescModulo().equals(aplicacion.get().getDescModulo())
							&& rolMod.getSegModulos().getNIdNivel().getDescNivel().equals("Aplicación")) {
						acceso.setAplicacion(rolMod.getSegModulos().getDescModulo());
						int idAplicacion = rolMod.getSegModulos().getId();
						menu = serviceMenu.fillMenu(rolModulos, menu, idAplicacion);
						acceso.setRol(rol.get().getDescripcion());
						acceso.setMenu(menu);

						response.setStatus("Success");
						response.setMessage("Información obtenida");
						response.setData(acceso);
						return response;

					}

				}
				response.setStatus("fail");
				response.setMessage("no se obtuvo información para este Rol");
				response.setData(acceso);

			}
		}

		return response;

	}

	public DTOResponseAdmin editarPermisosByRol(ResponseBodyMenu payload, DTOResponseAdmin response, int idRol, Authentication auth ) {
		UsuarioSecurityDTO userSecurity = (UsuarioSecurityDTO) auth.getDetails();
		if(userSecurity==null) {
			response.setMessage("No cuenta con permisos para realizar esta acción");
			return response;
		}
		IDRolesModulos idRolModulo = null;
		Optional<SegRolesModulos> rolToEdit = null;
		Optional<SegModulos> aplicacion = segModulosRepository.findByDescModulo(payload.getAplicacion());
		if (aplicacion.isPresent()) {
			Optional<SegRoles> rol = segRolesRepository.findById(idRol);

			if (rol.isPresent()) {
				for (PayloadMenu menu : payload.getMenu()) {
					// edicion para los módulos
					Optional<SegModulos> modulo = segModulosRepository.findByDescModulo(menu.getNombreModulo());
					idRolModulo = new IDRolesModulos();
					idRolModulo.setnIdRol(rol.get().getId());
					idRolModulo.setnIdModulo(modulo.get().getId());

					rolToEdit = segRolesModulosRepository.findById(idRolModulo);
					for (DTOPermisos permiso : menu.getPermisos()) {
						rolToEdit.get().setCrear(permiso.isCrear() ? "S" : "N");
						rolToEdit.get().setLeer(permiso.isLeer() ? "S" : "N");
						rolToEdit.get().setEditar(permiso.isEditar() ? "S" : "N");
						rolToEdit.get().setEliminar(permiso.isEliminar() ? "S" : "N");
						rolToEdit.get().setPublico(permiso.isPublico() ? "S" : "N");
						rolToEdit.get().setSessionId(userSecurity.getIdSession());

						segRolesModulosRepository.save(rolToEdit.get());
					}
					// consulta si existen submodulos dentro del módulo
					if (menu.getModulos().size() > 0) {
						for (PayloadMenu SubModulo : menu.getModulos()) {
							// edición para el caso de submodulos
							Optional<SegModulos> submoduloExist = segModulosRepository
									.findByDescModulo(SubModulo.getNombreModulo());
							idRolModulo = new IDRolesModulos();
							idRolModulo.setnIdRol(rol.get().getId());
							idRolModulo.setnIdModulo(submoduloExist.get().getId());

							rolToEdit = segRolesModulosRepository.findById(idRolModulo);

							for (DTOPermisos permiso : SubModulo.getPermisos()) {
								rolToEdit.get().setCrear(permiso.isCrear() ? "S" : "N");
								rolToEdit.get().setLeer(permiso.isLeer() ? "S" : "N");
								rolToEdit.get().setEditar(permiso.isEditar() ? "S" : "N");
								rolToEdit.get().setEliminar(permiso.isEliminar() ? "S" : "N");
								rolToEdit.get().setPublico(permiso.isPublico() ? "S" : "N");
								rolToEdit.get().setSessionId(userSecurity.getIdSession());

								segRolesModulosRepository.save(rolToEdit.get());
							}
						}
					}
				}

				response.setStatus("Success");
				response.setMessage("Actualización finalizada");
				return response;
			}
		}

		response.setStatus("fail");
		response.setMessage("no se puedo realizar la actualización para este Rol");
		return response;
	}

	public DTOResponseAdmin crearRol(PayloadRol payload, DTOResponseAdmin response,  Authentication auth) {
		UsuarioSecurityDTO userSecurity = (UsuarioSecurityDTO) auth.getDetails();
		if(userSecurity==null) {
			response.setMessage("No cuenta con permisos para realizar esta acción");
			return response;
		}
		
		Optional<SegRoles> rolExist = segRolesRepository.findByEtiquetaRol(payload.getCodigo());
		if (rolExist.isPresent()) {
			response.setStatus("Fail");
			response.setMessage("El rol que desea ingresar ya Existe");
			return response;
		} else {
			Optional<SegRoles> rolPadre = segRolesRepository.findById(payload.getRolPadre());
			if (rolPadre.isPresent()) {
				SegRoles newRol = new SegRoles();
				newRol.setEtiquetaRol(payload.getCodigo());
				newRol.setDescripcion(payload.getDescripcion());
				newRol.setRolPadreId(rolPadre.get());
				newRol.setRecActivo(1);
				newRol.setSessionId(userSecurity.getIdSession());
				segRolesRepository.save(newRol);

				response.setStatus("Succes");
				response.setMessage("El rol se ha guardado correctamente");

			} else {
				response.setStatus("Fail");
				response.setMessage("El rol padre no existe");
			}

		}
		// TODO Auto-generated method stub
		return response;
	}

	public DTOResponseAdmin asociarRolModulos(PayloadRolMenu payload, DTOResponseAdmin response, Authentication auth ) {
		UsuarioSecurityDTO userSecurity = (UsuarioSecurityDTO) auth.getDetails();
		if(userSecurity==null) {
			response.setMessage("No cuenta con permisos para realizar esta acción");
			return response;
		}
		int moduloId = 0;
		if (payload.getCodigoRol() != null) {
			Optional<SegRoles> rolExist = segRolesRepository.findByEtiquetaRol(payload.getCodigoRol());
			if (rolExist.isPresent()) {
				moduloId = payload.getModuloId();
				if (moduloId != 0) {
					Optional<SegModulos> moduloExist = segModulosRepository.findById(moduloId);
					if(!moduloExist.isPresent()) {
						response.setStatus("Fail");
						response.setMessage("El módulo seleccionado no existe");
						return response;
					}

					SegRolesModulos rolToSave = new SegRolesModulos();
					rolToSave.setnIdRol(rolExist.get().getId());
					rolToSave.setnIdModulo(moduloExist.get().getId());
					rolToSave.setCrear(payload.getPermisos().isCrear() ? "S" : "N");
					rolToSave.setLeer(payload.getPermisos().isLeer() ? "S" : "N");
					rolToSave.setEditar(payload.getPermisos().isEditar() ? "S" : "N");
					rolToSave.setEliminar(payload.getPermisos().isEliminar() ? "S" : "N");
					rolToSave.setPublico(payload.getPermisos().isPublico() ? "S" : "N");
					rolToSave.setSessionId(userSecurity.getIdSession());
					segRolesModulosRepository.save(rolToSave);
					
					response.setStatus("Succes");
					response.setMessage("La asignación se realizó correctamente");
					return response;

				}else {
					response.setStatus("Fail");
					response.setMessage("El módulo seleccionado no existe");
					return response;
				}

			}

		}
		response.setStatus("Fail");
		response.setMessage("El rol seleccionado no existe");
		// TODO Auto-generated method stub
		return response;
	}

}
