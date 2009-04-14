package laboratorio.dominio.practica.resultado;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

/**
 * Esta clase maneja los resultados de distintas 
 * practicas médicas de forma genérica.
 */
@Entity
@Table(name = "resultado")
public class Resultado {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** 
	 * Este mapa representa los resultados de una practica medica.
	 * 		clave: el nombre del resultado. 
	 * 		valor: el valor de dicho resultado.
	 */
	@CollectionOfElements
	private Map<String, String> resultados;
	
	
	public Resultado() {
		this.resultados = new HashMap<String, String>();
	}
	
	/**
	 * Cambia los resultados actuales por los nuevos.
	 * @param resultados
	 */	
	public void modificar(Map<String, String> nuevosResultados) {
		this.resultados = nuevosResultados;
	}
	
	public void setResultados(Map<String, String> resultados) {
		this.resultados = resultados;
	}
	
	public Map<String, String> getResultados() {
		return resultados;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
