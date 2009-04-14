package clinica.dominio.reglas.condiciones;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

import clinica.dominio.Prestacion;
import clinica.dominio.excepciones.ExcepcionEvaluacionCondicion;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Condicion;
import clinica.dominio.reglas.Frecuencia;

@Entity
@Table(name = "condicion_combinacion")
public class CondicionCombinacion extends Condicion {

	@CollectionOfElements
	private List<Integer> lCodigoPractica;
	
	@Column(name = "cantidad_practicas")
	private Integer cantidadPracticas;

	protected CondicionCombinacion() {
		super();
		this.lCodigoPractica = new ArrayList<Integer>();
	}
	
	public void setCantidadPracticas(Integer cantidadPracticas) {
		this.cantidadPracticas = cantidadPracticas;
	}

	public Integer getCantidadPracticas() {
		return cantidadPracticas;
	}
	
	public CondicionCombinacion(Integer cantidadPracticas, Integer codigoPractica) {
		this.cantidadPracticas = cantidadPracticas;
		this.lCodigoPractica = new ArrayList<Integer>();
		this.lCodigoPractica.add(codigoPractica);
	}
	
	public CondicionCombinacion(Integer cantidadPracticas, List<Integer> lCodigoPractica) {
		this.cantidadPracticas = cantidadPracticas;
		this.lCodigoPractica = lCodigoPractica;
	}
	
	@Override
	public void evaluar(Paciente paciente, Prestacion prestacion) {
		int iCantidadPracticas = 0;
		for (Integer i : lCodigoPractica) {
			Prestacion pPrest = new Prestacion();
			pPrest.setCodigo(i);
			iCantidadPracticas += paciente.getHistClinica().obtenerCantidadDePracticasPorPeriodo(pPrest, Frecuencia.MENSUAL);			
		}
		if (iCantidadPracticas > this.cantidadPracticas) {
			ExcepcionEvaluacionCondicion ex = new ExcepcionEvaluacionCondicion();
			ex.setPrestacionEvaluada(prestacion.getDescripcion());
			ex.setFrecuencia("mensual");
			throw ex;
		}
	}

	public void agregarCodigoPrestacion(int iCodigoPrestacion) {
		this.lCodigoPractica.add(iCodigoPrestacion);		
	}

	public List<Integer> getLCodigoPractica() {
		return lCodigoPractica;
	}

	public void setLCodigoPractica(List<Integer> codigoPractica) {
		lCodigoPractica = codigoPractica;
	}
	
	@Override
	public String toString() {
		if (lCodigoPractica.size() == 1) {
			return cantidadPracticas + " por mes a menos que ya se haya realizado una prestacion " + lCodigoPractica.get(0); 
		} else {
			StringBuffer buffer = new StringBuffer(cantidadPracticas + " al mes entre prestaciones ");
			for(Iterator<Integer> it = lCodigoPractica.iterator(); it.hasNext(); ) {
				buffer.append(it.next());
				if (it.hasNext()) buffer.append(", ");
			}
			return buffer.toString();
		}
		
	}
	
}
