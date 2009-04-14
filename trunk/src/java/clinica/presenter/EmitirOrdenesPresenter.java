package clinica.presenter;

import clinica.web.EmitirOrdenesVista;

public interface EmitirOrdenesPresenter {
	
	public void inicializar();

	public void setVista(EmitirOrdenesVista vista);

	public void borrar();
		
	public void actualizarCantidad();
	
	public void obtenerPracticas(Integer numeroOrdenMedica);
	
	public void agregarPractica();
	
	public void autorizarOrdenMedica();
	
	public void actualizarCostoAdicional();
}
