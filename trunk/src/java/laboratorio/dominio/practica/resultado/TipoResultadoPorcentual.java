package laboratorio.dominio.practica.resultado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Porcentual")
public class TipoResultadoPorcentual extends TipoResultado {

	@Transient
	private String TIPO = "porcentual";
	
	@Override
	public Boolean validarTipoResultado(String valor) {
		// El separador decimal puede ser tanto '.' como ','
		valor = valor.replace(',', '.');
		
		try {
			Float porcentaje = new Float(valor);
			if (porcentaje >= 0 && porcentaje <=100) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	@Override
	public String getTipo() {
		return TIPO;
	}

}
