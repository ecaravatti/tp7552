package laboratorio.presenter;

import general.servicio.ServicioPractica;
import laboratorio.web.ResultadoPracticaVista;


public class ResultadoPracticaPresenterImpl implements ResultadoPracticaPresenter {

	private ResultadoPracticaVista vista;
	
	private ServicioPractica servicioPractica;
	
	public void inicializar() {
		vista.setResultados(servicioPractica.obtenerResultadosPractica(vista.getCodigoPractica()));
		vista.inicializar();
	}
	
	public void grabar() {
		servicioPractica.modificarResultados(vista.getCodigoPractica(), vista.getResultados());
	}
	
	public void setVista(ResultadoPracticaVista vista) {
		this.vista = vista;
	}

	public void setServicioPractica(ServicioPractica servicioPractica) {
		this.servicioPractica = servicioPractica;
	}
}
