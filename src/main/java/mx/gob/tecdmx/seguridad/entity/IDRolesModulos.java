package mx.gob.tecdmx.seguridad.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IDRolesModulos implements Serializable {
	
    private Integer nIdRol;
    private Integer nIdModulo;
	public Integer getnIdRol() {
		return nIdRol;
	}
	public void setnIdRol(Integer nIdRol) {
		this.nIdRol = nIdRol;
	}
	public Integer getnIdModulo() {
		return nIdModulo;
	}
	public void setnIdModulo(Integer nIdModulo) {
		this.nIdModulo = nIdModulo;
	}
	
}