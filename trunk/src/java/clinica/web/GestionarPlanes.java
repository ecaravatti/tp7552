package clinica.web;

import general.web.PaginaBase;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import clinica.presenter.GestionarPlanesPresenter;
import clinica.vo.CoberturaVO;
import clinica.vo.PlanVO;
import clinica.vo.PrestacionVO;


public class GestionarPlanes extends PaginaBase implements GestionarPlanesVista {
	
	@SpringBean(name = "gestionarPlanesPresenter")
	private GestionarPlanesPresenter presenter;
	
	private List<PlanVO> planes;
	private PlanVO planSeleccionado;
	
	private List<CoberturaVO> coberturas = new ArrayList<CoberturaVO>();
	private CoberturaVO coberturaSeleccionada;
	
	@SuppressWarnings("unused")
	private String descripcionRegla;
	
	private List<String> comportamientos = new ArrayList<String>();
	private String comportamientoSeleccionado;
	
	private List<String> condiciones = new ArrayList<String>();
	private String condicionSeleccionada;
	
	private Integer cantidadCantidadIngresada;
	
	private List<String> frecuencias = new ArrayList<String>();
	private String cantidadFrecuenciaSeleccionada;
	
	private Integer carenciaMesesIngresado;

	private Integer combinacionCantidadIngresada;
	
	private List<PrestacionVO> prestaciones = new ArrayList<PrestacionVO>();
	private List<PrestacionVO> combinacionPrestacionesSeleccionadas;
	
	
	public GestionarPlanes() {
		presenter.setVista(this);
		presenter.inicializar();
	}

	public void inicializar() {
		Form form1 = new Form("form1");
		Form form2 = new Form("form2");

		DropDownChoice comportamientosDropDownChoice = crearComportamientosDropDownChoice();
		
		final ListMultipleChoice prestacionesMultipleChoice = crearPrestacionesMultipleChoice();
		WebMarkupContainer cantidadContainer = crearCondicionCantidadContainer();
		WebMarkupContainer carenciaContainer = crearCondicionCarenciaContainer();
		WebMarkupContainer combinacionContainer = crearCondicionCombinacionContainer(prestacionesMultipleChoice);
		
		final WebMarkupContainer condicionesContainer = crearCondicionesContainer(cantidadContainer, carenciaContainer, combinacionContainer);
		DropDownChoice condicionesDropDownChoice = crearCondicionesDropDownChoice(condicionesContainer);
		
		final Label reglaLabel = crearReglaLabel();
		final DropDownChoice prestacionesDropDownChoice = crearPrestacionesDropDownChoice(reglaLabel);
		DropDownChoice planesDropDownChoice = crearPlanesDropDownChoice(reglaLabel, prestacionesDropDownChoice,	prestacionesMultipleChoice);
		
		Button btnAniadirCondicion = crearBtnAniadirCondicion(prestacionesDropDownChoice);
		Button btnEliminarRegla = crearBtnEliminarRegla(prestacionesDropDownChoice);
		
		form1.add(planesDropDownChoice);
		form1.add(prestacionesDropDownChoice);
		form1.add(reglaLabel);
		form1.add(btnEliminarRegla);
		form2.add(form1);
		form2.add(comportamientosDropDownChoice);
		form2.add(condicionesDropDownChoice);
		form2.add(condicionesContainer);
		form2.add(btnAniadirCondicion);
		this.add(form2);
		this.add(new FeedbackPanel("feedback"));
	}
	
	private DropDownChoice crearComportamientosDropDownChoice() {
		DropDownChoice comportamientosDropDownChoice = new DropDownChoice("comportamientos", new PropertyModel(this, "comportamientoSeleccionado"), comportamientos);
		comportamientosDropDownChoice.setRequired(true);
		return comportamientosDropDownChoice;
	}
	
	private ListMultipleChoice crearPrestacionesMultipleChoice() {
		final ListMultipleChoice prestacionesMultipleChoice = new ListMultipleChoice("combinacionPrestaciones", new PropertyModel(this, "combinacionPrestacionesSeleccionadas"), prestaciones, new PrestacionesRenderer());
		prestacionesMultipleChoice.setRequired(true);
		prestacionesMultipleChoice.setOutputMarkupId(true);
		return prestacionesMultipleChoice;
	}
	
	private WebMarkupContainer crearCondicionCantidadContainer() {
		WebMarkupContainer condicionCantidadContainer = new WebMarkupContainer("cantidadContainer") {
			
			private static final long serialVersionUID = -6628758458560449787L;
			
			@Override
			public boolean isVisible() {
				return presenter.isCondicionCantidad(condicionSeleccionada);
			}
		};
		condicionCantidadContainer.add(new RequiredTextField("cantidadCantidad", new PropertyModel(this, "cantidadCantidadIngresada"), Integer.class));
		DropDownChoice frecuenciasDropDownChoice = new DropDownChoice("cantidadFrecuencia", new PropertyModel(this, "cantidadFrecuenciaSeleccionada"), frecuencias);
		frecuenciasDropDownChoice.setRequired(true);
		condicionCantidadContainer.add(frecuenciasDropDownChoice);
		return condicionCantidadContainer;
	}
	
