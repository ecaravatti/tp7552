package clinica.servicio;

import general.dominio.FactoryDeEstrategias;

import java.util.ArrayList;
import java.util.List;

import clinica.dominio.reglas.Frecuencia;

public class ServicioReglaImpl implements ServicioRegla {
	
	private static final String CONDICION_CANTIDAD = "Cantidad";
	private static final String CONDICION_COMBINACION = "Combinacion";
	private static final String CONDICION_CARENCIA = "Carencia";
	
	public List<String> getListadoDeComportamientos() {
		return FactoryDeEstrategias.getListadoComportamientos();
	}
	
	public List<String> getListadoDeCondiciones() {
		List<String> condiciones = new ArrayList<String>();
		condiciones.add(CONDICION_CANTIDAD);
		condiciones.add(CONDICION_CARENCIA);
		condiciones.add(CONDICION_COMBINACION);
		return condiciones;
	}

	public List<String> getListadoDeFrecuencias() {
		List<String> frecuencias = new ArrayList<String>();
		Frecuencia[] values = Frecuencia.values();
		for (int i = 0; i < values.length; i++) {
			frecuencias.add(values[i].name());
		}
		return frecuencias;
	}
	
	public boolean isCondicionCantidad(String condicion) {
		return CONDICION_CANTIDAD.equals(condicion);
	}
	
	public boolean isCondicionCombinacion(String condicion) {
		return CONDICION_COMBINACION.equals(condicion);
	}
	
	public boolean isCondicionCarencia(String condicion) {
		return CONDICION_CARENCIA.equals(condicion);
	}

}
