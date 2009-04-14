package laboratorio.web;

import java.util.List;

import laboratorio.vo.TareaVO;
import laboratorio.vo.TecnicoVO;

public interface PanelAreaVista {

	public Integer getAreaId();
	public Integer getSupervisorId();
	public List<TecnicoVO> getTecnicos();
	public List<TareaVO> getTareasNoAsignadas();
	public Integer getTecnicoId();
	public Integer getTareaId();
	public String getEstrategiaSeleccionada();
	public List<String> getEstrategias();
}
