package mx.gob.tecdmx.seguridad.api.menu;

public class DTOPermisos {
	String codigoRol;
	boolean crear;
	boolean leer;
	boolean editar;
	boolean eliminar;
	boolean publico;
	
	public String getCodigoRol() {
		return codigoRol;
	}
	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}
	public boolean isCrear() {
		return crear;
	}
	public void setCrear(boolean crear) {
		this.crear = crear;
	}
	public boolean isLeer() {
		return leer;
	}
	public void setLeer(boolean leer) {
		this.leer = leer;
	}
	public boolean isEditar() {
		return editar;
	}
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	public boolean isEliminar() {
		return eliminar;
	}
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}
	public boolean isPublico() {
		return publico;
	}
	public void setPublico(boolean publico) {
		this.publico = publico;
	}
	
}
