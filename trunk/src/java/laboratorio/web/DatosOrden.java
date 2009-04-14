package laboratorio.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laboratorio.dominio.excepciones.ExcepcionNumeroOrdenIncorrecto;
import laboratorio.presenter.VisualizarOrdenesPresenter;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import clinica.vo.MedicoVO;
import clinica.vo.PacienteVO;
import clinica.vo.PracticaVO;

public class DatosOrden extends Panel implements DatosOrdenVista {
	
	private static final long serialVersionUID = -7671885321287212387L;
	
	private PacienteVO pacienteMostrar;
	
	private MedicoVO medicoMostrar;
	
	private List<PracticaVO> practicas;
	
	private Integer cantidadPracticas;
	
	private Integer numeroOrdenMedica;
	
	private static final Integer TABLE_PAGINATION = 10;
	
	@SpringBean(name = "visualizarOrdenesPresenter")
	private VisualizarOrdenesPresenter presenter;
			
	
	public DatosOrden(String id, Integer nroOrden) {
		super(id);
		numeroOrdenMedica = nroOrden;
		presenter.setVistaOrden(this);
		WebMarkupContainer contenedor = new WebMarkupContainer("ordenMedicaDetalles");
		try {
			presenter.cargarDatos(); 
			Label pacienteNombreLabel = crearPacientoNombreLabel();
			Label pacienteApellidoLabel = crearPacienteApellidoLabel();
			Label medicoNombreLabel = crearMedicoNombreLabel();
			DefaultDataTable table = crearTablaPracticas();
			llenarContenedor(contenedor, pacienteNombreLabel, pacienteApellidoLabel, medicoNombreLabel, table);
		} catch (ExcepcionNumeroOrdenIncorrecto e) {			
		    contenedor.setVisible(false);
		    info(new StringResourceModel(e.getClave(), DatosOrden.this, null, new String[] {nroOrden.toString()}).getString());	    
		}
		add(contenedor);
		add(new FeedbackPanel("feedback"));
	}

	private void llenarContenedor(WebMarkupContainer contenedor, Label pacienteNombreLabel, Label pacienteApellidoLabel, Label medicoNombreLabel, DefaultDataTable table) {
		contenedor.add(pacienteNombreLabel);
		contenedor.add(pacienteApellidoLabel);
		contenedor.add(medicoNombreLabel);
		contenedor.add(table);
		contenedor.setVisible(true);
	}

	private Label crearMedicoNombreLabel() {
		Label medicoNombreLabel = new Label("medicoNombreMostrar", this.medicoMostrar.getNombre());
		return medicoNombreLabel;
	}

	private Label crearPacienteApellidoLabel() {
		Label pacienteApellidoLabel = new Label("pacienteApellidoMostrar", this.pacienteMostrar.getApellido());
		return pacienteApellidoLabel;
	}

	private Label crearPacientoNombreLabel() {
		Label pacienteNombreLabel = new Label("pacienteNombreMostrar", this.pacienteMostrar.getNombre());
		return pacienteNombreLabel;
	}

	private DefaultDataTable crearTablaPracticas() {
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new PropertyColumn(new ResourceModel("nombre"), "nombre", "nombre"));
		DefaultDataTable table = new DefaultDataTable("tablaPracticas", columns, new ProveedorDatosPractica(), TABLE_PAGINATION);
		table.setOutputMarkupId(true);
		return table;
	}
	
	class ProveedorDatosPractica extends SortableDataProvider {

		private static final long serialVersionUID = 718372052271831020L;

		public ProveedorDatosPractica() {
			super();
			setSort("nombre", true);
		}

		/**
		 * Devuelve un iterador a la lista de PracticaVOs
		 */
		public Iterator<PracticaVO> iterator(int from, int to) {
			// El presenter actualiza la lista de practicas.
			//presenter.buscar(from, to, getSort().isAscending());
			presenter.obtenerPracticas(numeroOrdenMedica);
			return practicas.iterator();
		}

		/**
		 * Devuelve el modelo de un PracticaVO
		 */
		public IModel model(Object o) {
			return new Model((Serializable) o);
		}

		/**
		 * Devuelve la cantidad de practicas.
		 */
		public int size() {
			// El presenter actualiza la cantidad de practicas.
			presenter.actualizarCantidad(numeroOrdenMedica);
			return cantidadPracticas;
		}
	}

	public void setCantidadPracticas(Integer cantidadPracticas) {		
		this.cantidadPracticas = cantidadPracticas;		
	}
	
	public void setMedico(MedicoVO medico) {		
		this.medicoMostrar=medico;		
	}
	
	public void setPaciente(PacienteVO paciente) {
		this.pacienteMostrar=paciente;		
	}
	
	public void setPracticas(List<PracticaVO> practicas) {
		this.practicas=practicas;		
	}

}
