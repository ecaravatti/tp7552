package laboratorio.web;

import java.util.List;

import clinica.vo.MedicoVO;
import clinica.vo.PacienteVO;
import clinica.vo.PracticaVO;

public interface DatosOrdenVista {
		
	public void setMedico(MedicoVO medico);
	
	public void setPaciente(PacienteVO paciente);
	
	public void setCantidadPracticas(Integer cantidadPracticas);
	
	public void setPracticas(List<PracticaVO> practicas) ;

}
