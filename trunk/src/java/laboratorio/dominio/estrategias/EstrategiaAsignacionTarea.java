package laboratorio.dominio.estrategias;

import java.util.List;

import laboratorio.dominio.Tecnico;
import laboratorio.dominio.tarea.Tarea;

public interface EstrategiaAsignacionTarea {

	public void asignarTareas(List<Tecnico> tecnicos, List<Tarea> tareas);
}
