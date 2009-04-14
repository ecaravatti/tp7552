package laboratorio.presenter;

import laboratorio.servicio.ServicioArea;
import laboratorio.servicio.ServicioSupervisor;
import laboratorio.servicio.ServicioTecnico;
import laboratorio.web.PanelAreaVista;

public class PanelAreaPresenterImpl implements PanelAreaPresenter {

	ServicioArea servicioArea;
	ServicioTecnico servicioTecnico;
	ServicioSupervisor servicioSupervisor;
	
	public void cargarEstrategias(PanelAreaVista vista) {
		vista.getEstrategias().clear();
		vista.getEstrategias().addAll(servicioSupervisor.getListadoDeEstrategias());
	}
	
	public void cargarTareasNoAsignadas(PanelAreaVista vista) {
		vista.getTareasNoAsignadas().clear();
		vista.getTareasNoAsignadas().addAll(servicioArea.obtenerTareasNoAsignadas(vista.getAreaId()));
	}

	public void cargarTecnicosArea(PanelAreaVista vista) {
		vista.getTecnicos().clear();
		vista.getTecnicos().addAll(servicioTecnico.obtenerTecnicosPorAreaSupervisor(vista.getAreaId(), vista.getSupervisorId()));
	}

	public void agregarTareaNoAsignada(PanelAreaVista vista) {
		if (vista.getTecnicoId() != null)
			servicioSupervisor.desasignarTarea(vista.getSupervisorId(), vista.getTecnicoId(), vista.getTareaId());
	}
	
	public void asignarTareasAutomaticamente(PanelAreaVista vista) {
		servicioSupervisor.asignarTareasAutomaticamente(vista.getSupervisorId(), vista.getEstrategiaSeleccionada(), vista.getTecnicos(), vista.getTareasNoAsignadas());
	}
	
	public void setServicioArea(ServicioArea servicioArea) {
		this.servicioArea = servicioArea;
	}
	
	public void setServicioTecnico(ServicioTecnico servicioTecnico) {
		this.servicioTecnico = servicioTecnico;
	}
	
	public void setServicioSupervisor(ServicioSupervisor servicioSupervisor) {
		this.servicioSupervisor = servicioSupervisor;
	}

}
