package mx.gob.tecdmx.seguridad.api.usuario;

import java.util.List;

public class DTOPayloadUsuario {
	
	int idUsuario;
	String usuario;
	String contrasenia;
	Integer statusCuenta;
	List<String> codigoRol;
	String email;
	String codigoSistema;
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public Integer getStatusCuenta() {
		return statusCuenta;
	}
	public void setStatusCuenta(Integer statusCuenta) {
		this.statusCuenta = statusCuenta;
	}
	public List<String> getCodigoRol() {
		return codigoRol;
	}
	public void setCodigoRol(List<String> codigoRol) {
		this.codigoRol = codigoRol;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCodigoSistema() {
		return codigoSistema;
	}
	public void setCodigoSistema(String codigoSistema) {
		this.codigoSistema = codigoSistema;
	}
	
}
