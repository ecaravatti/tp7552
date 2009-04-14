package clinica.web;

import general.dominio.excepciones.ExcepcionNegocio;
import general.web.PaginaBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import clinica.dominio.excepciones.ExcepcionEvaluacionCondicion;
import clinica.dominio.excepciones.ExcepcionEvaluacionCondicionCarencia;
import clinica.dominio.excepciones.ExcepcionPrestacionNoEstaEnPlan;
import clinica.dominio.excepciones.ExcepcionValidacionReglas;
import clinica.presenter.EmitirOrdenesPresenter;
import clinica.vo.MedicoVO;
import clinica.vo.PacienteVO;
import clinica.vo.PracticaVO;
import clinica.vo.PrestacionVO;


public class EmitirOrdenes  extends PaginaBase implements EmitirOrdenesVista {

	private static final Integer TABLE_PAGINATION = 10;
	
	@SpringBean(name = "emitirOrdenesPresenter")
	private EmitirOrdenesPresenter presenter;
	
	private Integer numeroOrdenMedica;
	private Boolean estadoOrdenMedica;
	private List<PacienteVO> pacientes;
	private PacienteVO pacienteSeleccionado;
	
	private List<MedicoVO> medicos;
	private MedicoVO medicoSeleccionado;

	private List<PrestacionVO> prestaciones;
	private PrestacionVO prestacionSeleccionada;

	private Integer cantidadPracticas;

	private PracticaVO practicaSeleccionada;
	private List<PracticaVO> nuevasPracticas;
	
	private String resultadoOrdenMedica;
	private Double costoOrdenMedica = 0D;
	
	public EmitirOrdenes() {
		presenter.setVista(this);
		presenter.inicializar();
	}
	
	public void inicializar() {
		Form form = new Form("form");		
		
		DropDownChoice pacientesDropDownChoice = crearPacientesDropDownChoice();
		DropDownChoice medicosDropDownChoice = crearMedicosDropDownChoice();
		
		Label especialidadMedico = crearEspecialidadMedicoLabel();
		Label numeroOrdenMedicaLabel = crearNumeroOrdenMedicaLabel();
		Label estadoOrdenMedicaLabel = crearEstadoOrdenMedicaLabel();
		Label resultadoOrdenMedicaLabel = crearResultadoOrdenMedicaLabel();
		Label costoLabel = crearCostoLabel();
		Label costoOrdenMedicaLabel = crearCostoOrdenMedicaLabel();
				
		DropDownChoice prestacionesDropDownChoice = crearPrestacionesDropDownChoice();
		
		Button btnAgregarPractica = crearBtnAgregarPractica();
		Button btnAutorizar = crearBtnAutorizar();

		DefaultDataTable table = crearTablaPracticas();
		
		form.add(pacientesDropDownChoice);
		form.add(medicosDropDownChoice);
		form.add(especialidadMedico);
		form.add(btnAgregarPractica);
		form.add(prestacionesDropDownChoice);		
		form.add(numeroOrdenMedicaLabel);
		form.add(estadoOrdenMedicaLabel);
		form.add(btnAutorizar);
		form.add(costoLabel);
		form.add(costoOrdenMedicaLabel);
		
		this.add(form);
		this.add(table);				
		this.add(resultadoOrdenMedicaLabel);
		this.add(new FeedbackPanel("feedback"));
	}

	private DefaultDataTable crearTablaPracticas() {
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new PropertyColumn(new ResourceModel("nombre"), "nombre", "nombre"));
		columns.add(new AbstractColumn(new ResourceModel("acciones")) {

			private static final long serialVersionUID = -4157554353874304732L;

			public void populateItem(Item item, String id, IModel model) {
				item.add(new ActionPanel(id, model));
			}
		});

