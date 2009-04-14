package clinica.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

public class ExcepcionEvaluacionCondicion extends ExcepcionNegocio {

	private static final long serialVersionUID = -6570491856920787234L;
	private String prestacionEvaluada;
	private String frecuencia;
	
	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getPrestacionEvaluada() {
		return prestacionEvaluada;
	}

	public void setPrestacionEvaluada(String prestacionEvaluada) {
		this.prestacionEvaluada = prestacionEvaluada;
	}

	public ExcepcionEvaluacionCondicion() {
		this.clave="ExcepcionEvaluacionCondicion";
	}
	
	public boolean equals (ExcepcionNegocio e){
		boolean result = false;
		if (super.equals(e))
			result = (this.getFrecuencia().equals(((ExcepcionEvaluacionCondicion)e).getFrecuencia()) && this.getPrestacionEvaluada().equals(((ExcepcionEvaluacionCondicion)e).getPrestacionEvaluada()));			
		return result;
	}
}
