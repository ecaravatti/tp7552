package clinica.presenter;

import clinica.web.GestionarPlanesVista;

public interface GestionarPlanesPresenter {

	public void inicializar();

	public void setVista(GestionarPlanesVista vista);
	
	public void actualizarCoberturas();
	
	public void aniadirCondicion();
	
	public void eliminarRegla();
	
	public boolean isCondicionCantidad(String condicionSeleccionada);

	public boolean isCondicionCombinacion(String condicionSeleccionada);
	
	public boolean isCondicionCarencia(String condicionSeleccionada);
}
