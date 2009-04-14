package clinica.vo;

import java.io.Serializable;

public class CondicionCarenciaVO implements Serializable {

	private static final long serialVersionUID = -8139186500728987307L;
	
	private Integer mesesCarencia;

	public CondicionCarenciaVO(Integer mesesCarencia) {
		super();
		this.mesesCarencia = mesesCarencia;
	}

	public Integer getMesesCarencia() {
		return mesesCarencia;
	}

	public void setMesesCarencia(Integer mesesCarencia) {
		this.mesesCarencia = mesesCarencia;
	}

}
