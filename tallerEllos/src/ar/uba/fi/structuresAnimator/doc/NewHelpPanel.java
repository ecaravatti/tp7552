package ar.uba.fi.structuresAnimator.doc;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * Panel que muestra la ayuda del sistema.
 * 
 * @author estefania
 */
public class NewHelpPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String HTML_RESOURCE = "/ar/uba/fi/structuresAnimator/doc/index.html";

	public NewHelpPanel() {

		final JEditorPane helpPane = new JEditorPane();
		helpPane.setEditable(false);
		helpPane.setMinimumSize(new Dimension(800, 600));
		JScrollPane scrollPane = new JScrollPane(helpPane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		// Setearle un kit de edición HTML al helpPane.
		HTMLEditorKit htmlKit = new HTMLEditorKit();
		StyleSheetUtils.addCSSRules(htmlKit.getStyleSheet());
		helpPane.setEditorKit(htmlKit);

		HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
		htmlDoc.setBase(getClass().getResource(HTML_RESOURCE));

		FileReader reader = null;
		// Crear parser para el archivo
		HTMLEditorKit.Parser parser = new ParserDelegator();
		HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);

		try {
			reader = new FileReader(getClass().getResource(HTML_RESOURCE)
					.getPath());

			// Parsear el archivo
			parser.parse(reader, callback, true);

			// Setear el HTMLDocument creado en el helpPane
			helpPane.setDocument(htmlDoc);

		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignoredException) {
				}
			}
		}
		helpPane.addHyperlinkListener(getHyperLinkListener());
	}

	/**
	 * @return Un listener para hyperlinks, que sirve para poder hacer click en
	 *         los links, e ir a los contenidos.
	 */
	private HyperlinkListener getHyperLinkListener() {
		return new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					JEditorPane pane = (JEditorPane) e.getSource();
					if (e instanceof HTMLFrameHyperlinkEvent) {
						HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
						HTMLDocument doc = (HTMLDocument) pane.getDocument();
						doc.processHTMLFrameHyperlinkEvent(evt);
					} else {
						try {
							String url = e.getURL().toString();

							if (url.startsWith("http"))
								// Para abrir links externos.
								Desktop.getDesktop().browse(URI.create(url));
							else
								// Para ir a links internos.
								pane.setPage(url);

						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				}
			}
		};
	}
}