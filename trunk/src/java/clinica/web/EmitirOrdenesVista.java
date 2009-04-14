package clinica.web;
import java.util.List;

import clinica.vo.MedicoVO;
import clinica.vo.PacienteVO;
import clinica.vo.PracticaVO;
import clinica.vo.PrestacionVO;

public interface EmitirOrdenesVista {

	public void inicializar();

	public void setPacientes(List<PacienteVO> pacientes);
	
	public List<PacienteVO> getPacientes();
	
	public PacienteVO getPacienteSeleccionado();
			
	public void setMedicos(List<MedicoVO> medicos);
	
	public List<MedicoVO> getMedicos();

	public PracticaVO getPracticaSeleccionada();
	
	public List<PrestacionVO> getPrestaciones();
	
	public void setCantidadPracticas(Integer cantidadPracticas);
	
	public void setPrestaciones(List<PrestacionVO> prestaciones);
	
	public void setNuevasPracticas(List<PracticaVO> practicas);
	
	public Integer getNumeroOrdenMedica();
	
	public void setNumeroOrdenMedica(Integer numeroOrdenMedica);
		
	public MedicoVO getMedicoSeleccionado();
	
	public void setResultadoOrdenMedica(String resultadoOrdenMedica);
	
	public void setEstadoOrdenMedica(Boolean estadoOrdenMedica);
	
	public PrestacionVO getPrestacionSeleccionada();
	
	public void setCostoOrdenMedica(Double costoOrdenMedica);
}