	private WebMarkupContainer crearCondicionCarenciaContainer() {
		WebMarkupContainer condicionCarenciaContainer = new WebMarkupContainer("carenciaContainer") {
			
			private static final long serialVersionUID = -7486609735949897764L;
			
			@Override
			public boolean isVisible() {
				return presenter.isCondicionCarencia(condicionSeleccionada);
			}
		};
		condicionCarenciaContainer.add(new RequiredTextField("carenciaMeses", new PropertyModel(this, "carenciaMesesIngresado"), Integer.class));
		return condicionCarenciaContainer;
	}
	
	private WebMarkupContainer crearCondicionCombinacionContainer(final ListMultipleChoice prestacionesMultipleChoice) {
		WebMarkupContainer condicionCombinacionContainer = new WebMarkupContainer("combinacionContainer") {
			
			private static final long serialVersionUID = -9092052845101490463L;
			
			@Override
			public boolean isVisible() {
				return presenter.isCondicionCombinacion(condicionSeleccionada);
			}
		};
		condicionCombinacionContainer.add(new RequiredTextField("combinacionCantidad", new PropertyModel(this, "combinacionCantidadIngresada"), Integer.class));
		condicionCombinacionContainer.add(prestacionesMultipleChoice);
		return condicionCombinacionContainer;
	}
	
	private WebMarkupContainer crearCondicionesContainer(WebMarkupContainer cantidadContainer, WebMarkupContainer carenciaContainer, WebMarkupContainer combinacionContainer) {
		final WebMarkupContainer condicionesContainer = new WebMarkupContainer("condicionesContainer");
		condicionesContainer.add(cantidadContainer);
		condicionesContainer.add(carenciaContainer);
		condicionesContainer.add(combinacionContainer);
		condicionesContainer.setOutputMarkupId(true);
		return condicionesContainer;
	}
	
