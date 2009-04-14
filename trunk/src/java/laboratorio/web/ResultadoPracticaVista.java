package laboratorio.web;

import java.util.List;

import laboratorio.vo.ItemResultadoVO;

public interface ResultadoPracticaVista {

	public void setResultados(List<ItemResultadoVO> resultados);
	
	public List<ItemResultadoVO> getResultados();
	
	public Integer getCodigoPractica();
	
	public void inicializar();
	
}
