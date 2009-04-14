package laboratorio.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

import java.util.Map;

import laboratorio.dominio.practica.resultado.TipoResultado;

public class ExcepcionResultadoInvalido extends ExcepcionNegocio {

	private static final long serialVersionUID = -7653262911844862062L;
	
	private Map<String, TipoResultado> resultadosInvalidos;

	
	public ExcepcionResultadoInvalido(Map<String, TipoResultado> resultadosInvalidos) {
		this.clave="ExcepcionResultadoInvalido";
		this.resultadosInvalidos = resultadosInvalidos;
	}
	
	public Map<String, TipoResultado> getResultadosInvalidos() {
		return resultadosInvalidos;
	}
}
