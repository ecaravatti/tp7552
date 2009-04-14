package laboratorio.dominio.estrategias;

import java.util.List;

import laboratorio.dominio.Tecnico;
import laboratorio.dominio.tarea.Tarea;

/*
 * Estrategia donde se agrega una tarea al tecnico que tiene menor cantidad de tareas
 * en la lista de tecnicos.
 */
public class EstrategiaAsignacionEquitativa implements EstrategiaAsignacionTarea {

	public void asignarTareas(List<Tecnico> tecnicos, List<Tarea> tareas) {
		Tecnico unTecnico;
		for (Tarea unaTarea : tareas) {
			unTecnico=this.obtenerTecnicoMenorTareas(tecnicos);
			unTecnico.asignarTarea(unaTarea);
		}
	}
	
	/*
	 * Obtiene el tecnico de la lista de Tecnicos que tiene la menor
	 * cantidad de tareas Asigandas
	 */
	private Tecnico obtenerTecnicoMenorTareas(List<Tecnico> tecnicos) {
		Tecnico unTecnico=tecnicos.get(0);
		for (Tecnico otroTecnico : tecnicos) {
			if(otroTecnico.obtenerCantidadTareas()<unTecnico.obtenerCantidadTareas())
				unTecnico=otroTecnico;
		}
		return unTecnico;		
	}

}