	private DropDownChoice crearCondicionesDropDownChoice(final WebMarkupContainer condicionesContainer) {
		DropDownChoice condicionesDropDownChoice = new DropDownChoice("condiciones", new PropertyModel(this, "condicionSeleccionada"), condiciones);
		condicionesDropDownChoice.setRequired(true);
		condicionesDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			
			private static final long serialVersionUID = 6659517164849196432L;
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.addComponent(condicionesContainer);
			}
		});
		return condicionesDropDownChoice;
	}
	
	private Label crearReglaLabel() {
		actualizarDescripcionRegla();
		final Label reglaLabel = new Label("regla", new PropertyModel(this, "descripcionRegla"));
		reglaLabel.setOutputMarkupId(true);
		return reglaLabel;
	}
	
	private DropDownChoice crearPrestacionesDropDownChoice(final Label reglaLabel) {
		final DropDownChoice prestacionesDropDownChoice = new DropDownChoice("prestaciones", new PropertyModel(this, "coberturaSeleccionada"), coberturas, new PrestacionesDeCoberturaRenderer());
		prestacionesDropDownChoice.setRequired(true);
		prestacionesDropDownChoice.setOutputMarkupId(true);
		prestacionesDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			
			private static final long serialVersionUID = 3595624836115689324L;
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				actualizarDescripcionRegla();
				target.addComponent(reglaLabel);
			}
		});
		return prestacionesDropDownChoice;
	}
	
	private DropDownChoice crearPlanesDropDownChoice(final Label reglaLabel, final DropDownChoice prestacionesDropDownChoice, final ListMultipleChoice prestacionesMultipleChoice) {
		DropDownChoice planesDropDownChoice = new DropDownChoice("planes", new PropertyModel(this, "planSeleccionado"), planes, new PlanesRenderer());
		planesDropDownChoice.setRequired(true);
		planesDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			
			private static final long serialVersionUID = -3843001650892256276L;
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				presenter.actualizarCoberturas();
				prestacionesDropDownChoice.setChoices(coberturas);
				prestacionesMultipleChoice.setChoices(prestaciones);
				target.addComponent(prestacionesDropDownChoice);
				target.addComponent(prestacionesMultipleChoice);
				descripcionRegla = "";
				target.addComponent(reglaLabel);
			}
		});
		return planesDropDownChoice;
	}
	
	private Button crearBtnAniadirCondicion(final DropDownChoice prestacionesDropDownChoice) {
		Button btnAniadirCondicion = new Button("btnAniadirCondicion") {
			
			private static final long serialVersionUID = 8678877716547146059L;
			
			@Override
			public void onSubmit() {
				super.onSubmit();
				presenter.aniadirCondicion();
				prestacionesDropDownChoice.setChoices(coberturas);
				actualizarDescripcionRegla();
			}
		};
		return btnAniadirCondicion;
	}

	private Button crearBtnEliminarRegla(final DropDownChoice prestacionesDropDownChoice) {
		Button btnEliminarRegla = new Button("btnEliminarRegla") {

			private static final long serialVersionUID = 5860838445025067204L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				presenter.eliminarRegla();
				prestacionesDropDownChoice.setChoices(coberturas);
				actualizarDescripcionRegla();
			}
		};
		return btnEliminarRegla;
	}

	
	private class PlanesRenderer extends ChoiceRenderer {

		private static final long serialVersionUID = -3681724275607589559L;

		@Override
		public Object getDisplayValue(Object object) {
			return ((PlanVO)object).getDescripcion();
		}
		
	}
	
	private class PrestacionesDeCoberturaRenderer extends ChoiceRenderer {

		private static final long serialVersionUID = -3477872476693765684L;

		@Override
		public Object getDisplayValue(Object object) {
			return ((CoberturaVO)object).getPrestacionVO().getDescripcion();
		}
		
	}
	
	private class PrestacionesRenderer extends ChoiceRenderer {

		private static final long serialVersionUID = 6555295182159792325L;

		@Override
		public Object getDisplayValue(Object object) {
			return ((PrestacionVO)object).getDescripcion();
		}
		
	}
	
	private void actualizarDescripcionRegla() {
		descripcionRegla = (coberturaSeleccionada == null || coberturaSeleccionada.getReglaVO() == null) ? "" : coberturaSeleccionada.getReglaVO().getDescripcion();
	}

	public void setPresenter(GestionarPlanesPresenter presenter) {
		this.presenter = presenter;
	}
	
	public List<PlanVO> getPlanes() {
		return planes;
	}
	
	public void setPlanes(List<PlanVO> planes) {
		this.planes = planes;
	}
	
	public PlanVO getPlanSeleccionado() {
		return planSeleccionado;
	}
	
	public void setCoberturas(List<CoberturaVO> coberturas) {
		this.coberturas = coberturas;
	}

	public void setCoberturaSeleccionada(CoberturaVO coberturaSeleccionada) {
		this.coberturaSeleccionada = coberturaSeleccionada;
	}

	public void setPlanSeleccionado(PlanVO planSeleccionado) {
		this.planSeleccionado = planSeleccionado;
	}

	public void setComportamientos(List<String> comportamientos) {
		this.comportamientos = comportamientos;
	}

	public void setComportamientoSeleccionado(String comportamientoSeleccionado) {
		this.comportamientoSeleccionado = comportamientoSeleccionado;
	}
	
	public void setCantidadCantidadIngresada(Integer cantidadCantidadIngresada) {
		this.cantidadCantidadIngresada = cantidadCantidadIngresada;
	}

	public void setCondiciones(List<String> condiciones) {
		this.condiciones = condiciones;
	}

	public String getCondicionSeleccionada() {
		return condicionSeleccionada;
	}

	public void setCondicionSeleccionada(String condicionSeleccionada) {
		this.condicionSeleccionada = condicionSeleccionada;
	}

	public void setFrecuencias(List<String> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public void setCantidadFrecuenciaSeleccionada(String cantidadFrecuenciaSeleccionada) {
		this.cantidadFrecuenciaSeleccionada = cantidadFrecuenciaSeleccionada;
	}

	public void setCarenciaMesesIngresado(Integer carenciaMesesIngresado) {
		this.carenciaMesesIngresado = carenciaMesesIngresado;
	}

	public void setCombinacionCantidadIngresada(Integer combinacionCantidadIngresada) {
		this.combinacionCantidadIngresada = combinacionCantidadIngresada;
	}

	public void setPrestaciones(List<PrestacionVO> prestaciones) {
		this.prestaciones = prestaciones;
	}

	public void setCombinacionPrestacionesSeleccionadas(List<PrestacionVO> combinacionPrestacionesSeleccionadas) {
		this.combinacionPrestacionesSeleccionadas = combinacionPrestacionesSeleccionadas;
	}

	public CoberturaVO getCoberturaSeleccionada() {
		return coberturaSeleccionada;
	}

	public Integer getCantidadCantidadIngresada() {
		return cantidadCantidadIngresada;
	}

	public String getCantidadFrecuenciaSeleccionada() {
		return cantidadFrecuenciaSeleccionada;
	}

	public String getComportamientoSeleccionado() {
		return comportamientoSeleccionado;
	}

	public void setDescripcionRegla(String descripcionRegla) {
		this.descripcionRegla = descripcionRegla;
	}

	public List<CoberturaVO> getCoberturas() {
		return coberturas;
	}

	public Integer getCarenciaMesesIngresado() {
		return carenciaMesesIngresado;
	}

	public List<PrestacionVO> getCombinacionPrestacionesSeleccionadas() {
		return combinacionPrestacionesSeleccionadas;
	}

	public Integer getCombinacionCantidadIngresada() {
		return combinacionCantidadIngresada;
	}
	
}
