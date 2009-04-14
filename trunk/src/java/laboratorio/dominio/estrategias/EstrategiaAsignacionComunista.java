package laboratorio.dominio.estrategias;

import java.util.List;

import laboratorio.dominio.Tecnico;
import laboratorio.dominio.tarea.Tarea;

/*
 * Estrategia donde se agrega una tarea a cada tecnico de la lista
 */
public class EstrategiaAsignacionComunista implements EstrategiaAsignacionTarea {

	public void asignarTareas(List<Tecnico> tecnicos, List<Tarea> tareas) {
		int cantidadTecnicos=tecnicos.size();
		int i=0;
		Tecnico unTecnico;
		for (Tarea unaTarea : tareas) {
			unTecnico=tecnicos.get(i);
			unTecnico.asignarTarea(unaTarea);
			i++;
			if(i == cantidadTecnicos){
				i=0;
			}
		}
	}

}
