package laboratorio.web;

import java.util.List;

import clinica.vo.PracticaVO;

public interface RegistrarResultadosVista {

	public void inicializar();
	
	public Integer getPracticaSeleccionada();

	public List<PracticaVO> getPracticas();

	public void setPracticas(List<PracticaVO> practicas);
	
	public Integer getCantidadPracticas();

	public void setCantidadPracticas(Integer cantidadPracticas);
	
}
