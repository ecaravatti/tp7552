package clinica.dominio.reglas.condiciones;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import clinica.dominio.Prestacion;
import clinica.dominio.excepciones.ExcepcionEvaluacionCondicionCarencia;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Condicion;

@Entity
@Table(name = "condicion_carencia")
public class CondicionCarencia extends Condicion {

	@Column(name = "meses_carencia")
	private Integer mesesCarencia;

	protected CondicionCarencia() {
		super();
	}
	
	public CondicionCarencia(Integer mesesCarencia) {
		this.mesesCarencia = mesesCarencia;
	}
	
	@Override
	public void evaluar(Paciente paciente, Prestacion prestacion) {
		Calendar fechaActual = Calendar.getInstance();
		Calendar fechaIngreso = Calendar.getInstance();
		
		fechaIngreso.setTime(paciente.getFechaIngreso());
		fechaActual.add(Calendar.MONTH, this.mesesCarencia * -1);
		
		Date fechaIngresoPaciente = fechaIngreso.getTime();
		Date fechaIngresoSinCarencia = fechaActual.getTime();
				
		if (fechaIngresoPaciente.after(fechaIngresoSinCarencia))
		{
			ExcepcionEvaluacionCondicionCarencia ex = new ExcepcionEvaluacionCondicionCarencia();
			ex.setAntiguedadRequerida(mesesCarencia.toString());
			throw ex;
		}	
	}

	public Integer getMesesDeCarencia() {
		return this.mesesCarencia;
	}

	public void setMesesDeCarencia(Integer cantMesesCarencia) {
		this.mesesCarencia = cantMesesCarencia;
	}
	
	@Override
	public String toString() {
		return "paciente registrado desde hace " + mesesCarencia + (mesesCarencia == 1 ? " mes" : " meses");
	}

}
