package mx.gob.tecdmx.seguridad.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_roles", schema = "public")
public class SegRoles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_id_rol", unique = true)
	int  id;
  
	@Column(name = "s_etiqueta_rol")
	String  etiquetaRol;
  
	@Column(name = "s_descripcion")
	String  descripcion;
  
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_rol_padre", referencedColumnName="n_id_rol")
	SegRoles  rolPadreId;
  
	@Column(name = "n_rec_activo")
	int  recActivo;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_session_id", referencedColumnName="n_session_id")
	SegLogSesion  sessionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEtiquetaRol() {
		return etiquetaRol;
	}

	public void setEtiquetaRol(String etiquetaRol) {
		this.etiquetaRol = etiquetaRol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public SegRoles getRolPadreId() {
		return rolPadreId;
	}

	public void setRolPadreId(SegRoles rolPadreId) {
		this.rolPadreId = rolPadreId;
	}

	public int getRecActivo() {
		return recActivo;
	}

	public void setRecActivo(int recActivo) {
		this.recActivo = recActivo;
	}

	public SegLogSesion getSessionId() {
		return sessionId;
	}

	public void setSessionId(SegLogSesion sessionId) {
		this.sessionId = sessionId;
	}

	
	
}