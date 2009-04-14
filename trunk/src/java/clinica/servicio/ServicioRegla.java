package clinica.servicio;

import java.util.List;


public interface ServicioRegla {

	public List<String> getListadoDeComportamientos();

	public List<String> getListadoDeCondiciones();

	public List<String> getListadoDeFrecuencias();
	
	public boolean isCondicionCantidad(String condicion);
	
	public boolean isCondicionCombinacion(String condicion);
	
	public boolean isCondicionCarencia(String condicion);
	
}
