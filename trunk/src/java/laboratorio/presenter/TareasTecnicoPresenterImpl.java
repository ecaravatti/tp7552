package laboratorio.presenter;

import java.util.List;

import laboratorio.dominio.tarea.EstadoTarea;
import laboratorio.servicio.ServicioSupervisor;
import laboratorio.servicio.ServicioTecnico;
import laboratorio.vo.TareaVO;
import laboratorio.web.TareasTecnicoVista;

public class TareasTecnicoPresenterImpl implements TareasTecnicoPresenter {

	ServicioSupervisor servicioSupervisor;
	ServicioTecnico servicioTecnico;
	
	public void inicializar(TareasTecnicoVista vista) {
		cargarTareasTecnico(vista);
	}
	
	public void asignarTarea(TareasTecnicoVista vista) {
		
		if (vista.getTecnicoOrigenId() == null)
			servicioSupervisor.asignarTarea(vista.getSupervisorId(), vista.getTecnicoId(), vista.getTareaId());
		else if (!vista.getTecnicoId().equals(vista.getTecnicoOrigenId()))
			servicioSupervisor.reasignarTarea(vista.getSupervisorId(), vista.getTecnicoOrigenId(), vista.getTecnicoId(), vista.getTareaId());
	}
	
	private void cargarTareasTecnico(TareasTecnicoVista vista) {
		List<TareaVO> tareas = servicioTecnico.obtenerTareasPorTecnico(vista.getTecnicoId());
		vista.getTareasEnProgreso().clear();
		vista.getTareasPendientes().clear();
		vista.getTareasOtrasAreas().clear();
		
		for (TareaVO t : tareas) {
			if (!t.getAreaId().equals(vista.getAreaId()))
				vista.getTareasOtrasAreas().add(t);
			else if (t.getEstado().equals(EstadoTarea.EnProgreso))
				vista.getTareasEnProgreso().add(t);
			else if (t.getEstado().equals(EstadoTarea.Asignada))
				vista.getTareasPendientes().add(t);
		}
	}

	public void setServicioSupervisor(ServicioSupervisor servicioSupervisor) {
		this.servicioSupervisor = servicioSupervisor;
	}
	
	public void setServicioTecnico(ServicioTecnico servicioTecnico) {
		this.servicioTecnico = servicioTecnico;
	}

}
