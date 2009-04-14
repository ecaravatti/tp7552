package laboratorio.presenter;

import general.servicio.ServicioPractica;
import laboratorio.web.RegistrarResultadosVista;


public class RegistrarResultadosPresenterImpl implements RegistrarResultadosPresenter {

	private ServicioPractica servicioPractica;
	
	private RegistrarResultadosVista vista;
	
	public void borrar() {
		servicioPractica.borrar(vista.getPracticaSeleccionada());
	}

	public void inicializar() {
		this.vista.setPracticas(servicioPractica.obtenerTodas());
		this.vista.inicializar();
	}

	public void actualizarCantidad() {
		vista.setCantidadPracticas(servicioPractica.obtenerCantidad());
	}

	public void buscar(String ordenarPor, Integer desde, Integer cantidad, boolean ascending) {
		vista.setPracticas(servicioPractica.obtenerTodas(ordenarPor, ascending? "ASC":"DESC", desde, cantidad));
	}
	
	public void setVista(RegistrarResultadosVista vista) {
		this.vista = vista;
	}

	public void setServicioPractica(ServicioPractica servicioPractica) {
		this.servicioPractica = servicioPractica;
	}

}
