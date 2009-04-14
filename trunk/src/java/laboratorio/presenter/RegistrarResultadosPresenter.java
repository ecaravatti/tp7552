package laboratorio.presenter;

import laboratorio.web.RegistrarResultadosVista;

public interface RegistrarResultadosPresenter {

	public void borrar();
	
	public void inicializar();
	
	public void buscar(String ordenarPor, Integer from, Integer count, boolean ascending);
	
	public void actualizarCantidad();
	
	public void setVista(RegistrarResultadosVista vista);
}
