package clinica.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

public class ExcepcionEvaluacionCondicionCarencia extends ExcepcionNegocio {

	private static final long serialVersionUID = -6570491856920787234L;
	private String antiguedadRequerida;
	
	public ExcepcionEvaluacionCondicionCarencia() {
		this.clave="ExcepcionEvaluacionCondicionCarencia";
	}

	public String getAntiguedadRequerida() {
		return antiguedadRequerida;
	}

	public void setAntiguedadRequerida(String antiguedadRequerida) {
		this.antiguedadRequerida = antiguedadRequerida;
	}

	public boolean equals (ExcepcionNegocio e){
		boolean result = false;
		if (super.equals(e))
			result = (this.getAntiguedadRequerida().equals(((ExcepcionEvaluacionCondicionCarencia)e).getAntiguedadRequerida()));			
		return result;
	}
}
