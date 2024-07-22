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
	
	@Column(name = "codigo")
	String  codigo;
  
	@Column(name = "menu_desc")
	String  menuDesc;
  
	@Column(name = "menu_url")
	String  menuUrl;
  
	@Column(name = "menu_pos")
	int  menuPos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SegCatNivelModulo getNIdNivel() {
		return nIdNivel;
	}

	public void setNIdNivel(SegCatNivelModulo nIdNivel) {
		this.nIdNivel = nIdNivel;
	}

	public String getDescModulo() {
		return descModulo;
	}

	public void setDescModulo(String descModulo) {
		this.descModulo = descModulo;
	}

	public SegModulos getNIdModuloPadre() {
		return nIdModuloPadre;
	}

	public void setNIdModuloPadre(SegModulos nIdModuloPadre) {
		this.nIdModuloPadre = nIdModuloPadre;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public int getMenuPos() {
		return menuPos;
	}

	public void setMenuPos(int menuPos) {
		this.menuPos = menuPos;
	}

	public SegCatNivelModulo getnIdNivel() {
		return nIdNivel;
	}

	public void setnIdNivel(SegCatNivelModulo nIdNivel) {
		this.nIdNivel = nIdNivel;
	}

	public SegModulos getnIdModuloPadre() {
		return nIdModuloPadre;
	}

	public void setnIdModuloPadre(SegModulos nIdModuloPadre) {
		this.nIdModuloPadre = nIdModuloPadre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}