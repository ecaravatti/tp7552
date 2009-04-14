package laboratorio.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

public class ExcepcionTareaADesasignarInconsistente extends ExcepcionNegocio {

	private static final long serialVersionUID = 8731040841427190881L; 
	
	public ExcepcionTareaADesasignarInconsistente() {
		this.clave="ExcepcionTareaADesasignarInconsistente";
	}	
}
