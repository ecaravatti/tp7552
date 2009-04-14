package clinica.vo;

import java.io.Serializable;
import java.util.List;

public class HistoriaClinicaVO implements Serializable {

	private static final long serialVersionUID = 8245913125320948205L;

	private Integer id;
	private List<PracticaVO> practicas;
		
	public List<PracticaVO> getPracticas() {
		return practicas;
	}

	public void setPracticas(List<PracticaVO> practicas) {
		this.practicas = practicas;
	}

	public HistoriaClinicaVO(Integer id, List<PracticaVO> practicasVO) {
		this.id=id;
		this.practicas = practicasVO;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
