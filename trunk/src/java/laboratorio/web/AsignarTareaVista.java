package laboratorio.web;

import java.util.List;

import laboratorio.vo.AreaVO;
import laboratorio.vo.SupervisorVO;

public interface AsignarTareaVista {

	public void inicializar();
	
	public void setSupervisores(List<SupervisorVO> supervisores);
	
	public void setAreas(List<AreaVO> areas);
	
	public SupervisorVO getSupervisorSeleccionado();

	public AreaVO getAreaSeleccionada();
	
}
