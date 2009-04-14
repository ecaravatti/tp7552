package general.servicio;

import general.dao.DAO;
import general.dao.PracticaDAO;
import general.dominio.Practica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import laboratorio.dominio.excepciones.ExcepcionResultadoInvalido;
import laboratorio.vo.ItemResultadoVO;
import clinica.dominio.Prestacion;
import clinica.vo.PracticaVO;
import clinica.vo.PrestacionVO;

public class ServicioPracticaImpl implements ServicioPractica {

	private DAO<Practica> practicaDAO;
	private PracticaDAO practicaCustomDAO;

	@Transactional
	public List<ItemResultadoVO> obtenerResultadosPractica(Integer PracticaId) {
		Practica practica = practicaDAO.obtener(PracticaId);
		List<ItemResultadoVO> itemsResultado = new ArrayList<ItemResultadoVO>();
		
		for(String clave: practica.getPrestacion().getParametrosAEvaluar().keySet()) {
			ItemResultadoVO item = new ItemResultadoVO(clave);
			if (practica.getResultado() != null) {
				item.setValor(practica.getResultado().getResultados().get(clave));
			}
			itemsResultado.add(item);
		}
		
		return itemsResultado;
	}

	@Transactional
	public void modificarResultados(Integer codigoPractica, List<ItemResultadoVO> itemsResultados) throws ExcepcionResultadoInvalido {
		Practica practica = practicaDAO.obtener(codigoPractica);
		HashMap<String, String> resultados = new HashMap<String, String>();
		
		for (ItemResultadoVO item: itemsResultados) {
			if (item.getValor() != null) {
				resultados.put(item.getClave(), item.getValor());
			}
		}
		practica.modificarResultados(resultados);
	}	
	
	@Transactional
	public List<PracticaVO> obtenerTodas() {
		List<PracticaVO> practicas = new ArrayList<PracticaVO>();
		
		for (Practica practica : practicaDAO.obtenerTodos()) {
			practicas.add(new PracticaVO(practica.getId(), convertirEnPrestacionVO(practica.getPrestacion())));
		}
		return practicas;
	}

	@Transactional
	public List<PracticaVO> obtenerTodas(String ordenarPor, String orden, Integer desde, Integer cantidad) {
		return practicaCustomDAO.obtenerTodas(ordenarPor, orden, desde, cantidad);
	}
	
	@Transactional
	public Integer obtenerCantidad() {
		return practicaCustomDAO.obtenerCantidad();
	}
	
	@Transactional
	public void borrar(Integer id) {
		Practica practica = practicaDAO.obtener(id);
		practicaDAO.remover(practica);
	}
	
	@Transactional
	public void insertar(PracticaVO practicaVO) {
		Practica practica = new Practica();
		practica.setPrestacion(convertirEnPrestacion(practicaVO.getPrestacion()));
		practicaDAO.insertar(practica);
	}
	
	@Transactional
	public Double obtenerCostoOrdenMedica(Integer numeroOrdenMedica) {
		return practicaCustomDAO.obtenerCostoOrdenMedica(numeroOrdenMedica);
	}

	private PrestacionVO convertirEnPrestacionVO(Prestacion prestacion) {
		return new PrestacionVO(prestacion.getCodigo(), prestacion.getDescripcion());
	}

	private Prestacion convertirEnPrestacion (PrestacionVO prestacionVO) {
		Prestacion prestacion = new Prestacion();
		prestacion.setCodigo(prestacionVO.getCodigo());
		prestacion.setDescripcion(prestacionVO.getDescripcion());
		return prestacion;
	}

	public void setPracticaDAO(DAO<Practica> practicaDAO) {
		this.practicaDAO = practicaDAO;
	}

	public void setPracticaCustomDAO(PracticaDAO practicaCustomDAO) {
		this.practicaCustomDAO = practicaCustomDAO;
	}

}

