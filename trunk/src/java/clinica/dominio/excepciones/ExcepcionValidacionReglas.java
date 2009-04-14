package clinica.dominio.excepciones;

import general.dominio.excepciones.ExcepcionNegocio;

import java.util.ArrayList;
import java.util.List;

public class ExcepcionValidacionReglas extends ExcepcionNegocio {
	
	private static final long serialVersionUID = -1503045035981463966L;	
	private List<ExcepcionNegocio> excepciones; 
	
	public ExcepcionValidacionReglas() {
		excepciones = new ArrayList<ExcepcionNegocio>();
		this.clave="ExcepcionValidacionReglas";
	}	
	
	public void agregarExcepcion(ExcepcionNegocio ex){
		// Solo se agregan las excepciones distintas
		if (!buscarExcepcion(ex))
			excepciones.add(ex);
	}
	
	public Integer getCantidadExcepciones(){
		return excepciones.size();
	}

	public List<ExcepcionNegocio> getExcepciones() {
		return excepciones;
	}
	
	private boolean buscarExcepcion(ExcepcionNegocio ex) {
		boolean existe = false;
		for (ExcepcionNegocio excepcion : excepciones) {
			if (excepcion.equals(ex)) 								
				existe = true;
		}		
		return existe;
	}		
}
