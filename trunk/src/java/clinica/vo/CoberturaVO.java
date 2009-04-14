package clinica.vo;

import java.io.Serializable;

public class CoberturaVO implements Serializable {

	private static final long serialVersionUID = -3194509795637826346L;

	private Integer id;
	
	private PrestacionVO prestacionVO;
	
	private ReglaVO reglaVO;
	
	public CoberturaVO(Integer id, PrestacionVO prestacionVO, ReglaVO reglaVO) {
		this.id = id;
		this.prestacionVO = prestacionVO;
		this.reglaVO = reglaVO;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PrestacionVO getPrestacionVO() {
		return prestacionVO;
	}

	public void setPrestacionVO(PrestacionVO prestacionVO) {
		this.prestacionVO = prestacionVO;
	}

	public ReglaVO getReglaVO() {
		return reglaVO;
	}

	public void setReglaVO(ReglaVO reglaVO) {
		this.reglaVO = reglaVO;
	}

}