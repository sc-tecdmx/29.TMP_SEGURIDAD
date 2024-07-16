package mx.gob.tecdmx.seguridad.entity;

import java.util.Date;

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
@Table(name = "seg_usuarios_modulos", schema = "public")
@IdClass(IDUsuariosModulos.class)
public class SegUsuariosModulos {
	@Id
	@Column(name="n_id_usuario")
	Integer  nIdUsuario;
	
	@Id
	@Column(name="n_id_modulo")  
	Integer  nIdModulo;
	
	@Column(name = "d_fecha_alta")
	Date  fechaAlta;
	
	@Column(name = "d_fecha_baja")
	Date  fechaBaja;
	
	@Column(name = "status")
	boolean  status;
	
	@Column(name = "n_session_id")
	Integer  sessionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_usuario", referencedColumnName="n_id_usuario", insertable = false, updatable = false)
    private SegUsuarios segUsuarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="n_id_modulo", referencedColumnName="n_id_modulo", insertable = false, updatable = false)
    private SegModulos segModulos;

	public Integer getNIdUsuario() {
		return nIdUsuario;
	}

	public void setNIdUsuario(Integer nIdUsuario) {
		this.nIdUsuario = nIdUsuario;
	}

	public Integer getNIdModulo() {
		return nIdModulo;
	}

	public void setNIdModulo(Integer nIdModulo) {
		this.nIdModulo = nIdModulo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public SegUsuarios getSegUsuarios() {
		return segUsuarios;
	}

	public void setSegUsuarios(SegUsuarios segUsuarios) {
		this.segUsuarios = segUsuarios;
	}

	public SegModulos getSegModulos() {
		return segModulos;
	}

	public void setSegModulos(SegModulos segModulos) {
		this.segModulos = segModulos;
	}

    
	
	
}