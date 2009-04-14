package clinica.vo;

import java.io.Serializable;
import java.util.List;

public class CondicionCombinacionVO implements Serializable {

	private static final long serialVersionUID = -3657528011093568365L;

	private List<Integer> lCodigoPractica;
	
	private Integer cantidadPracticas;

	public CondicionCombinacionVO(List<Integer> codigoPractica,	Integer cantidadPracticas) {
		super();
		lCodigoPractica = codigoPractica;
		this.cantidadPracticas = cantidadPracticas;
	}

	public List<Integer> getLCodigoPractica() {
		return lCodigoPractica;
	}

	public void setLCodigoPractica(List<Integer> codigoPractica) {
		lCodigoPractica = codigoPractica;
	}

	public Integer getCantidadPracticas() {
		return cantidadPracticas;
	}

	public void setCantidadPracticas(Integer cantidadPracticas) {
		this.cantidadPracticas = cantidadPracticas;
	}
}
