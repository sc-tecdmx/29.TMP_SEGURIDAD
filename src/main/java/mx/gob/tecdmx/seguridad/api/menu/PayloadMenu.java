package mx.gob.tecdmx.seguridad.api.menu;

import java.util.List;
public class PayloadMenu {
	int idModulo;
	String nivelModulo;
	String nombreModulo;
	String codigo;
	String url;
	int pos;
	int padreId;
	
	List<PayloadMenu> modulos;
	List<DTOPermisos> permisos;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNivelModulo() {
		return nivelModulo;
	}
	public void setNivelModulo(String nivelModulo) {
		this.nivelModulo = nivelModulo;
	}
	public String getNombreModulo() {
		return nombreModulo;
	}
	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public List<PayloadMenu> getModulos() {
		return modulos;
	}
	public void setModulos(List<PayloadMenu> modulos) {
		this.modulos = modulos;
	}
	public List<DTOPermisos> getPermisos() {
		return permisos;
	}
	public void setPermisos(List<DTOPermisos> permisos) {
		this.permisos = permisos;
	}
	public int getPadreId() {
		return padreId;
	}
	public void setPadreId(int padreId) {
		this.padreId = padreId;
	}
	public int getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
	}
	

}
