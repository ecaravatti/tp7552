package laboratorio.presenter;

import laboratorio.web.DatosOrden;
import laboratorio.web.VisualizarOrdenes;

public interface VisualizarOrdenesPresenter {
	
	public void inicializar();
	
	public void cargarDatos();

	public void setVista(VisualizarOrdenes vista);
	
	public void setVistaOrden(DatosOrden vistaOrden);
			
	public void buscar();

	public void actualizarCantidad(Integer numeroOrdenMedica);
	
	public void obtenerPracticas(Integer numeroOrdenMedica);

}
