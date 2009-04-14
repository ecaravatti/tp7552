package clinica.servicio;

import general.dao.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import clinica.dominio.Medico;
import clinica.vo.MedicoVO;

public class ServicioMedicoImpl implements ServicioMedico {
	
	private DAO<Medico> medicoDAO;

	@Transactional
	public List<MedicoVO> obtenerTodos() {
		
		List<MedicoVO> medicos = new ArrayList<MedicoVO>();
		
		for (Medico medico : medicoDAO.obtenerTodos()) {
			medicos.add(new MedicoVO(medico.getId(), medico.getNombre(), medico.getEspecialidad()));
		}
		return medicos;
	}

	public void setMedicoDAO(DAO<Medico> medicoDAO) {
		this.medicoDAO = medicoDAO;
	}
}
