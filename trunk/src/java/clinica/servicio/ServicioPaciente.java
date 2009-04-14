package clinica.servicio;

import java.util.List;

import clinica.dominio.paciente.Paciente;
import clinica.vo.PacienteVO;

public interface ServicioPaciente {

	public List<PacienteVO> obtenerTodos();
		
	public Paciente obtener(Integer numeroPaciente);
	
}
