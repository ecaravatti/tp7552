package clinica.dominio;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import laboratorio.dominio.practica.resultado.TipoResultado;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name = "prestacion")
public class Prestacion {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;	
	
	/** 
	 * Contiene un mapa con los puntos a evaluar por esta prestacion como 
	 * claves, y el tipo de resultados correspondientes a los mismos.
	 * Por ejemplo para la clave 'Globulos rojos' se espera un valor de
	 * tipo Entero. 
	 */
	@CollectionOfElements(targetElement = TipoResultado.class)
	private Map<String, TipoResultado> parametrosAEvaluar;

	@Column(name = "costo_adicional", nullable = true)
	private double costoAdicional;
	
	public Prestacion(){
	}
	
	public Prestacion(Integer codigo, String descripcion, Map<String, TipoResultado> parametrosAEvaluar) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.parametrosAEvaluar = parametrosAEvaluar;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public Map<String, TipoResultado> getParametrosAEvaluar() {
		return parametrosAEvaluar;
	}

	public double getCostoAdicional() {
		return costoAdicional;
	}

	public void setCostoAdicional(double costoAdicional) {
		this.costoAdicional = costoAdicional;
	}
	
}
