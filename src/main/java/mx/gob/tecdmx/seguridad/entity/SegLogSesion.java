package mx.gob.tecdmx.seguridad.entity;

import java.util.Date;

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
@Table(name = "seg_log_sesion", schema = "public")
public class SegLogSesion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_session_id", unique = true, nullable = false)
	int id;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_usuario", referencedColumnName="n_id_usuario")
	SegUsuarios idUsuario;
  
	@Column(name = "d_fecha_inicio")
	Date  fechaInicio;
  
	@Column(name = "d_fecha_fin")
	Date  fechaFin;
  
	@Column(name = "n_end_sesion")
	long  endSesion;
  
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="chain_n_session_id", referencedColumnName="n_session_id")
	SegLogSesion chainSessionId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SegUsuarios getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(SegUsuarios idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public long getEndSesion() {
		return endSesion;
	}

	public void setEndSesion(long endSesion) {
		this.endSesion = endSesion;
	}

	public SegLogSesion getChainSessionId() {
		return chainSessionId;
	}

	public void setChainSessionId(SegLogSesion chainSessionId) {
		this.chainSessionId = chainSessionId;
	}
	
	
}