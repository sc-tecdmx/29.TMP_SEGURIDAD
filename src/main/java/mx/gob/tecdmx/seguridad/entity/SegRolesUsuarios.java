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
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "seg_roles_usuarios", schema = "public")
public class SegRolesUsuarios {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_id_rol_usuario", unique = true, nullable = false)
	int  id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_rol", referencedColumnName="n_id_rol")
	SegRoles  idRol;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_usuario", referencedColumnName="n_id_usuario")
	SegUsuarios  idUsuario;

	@Column(name = "n_id_empleado_puesto_area")
	int idEmpleadoPuestoArea;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_session_id", referencedColumnName="n_session_id")
	SegLogSesion  nSessionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SegRoles getIdRol() {
		return idRol;
	}

	public void setIdRol(SegRoles idRol) {
		this.idRol = idRol;
	}

	public SegUsuarios getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(SegUsuarios idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdEmpleadoPuestoArea() {
		return idEmpleadoPuestoArea;
	}

	public void setIdEmpleadoPuestoArea(int idEmpleadoPuestoArea) {
		this.idEmpleadoPuestoArea = idEmpleadoPuestoArea;
	}

	public SegLogSesion getNSessionId() {
		return nSessionId;
	}

	public void setNSessionId(SegLogSesion nSessionId) {
		this.nSessionId = nSessionId;
	}

	
	
}