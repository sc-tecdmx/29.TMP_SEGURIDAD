package mx.gob.tecdmx.seguridad.api.Admin;

public class PayloadRol {
	
	String codigo;
	String descripcion;
	int rolPadre;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getRolPadre() {
		return rolPadre;
	}
	public void setRolPadre(int rolPadre) {
		this.rolPadre = rolPadre;
	}
	

}
