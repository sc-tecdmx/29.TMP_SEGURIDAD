package mx.gob.tecdmx.seguridad.api.usuario;

public class DTOUsuario {
	int idUsuario;
	String usuario;
	String contrasenia;
	String estatusCuenta;
	String codigoRol;
	String rolAnterior;
	String email;
	String sistema;
	
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
	public String getEstatusCuenta() {
		return estatusCuenta;
	}
	public void setEstatusCuenta(String estatusCuenta) {
		this.estatusCuenta = estatusCuenta;
	}
	public String getCodigoRol() {
		return codigoRol;
	}
	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRolAnterior() {
		return rolAnterior;
	}
	public void setRolAnterior(String rolAnterior) {
		this.rolAnterior = rolAnterior;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
}
