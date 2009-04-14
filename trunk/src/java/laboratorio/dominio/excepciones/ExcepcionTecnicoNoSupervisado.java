package laboratorio.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

public class ExcepcionTecnicoNoSupervisado extends ExcepcionNegocio {

	private static final long serialVersionUID = 7588901718043828090L;
	
	public ExcepcionTecnicoNoSupervisado() {
		this.clave="ExcepcionTecnicoNoSupervisado";
	}
}
