package general.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import laboratorio.dominio.excepciones.ExcepcionClaveResultadoPracticaInexistente;
import laboratorio.dominio.excepciones.ExcepcionResultadoInvalido;
import laboratorio.dominio.practica.resultado.Resultado;
import laboratorio.dominio.practica.resultado.TipoResultado;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

import clinica.dominio.Prestacion;

@Entity
@Table(name = "practica")
public class Practica {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	@OneToOne
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "prestacion")
	@ForeignKey(name = "practica_prestacion_fk")
	private Prestacion prestacion;
	
	@Column(name = "fecha_realizacion")
	private Date fechaRealizacion;
	
	@Column(name = "fecha_resultado")
	private Date fechaResultado;
	
	@OneToOne(optional= true)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "practica_resultado")
	@ForeignKey(name = "resultado_fk")
	private Resultado resultado = new Resultado();
	
	@Column(name = "aprobada")
	private Boolean aprobada;
	
	public Practica() {
		this.resultado = new Resultado();
		this.aprobada=false;
	}
	
	public Practica(Prestacion prestacion) {
		this();
		this.prestacion = prestacion;
	}
	
	public Boolean estaAprobada(){
		return this.aprobada;
	}
	
	public Boolean setAprobada(Boolean estado){
		return this.aprobada=estado;
	}
	
	public void setPrestacion(Prestacion prestacion) {
		this.prestacion = prestacion;
	}
	
	public Prestacion getPrestacion() {
		return prestacion;
	}
	
	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}
	
	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}
	
	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}
	
	public Date getFechaResultado() {
		return fechaResultado;
	}

	public Integer getId() {
		return id;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public Map<String, TipoResultado> getParametrosResultado() {
		return prestacion.getParametrosAEvaluar();
	}
	
	/**
	 * Este método modifica los resultados de la practica. 
	 * @param resultados contiene el nombre y valor de los distintos items de resultados.
	 * @throws ExcepcionClaveResultadoPracticaInexistente
	 * @throws ExcepcionResultadoInvalido
	 */
	public void modificarResultados(Map<String, String> resultados) 
		throws ExcepcionClaveResultadoPracticaInexistente, ExcepcionResultadoInvalido {
		
		List<String> resultadosInexistentes = new ArrayList<String>();
		Map<String, TipoResultado> resultadosInvalidos = new HashMap<String, TipoResultado>();
		
		for (String parametro: resultados.keySet()) {
			if (!this.existeParametroAEvaluar(parametro)) {
				resultadosInexistentes.add(parametro);
			} else {
				// Si el nombre de la clave es existente, valido que su valor asociado sea del tipo correcto.
				String valorParametro = resultados.get(parametro);
				if (!validarTipoResultado(parametro, valorParametro)) {
					resultadosInvalidos.put(parametro, getParametrosResultado().get(parametro));
				}
			}
		}
		
		if (resultadosInexistentes.size() > 0) {
			throw new ExcepcionClaveResultadoPracticaInexistente(resultadosInexistentes);
		}
		
		if (resultadosInvalidos.size() > 0) {
			throw new ExcepcionResultadoInvalido(resultadosInvalidos);
		}
		
		// Si la clave y el valor son correctos modifico los resultados.
		if (this.resultado == null) {
			this.resultado = new Resultado();
		}
		this.resultado.modificar(resultados);
		this.fechaResultado = new Date();
	}
	
	private Boolean existeParametroAEvaluar(String parametro) {
		return this.getParametrosResultado().containsKey(parametro);
	}
	
	private Boolean validarTipoResultado(String parametro, String valor) {
		return this.getParametrosResultado().get(parametro).validarTipoResultado(valor);
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