		DefaultDataTable table = new DefaultDataTable("tablaPracticas", columns, new ProveedorDatosPracticas(), TABLE_PAGINATION);
		table.setOutputMarkupId(true);
		return table;
	}

	private Button crearBtnAutorizar() {
		Button btnAutorizar = new Button("btnAutorizar") {
			
			private static final long serialVersionUID = 5736158426032420878L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				try {
					estadoOrdenMedica = true;
					presenter.autorizarOrdenMedica();										
				}
				catch(ExcepcionValidacionReglas ex) {
					escribirExcepciones (ex);
					estadoOrdenMedica = false;	
				}
				presenter.actualizarCostoAdicional();
			}
		};
		return btnAutorizar;
	}

	private Button crearBtnAgregarPractica() {
		Button btnAgregarPractica = new Button("btnAgregarPractica") {
			
			private static final long serialVersionUID = 5736158426032420878L;

			@Override
			public void onSubmit() {				
				if (prestacionSeleccionada == null)
					info(new StringResourceModel("prestaciones.Required", EmitirOrdenes.this, null, "").getString());
				else {
					super.onSubmit();
					presenter.agregarPractica();
					prestaciones.remove(prestacionSeleccionada);
				}
			}
		};
		return btnAgregarPractica;
	}

	private DropDownChoice crearPrestacionesDropDownChoice() {
		DropDownChoice prestacionesDropDownChoice = new DropDownChoice("prestaciones", new PropertyModel(this, "prestacionSeleccionada"), prestaciones, new PrestacionesRenderer());		
		prestacionesDropDownChoice.setOutputMarkupId(true);
		return prestacionesDropDownChoice;
	}

	private Label crearCostoOrdenMedicaLabel() {
		Label costoOrdenMedicaLabel = new Label("costoOrdenMedica", new PropertyModel(this, "costoOrdenMedica")) {

			private static final long serialVersionUID = 6805373544037383248L;

			public boolean isVisible() {
				return costoOrdenMedica > 0;
			}	
		};
		return costoOrdenMedicaLabel;
	}

	private Label crearCostoLabel() {
		Label costoLabel = new Label("labelCosto", "Costo"){

			private static final long serialVersionUID = -420204543908213468L;

			public boolean isVisible() {
				return costoOrdenMedica > 0;
			}
		};
		return costoLabel;
	}

	private Label crearResultadoOrdenMedicaLabel() {
		Label resultadoOrdenMedicaLabel = new Label("resultadoOrdenMedica", new PropertyModel(this, "resultadoOrdenMedica"));
		return resultadoOrdenMedicaLabel;
	}

	private Label crearEstadoOrdenMedicaLabel() {
		Label estadoOrdenMedicaLabel = new Label("estadoOrdenMedica", new PropertyModel(this, "descripcionEstadoOrdenMedica"));
		return estadoOrdenMedicaLabel;
	}

	private Label crearNumeroOrdenMedicaLabel() {
		Label numeroOrdenMedicaLabel = new Label("numeroOrdenMedica", new PropertyModel(this, "numeroOrdenMedica"));
		return numeroOrdenMedicaLabel;
	}

	private Label crearEspecialidadMedicoLabel() {
		Label especialidadMedico = new Label("especialidadMedico", new PropertyModel(this, "medicoSeleccionado.especialidad"));
		especialidadMedico.setOutputMarkupId(true);
		return especialidadMedico;
	}

	private DropDownChoice crearMedicosDropDownChoice() {
		DropDownChoice medicosDropDownChoice = new DropDownChoice("medicos", new PropertyModel(this, "medicoSeleccionado"), medicos, new MedicosRenderer());
		medicosDropDownChoice.setRequired(true);
		medicosDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = -372810229492592981L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.addComponent(EmitirOrdenes.this.get("form:especialidadMedico"));
			}
			
		});
		return medicosDropDownChoice;
	}

	private DropDownChoice crearPacientesDropDownChoice() {
		DropDownChoice pacientesDropDownChoice = new DropDownChoice("pacientes", new PropertyModel(this, "pacienteSeleccionado"), pacientes, new PacientesRenderer());
		pacientesDropDownChoice.setRequired(true);
		return pacientesDropDownChoice;
	}

	class ActionPanel extends Panel {

		private static final long serialVersionUID = -7431034266631994294L;

		public ActionPanel(String id, IModel model) {
			super(id, model);
			add(new AjaxLink("borrar", model) {
				private static final long serialVersionUID = 1L;

				public void onClick(AjaxRequestTarget target) {
					target.addComponent(EmitirOrdenes.this.get("tablaPracticas"));
					target.addComponent(EmitirOrdenes.this.get("form:prestaciones"));
					PracticaVO practica = (PracticaVO) ActionPanel.this.getModelObject();
					practicaSeleccionada = practica;
					prestaciones.add(practica.getPrestacion());
					presenter.borrar();
				}
			});
		}
	}
	
	class ProveedorDatosPracticas extends SortableDataProvider {

		private static final long serialVersionUID = 2276818838721105509L;

		public ProveedorDatosPracticas() {
			super();
			setSort("nombreArea", true);
		}

		/**
		 * Devuelve un iterador a la lista de AreaVOs
		 */
		public Iterator<PracticaVO> iterator(int from, int to) {
			// El presenter actualiza la lista de areas.
			//presenter.buscar(from, to, getSort().isAscending());
			presenter.obtenerPracticas(numeroOrdenMedica);
			return nuevasPracticas.iterator();
		}

		/**
		 * Devuelve el modelo de un AreaVO
		 */
		public IModel model(Object o) {
			return new Model((Serializable) o);
		}

		/**
		 * Devuelve la cantidad de areas.
		 */
		public int size() {
			// El presenter actualiza la cantidad de areas.
			presenter.actualizarCantidad();
			return cantidadPracticas;
		}
	}
	
	private class PacientesRenderer extends ChoiceRenderer {
		
		private static final long serialVersionUID = -8827174322387230106L;
		
		@Override
		public Object getDisplayValue(Object object) {
			return ((PacienteVO)object).getApellido()+" "+((PacienteVO)object).getNombre();
		}						
	}

	private class PrestacionesRenderer extends ChoiceRenderer {
		
		private static final long serialVersionUID = -8827174322387230106L;
		
		@Override
		public Object getDisplayValue(Object object) {
			return ((PrestacionVO)object).getDescripcion();
		}						
	}

	private class MedicosRenderer extends ChoiceRenderer {		
		
		private static final long serialVersionUID = -8969188611658300421L;		

		@Override
		public Object getDisplayValue(Object object) {
			return ((MedicoVO)object).getNombre();
		}						
	}
	
	private void escribirExcepciones (ExcepcionNegocio ex) {
		for (ExcepcionNegocio exNeg : ((ExcepcionValidacionReglas)ex).getExcepciones()){
			if ("ExcepcionPrestacionNoEstaEnPlan".equals(exNeg.getClave()))
				info(new StringResourceModel(exNeg.getClave(), EmitirOrdenes.this, null, new String[] {((ExcepcionPrestacionNoEstaEnPlan)exNeg).getPrestacionNoCubierta(), pacienteSeleccionado.getPlanMedico().getDescripcion()}).getString());							
			if ("ExcepcionEvaluacionCondicion".equals(exNeg.getClave()))
				info(new StringResourceModel(exNeg.getClave(), EmitirOrdenes.this, null, new String[] {((ExcepcionEvaluacionCondicion)exNeg).getPrestacionEvaluada(), ((ExcepcionEvaluacionCondicion)exNeg).getFrecuencia()}).getString());
			if ("ExcepcionEvaluacionCondicionCarencia".equals(exNeg.getClave()))
				info(new StringResourceModel(exNeg.getClave(), EmitirOrdenes.this, null, new String[] {((ExcepcionEvaluacionCondicionCarencia)exNeg).getAntiguedadRequerida()}).getString());						
			if ("ExcepcionValidacionReglas".equals(exNeg.getClave()))
				escribirExcepciones(exNeg);
		}		
	}
	
	public String getDescripcionEstadoOrdenMedica() {
		String estado;
		if (this.estadoOrdenMedica)
			estado = getLocalizer().getString("Aprobada", this);
		else
			estado = getLocalizer().getString("DesAprobada", this);
		return estado;
	}
	
	public PrestacionVO getPrestacionSeleccionada() {
		return prestacionSeleccionada;
	}

	public void setPrestacionSeleccionada(PrestacionVO prestacionSeleccionada) {
		this.prestacionSeleccionada = prestacionSeleccionada;
	}

	public PracticaVO getPracticaSeleccionada() {
		return practicaSeleccionada;
	}

	public void setPracticaSeleccionada(PracticaVO nuevapracticaSeleccionada) {
		this.practicaSeleccionada = nuevapracticaSeleccionada;
	}

	public Integer getCantidadPracticas() {
		return cantidadPracticas;
	}

	public void setCantidadPracticas(Integer cantidadPracticas) {
		this.cantidadPracticas = cantidadPracticas;
	}

	public List<PrestacionVO> getPrestaciones() {
		return prestaciones;
	}

	public void setPrestaciones(List<PrestacionVO> prestaciones) {
		this.prestaciones = prestaciones;
	}

	public List<MedicoVO> getMedicos() {
		return medicos;
	}

	public PacienteVO getPacienteSeleccionado() {
		return pacienteSeleccionado;
	}

	public List<PacienteVO> getPacientes() {
		return pacientes;
	}

	public List<PracticaVO> getNuevasPracticas() {
		return nuevasPracticas;
	}
	
	public void setNuevasPracticas(List<PracticaVO> nuevasPracticas) {
		this.nuevasPracticas = nuevasPracticas;
	}
	
	public Integer getNumeroOrdenMedica() {
		return numeroOrdenMedica;
	}
	
	public void setNumeroOrdenMedica(Integer numeroOrdenMedica) {
		this.numeroOrdenMedica = numeroOrdenMedica;
	}
	
	public void setMedicos(List<MedicoVO> medicos) {
		this.medicos=medicos;		
	}

	public void setPacientes(List<PacienteVO> pacientes) {
		this.pacientes=pacientes;		
	}
	
	public MedicoVO getMedicoSeleccionado() {
		return medicoSeleccionado;
	}

	public void setMedicoSeleccionado(MedicoVO medicoSeleccionado) {
		this.medicoSeleccionado = medicoSeleccionado;
	}

	public void setPacienteSeleccionado(PacienteVO pacienteSeleccionado) {
		this.pacienteSeleccionado = pacienteSeleccionado;
	}

	public PracticaVO getNuevaPracticaSeleccionada() {
		return practicaSeleccionada;
	}

	public void setNuevaPracticaSeleccionada(PracticaVO nuevaPracticaSeleccionada) {
		this.practicaSeleccionada = nuevaPracticaSeleccionada;
	}

	public String getResultadoOrdenMedica() {
		return resultadoOrdenMedica;
	}

	public void setResultadoOrdenMedica(String resultadoOrdenMedica) {
		this.resultadoOrdenMedica = resultadoOrdenMedica;
	}

	public Boolean getEstadoOrdenMedica() {
		return estadoOrdenMedica;
	}

	public void setEstadoOrdenMedica(Boolean estadoOrdenMedica) {
		this.estadoOrdenMedica = estadoOrdenMedica;
	}

	public Double getCostoOrdenMedica() {
		return costoOrdenMedica;
	}

	public void setCostoOrdenMedica(Double costoOrdenMedica) {
		this.costoOrdenMedica = costoOrdenMedica;
	}	

}