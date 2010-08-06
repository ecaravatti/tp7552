package view.common;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.exception.common.CannotUndoException;
import controller.InteractiveController;
import dataStructure.DataStructureApplet;

/**
 * Contiene todos los elementos que permiten que las operaciones se realicen en
 * forma interactiva.
 * 
 * 
 */
public class InteractivePanel extends javax.swing.JPanel {
	private static final long serialVersionUID = 1791141348829770224L;

	private InteractiveController controller;

	private static final int MIN = 0;
	private static final int MAX = 200;

	/** Creates new form InteractivePanel */
	public InteractivePanel() {
		initComponents();
		this.controller = null;
		this.setEnabledButtons(false);
		this.slider.setMinimum(MIN);
		this.slider.setMaximum(MAX);
		this.slider.addChangeListener(new SliderListener());
	}

	/**
	 * Agrega un controlador
	 * 
	 * @param controller
	 *            controlador a agregar
	 */
	public void addInteractiveController(InteractiveController controller) {
		this.controller = controller;
	}

	/**
	 * Cambia el controlador interactivo
	 * 
	 * @param controller
	 *            nuevo controlador
	 */
	/*
	 * public void setInteractiveController(InteractiveController controller) {
	 * this.controller = controller; }
	 */

	/**
	 * Habilita (o desactiva) los botones del panel
	 * 
	 * @param b
	 *            true para habilitar, false para desactivar.
	 */
	public void setEnabledButtons(boolean b) {
		this.nextButton.setEnabled(b);
		this.undoStepButton.setEnabled(b);
	}

	/**
	 * Habilita (o no) el boton siguiente
	 * 
	 * @param b
	 *            true para habilitar, false en caso contrario
	 */
	public void setEnabledNextButton(boolean b) {
		this.nextButton.setEnabled(b);
	}

	/**
	 * Habilita (o no) el boton deshacer
	 * 
	 * @param b
	 *            true para habilitar, false en caso contrario
	 */
	public void setEnabledUndoButton(boolean b) {
		this.undoStepButton.setEnabled(b);
	}

	/**
	 * Ajusta el valor
	 * 
	 * @param value
	 *            nuevo valor.
	 */
	public void setValueSlider(int value) {
		this.slider.setValue(value);
	}

	public void setMaximumSlider(int max) {
		this.slider.setMaximum(max);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setHgap(5);
		flowLayout.setVgap(5);
		buttonPanel = new javax.swing.JPanel();
		panel = new javax.swing.JPanel();

		sliderPanel = new javax.swing.JPanel();
		slider = new javax.swing.JSlider();

		setLayout(new java.awt.GridBagLayout());

		// buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
		buttonPanel
				.setBorder(BorderFactory.createTitledBorder("Paso por paso"));
		buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel,
				javax.swing.BoxLayout.Y_AXIS));

		buttonPanel.add(panel, panel.getName());
		panel.setLayout(flowLayout);
		// checkBoxInteractive.setBackground(new java.awt.Color(255, 255, 255));

		Icon playIcon = new ImageIcon(DataStructureApplet.BUTTON_PLAY_IMAGE);
		Icon pauseIcon = new ImageIcon(DataStructureApplet.BUTTON_PAUSE_IMAGE);
		checkBoxInteractive = new JCheckBox("Interactivo", pauseIcon, false);
		checkBoxInteractive.setSelectedIcon(playIcon);
		checkBoxInteractive.setToolTipText("Modo interactivo");
		checkBoxInteractive.setAlignmentX(1.0f);
		checkBoxInteractive
				.putClientProperty("JComponent.sizeVariant", "large");
		checkBoxInteractive.setMnemonic(KeyEvent.VK_N);
		checkBoxInteractive
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						checkBoxInteractiveActionPerformed(evt);
					}
				});

		Icon nextIcon = new ImageIcon(
				DataStructureApplet.BUTTON_FAST_FORWARD_IMAGE);
		nextButton = new JButton("Siguiente", nextIcon);
		nextButton.setToolTipText("Siguiente paso");
		nextButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
		nextButton.putClientProperty("JComponent.sizeVariant", "small");
		nextButton.setMnemonic(KeyEvent.VK_S);
		panel.add(checkBoxInteractive, checkBoxInteractive.getName());
		nextButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextButtonActionPerformed(evt);
			}
		});
		panel.add(nextButton);

		Icon undoIcon = new ImageIcon(DataStructureApplet.BUTTON_REWIND_IMAGE);
		undoStepButton = new javax.swing.JButton("Deshacer", undoIcon);
		undoStepButton.setToolTipText("Paso anterior");
		undoStepButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
		undoStepButton.putClientProperty("JComponent.sizeVariant", "small");
		undoStepButton.setMnemonic(KeyEvent.VK_H);
		undoStepButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				undoStepButtonActionPerformed(evt);
			}
		});
		panel.add(undoStepButton);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		add(buttonPanel, gridBagConstraints);

		// sliderPanel.setBackground(new java.awt.Color(255, 255, 255));
		sliderPanel.setBorder(BorderFactory
				.createTitledBorder("Velocidad de animación"));
		sliderPanel.setLayout(new javax.swing.BoxLayout(sliderPanel,
				javax.swing.BoxLayout.PAGE_AXIS));

		// slider.setBackground(new java.awt.Color(255, 255, 255));
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.putClientProperty("JComponent.sizeVariant", "large");
		JLabel labelLento = new JLabel("+ lento", SwingConstants.LEFT);
		labelLento.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
		JLabel labelRapido = new JLabel("+ rápido", SwingConstants.RIGHT);
		labelRapido.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
		JPanel p = new JPanel(new GridLayout());
		p.add(labelLento);
		p.add(labelRapido);
		sliderPanel.add(slider);
		sliderPanel.add(p);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.weighty = 1.0;
		this.add(sliderPanel, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * Invocado cuando se hace click sobre el check bock
	 * 
	 * @param evt
	 */
	private void checkBoxInteractiveActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_checkBoxInteractiveActionPerformed
		setEnabledButtons(false);
		checkBoxInteractive
				.setFont(checkBoxInteractive.isSelected() ? getFont()
						.deriveFont(Font.ITALIC, 14) : getFont().deriveFont(
						Font.PLAIN, 14));
		controller.setInteractive(this.checkBoxInteractive.isSelected());
	}// GEN-LAST:event_checkBoxInteractiveActionPerformed

	/**
	 * Invodo cuando se presiona el boton "Siguiente"
	 * 
	 * @param evt
	 *            evento
	 */
	private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextButtonActionPerformed
		setEnabledButtons(false);
		controller.nextStep();
	}// GEN-LAST:event_nextButtonActionPerformed

	/**
	 * Invocado cuando se presiona el boton "deshacer"
	 * 
	 * @param evt
	 *            evento
	 */
	private void undoStepButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_undoStepButtonActionPerformed
		try {
			controller.undoStep();
			setEnabledButtons(false);
		} catch (CannotUndoException ex) {
			// TODO
		}
	}// GEN-LAST:event_undoStepButtonActionPerformed

	private class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent event) {
			JSlider source = (JSlider) event.getSource();
			if (!source.getValueIsAdjusting()) {
				controller.speedChanged(source.getValue());
			}
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel buttonPanel;
	private javax.swing.JCheckBox checkBoxInteractive;
	private javax.swing.JButton nextButton;
	private javax.swing.JPanel panel;
	private javax.swing.JSlider slider;
	private javax.swing.JPanel sliderPanel;
	private javax.swing.JButton undoStepButton;
	// End of variables declaration//GEN-END:variables

}
