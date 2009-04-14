package clinica.dominio.reglas.condiciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import clinica.dominio.Prestacion;
import clinica.dominio.excepciones.ExcepcionEvaluacionCondicion;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Condicion;
import clinica.dominio.reglas.Frecuencia;

@Entity
@Table(name = "condicion_cantidad")
public class CondicionCantidad extends Condicion {

	@Column(name = "cantidad_pruebas")
	private Integer cantidadPruebas;
	
	@Column(name = "frecuencia")
	private Frecuencia frecuencia;
	
	protected CondicionCantidad() {
		super();
	}
	
	public CondicionCantidad(Integer cantidadPruebas, Frecuencia frecuencia) {
		this.cantidadPruebas = cantidadPruebas;
		this.frecuencia = frecuencia;
	}
	
	public void setCantidadPruebas(Integer cantidadPruebas) {
		this.cantidadPruebas = cantidadPruebas;
	}

	public Integer getCantidadPruebas() {
		return cantidadPruebas;
	}

	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	@Override
	public void evaluar(Paciente pPaciente, Prestacion pPrestacion) {
		if (pPaciente.getHistClinica().obtenerCantidadDePracticasPorPeriodo(pPrestacion, this.frecuencia) > this.cantidadPruebas) {
			ExcepcionEvaluacionCondicion ex = new ExcepcionEvaluacionCondicion();
			ex.setPrestacionEvaluada(pPrestacion.getDescripcion());
			ex.setFrecuencia(frecuencia.toString());
			throw ex;
		}
	}
	
	@Override
	public String toString() {
		return cantidadPruebas + " " + frecuencia.toString().toLowerCase();
	}

}
