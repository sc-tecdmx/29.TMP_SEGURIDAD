package mx.gob.tecdmx.seguridad.api.usuario;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mx.gob.tecdmx.seguridad.api.menu.PerfilDTO;


@JsonInclude(Include.NON_NULL)
public class DTOUserInfo {
	String email;
	String usuario;
	String nombre;
	String apellido1;
	String apellido2;
	int idUsuario;
	int idEmpleado;
	List<PerfilDTO> perfiles;
	String aplicacion;
	String statusCuenta;
	String idSession;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public List<PerfilDTO> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(List<PerfilDTO> perfiles) {
		this.perfiles = perfiles;
	}
	public String getAplicacion() {
		return aplicacion;
	}
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	public String getStatusCuenta() {
		return statusCuenta;
	}
	public void setStatusCuenta(String statusCuenta) {
		this.statusCuenta = statusCuenta;
	}
	public String getIdSession() {
		return idSession;
	}
	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}
}
