package clinica.servicio;

import clinica.vo.CondicionCantidadVO;
import clinica.vo.CondicionCarenciaVO;
import clinica.vo.CondicionCombinacionVO;

public interface ServicioCobertura {
	
	public void aniadirCondicionCantidadARegla(Integer coberturaId,	CondicionCantidadVO condicionCantidadVO, String comportamientoKey);

	public void aniadirCondicionCarenciaARegla(Integer coberturaId,	CondicionCarenciaVO condicionCarenciaVO, String comportamientoKey);

	public void aniadirCondicionCombinacionARegla(Integer coberturaId, CondicionCombinacionVO condicionCombinacionVO, String comportamientoKey);
	
	public void eliminarRegla(Integer coberturaId);

}
