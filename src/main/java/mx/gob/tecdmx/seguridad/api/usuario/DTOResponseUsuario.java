package mx.gob.tecdmx.seguridad.api.usuario;

import java.util.List;

public class DTOResponseUsuario {
	int idUsuario;
	String usuario;
	String estatusCuenta;
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
	public String getEstatusCuenta() {
		return estatusCuenta;
	}
	public void setEstatusCuenta(String estatusCuenta) {
		this.estatusCuenta = estatusCuenta;
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
