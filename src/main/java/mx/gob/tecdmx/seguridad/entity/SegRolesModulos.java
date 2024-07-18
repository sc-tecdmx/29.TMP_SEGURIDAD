package mx.gob.tecdmx.seguridad.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_roles_modulos", schema = "public")
@IdClass(IDRolesModulos.class)
public class SegRolesModulos {
	@Id
	@Column(name="n_id_rol")
	Integer  nIdRol;
	
	@Id
	@Column(name="n_id_modulo")  
	Integer  nIdModulo;

	@Column(name = "crear")
	String  crear;
  
	@Column(name = "leer")
	String  leer;
  
	@Column(name = "editar")
	String  editar;
  
	@Column(name = "eliminar")
	String  eliminar;
  
	@Column(name = "publico")
	String  publico;
  
	@Column(name = "n_session_id")
	Integer  sessionId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_rol", referencedColumnName="n_id_rol", insertable = false, updatable = false)
    private SegRoles segRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_modulo", referencedColumnName="n_id_modulo", insertable = false, updatable = false)
    private SegModulos segModulos;


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

	public String getCrear() {
		return crear;
	}

	public void setCrear(String crear) {
		this.crear = crear;
	}

	public String getLeer() {
		return leer;
	}

	public void setLeer(String leer) {
		this.leer = leer;
	}

	public String getEditar() {
		return editar;
	}

	public void setEditar(String editar) {
		this.editar = editar;
	}

	public String getEliminar() {
		return eliminar;
	}

	public void setEliminar(String eliminar) {
		this.eliminar = eliminar;
	}

	public String getPublico() {
		return publico;
	}

	public void setPublico(String publico) {
		this.publico = publico;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public SegRoles getSegRoles() {
		return segRoles;
	}

	public void setSegRoles(SegRoles segRoles) {
		this.segRoles = segRoles;
	}

	public SegModulos getSegModulos() {
		return segModulos;
	}

	public void setSegModulos(SegModulos segModulos) {
		this.segModulos = segModulos;
	}
}