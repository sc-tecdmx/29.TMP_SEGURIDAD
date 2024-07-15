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
@Table(name = "seg_modulos", schema = "public")
public class SegModulos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "n_id_modulo", unique = true)
	Integer  id;
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_nivel", referencedColumnName="n_id_nivel")
	SegCatNivelModulo  nIdNivel;
  
	@Column(name = "desc_modulo")
	String  descModulo;
  
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="n_id_modulo_padre", referencedColumnName="n_id_modulo")
	SegModulos  nIdModuloPadre;
  
	@Column(name = "menu")
	String  menu;
  
	@Column(name = "menu_desc")
	String  menuDesc;
  
	@Column(name = "menu_url")
	String  menuUrl;
  
	@Column(name = "menu_pos")
	int  menuPos;

	
	
}