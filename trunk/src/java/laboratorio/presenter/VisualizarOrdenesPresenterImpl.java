package laboratorio.presenter;

import general.servicio.ServicioOrdenMedica;
import laboratorio.web.DatosOrden;
import laboratorio.web.VisualizarOrdenes;
import clinica.vo.OrdenMedicaVO;

public class VisualizarOrdenesPresenterImpl implements VisualizarOrdenesPresenter {
	
	private VisualizarOrdenes vista;
	private DatosOrden vistaOrden;
	
	public DatosOrden getVistaOrden() {
		return vistaOrden;
	}

	public void setVistaOrden(DatosOrden vistaOrden) {
		this.vistaOrden = vistaOrden;
	}

	private ServicioOrdenMedica servicioOrdenMedica;
	
	public ServicioOrdenMedica getServicioOrdenMedica() {
		return servicioOrdenMedica;
	}

	public void setServicioOrdenMedica(ServicioOrdenMedica servicioOrdenMedica) {
		this.servicioOrdenMedica = servicioOrdenMedica;
	}

	public VisualizarOrdenes getVista() {
		return vista;
	}
	
	public void actualizarCantidad(Integer numeroOrdenMedica) {
		vistaOrden.setCantidadPracticas(servicioOrdenMedica.obtenerPracticasDeOrden(numeroOrdenMedica).size());		
	}

	public void buscar(){
		vistaOrden.setPracticas(servicioOrdenMedica.obtenerPracticasDeOrden(vista.getNumeroOrden()));
	}
	
	public void inicializar() {		
		vista.inicializar();
	}

	public void setVista(VisualizarOrdenes vista) {
		this.vista = vista;		
	}

	public void obtenerPracticas(Integer numeroOrdenMedica) {
		vistaOrden.setPracticas(servicioOrdenMedica.obtenerPracticasDeOrden(numeroOrdenMedica));
		
	}

	public void cargarDatos() {
		Integer numero=vista.getNumeroOrden();		
		OrdenMedicaVO ordenVo=servicioOrdenMedica.obtener(numero);
		vistaOrden.setMedico(ordenVo.getMedico());
		vistaOrden.setPaciente(ordenVo.getPaciente());					
	}

}
