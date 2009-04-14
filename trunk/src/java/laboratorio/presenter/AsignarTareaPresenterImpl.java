package laboratorio.presenter;


import laboratorio.servicio.ServicioArea;
import laboratorio.servicio.ServicioSupervisor;
import laboratorio.web.AsignarTareaVista;

public class AsignarTareaPresenterImpl implements AsignarTareaPresenter {

	private AsignarTareaVista vista;
	private ServicioSupervisor servicioSupervisor;
	private ServicioArea servicioArea;
	
	public void inicializar() {
		
		this.vista.setSupervisores(servicioSupervisor.obtenerSupervisores());
		this.vista.inicializar();
	}

	public void actualizarAreas(){
		vista.setAreas(servicioArea.obtenerAreasPorSupervisor(vista.getSupervisorSeleccionado().getId()));
	}

	public void setVista(AsignarTareaVista vista) {
		this.vista = vista;
	}

	public void setServicioSupervisor(ServicioSupervisor servicioSupervisor) {
		this.servicioSupervisor = servicioSupervisor;
	}

	public void setServicioArea(ServicioArea servicio) {
		this.servicioArea = servicio;
	}
}
