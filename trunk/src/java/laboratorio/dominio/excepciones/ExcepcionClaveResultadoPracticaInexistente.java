package laboratorio.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

import java.util.List;

public class ExcepcionClaveResultadoPracticaInexistente extends ExcepcionNegocio {

	private static final long serialVersionUID = -1344958146079382988L;

	private List<String> resultadosInexistentes;
	
	public ExcepcionClaveResultadoPracticaInexistente(List<String> resultadosInexistente) {
		this.clave="ExcepcionClaveResultadoPracticaInexistente";
		this.resultadosInexistentes = resultadosInexistente;
	}

	public List<String> getResultadosInexistentes() {
		return resultadosInexistentes;
	}
}
