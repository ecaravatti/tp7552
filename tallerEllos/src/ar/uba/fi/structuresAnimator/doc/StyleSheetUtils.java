package ar.uba.fi.structuresAnimator.doc;

import javax.swing.text.html.StyleSheet;

/**
 * Clase utilitaria para agregar estilos CSS.
 *
 * @author estefania
 */
public class StyleSheetUtils {

	public static void addCSSRules(StyleSheet styleSheet) {
		styleSheet
				.addRule(
						"body { font-size: 20pt; font-family: \"Lucida Grande\", \"Lucida Sans Unicode\", verdana, geneva, sans-serif; color:#555555; background: #ffffff; }");
		styleSheet
				.addRule(
						"a { color:#007898; border-bottom:1px dotted #cccccc; text-decoration:none; }");
		styleSheet.addRule(
				"a:hover { border-bottom:1px solid #cccccc; color: #808080; }");
		styleSheet.addRule("a:focus { outline: 0; }");
		styleSheet.addRule(
				"ul { margin:10px 10px 10px 0; padding:0 0 0 15px; }");
		styleSheet.addRule(
				"li { margin:0 0 0 10px; padding:3px; }");
		styleSheet.addRule(
				"img { border: 0; text-decoration: none; }");
		styleSheet
				.addRule(
						".wrap { padding:0 10px 10px 10px; width:860px; margin:0 auto; }");
		styleSheet
				.addRule(
						"#logo {margin: 26px 20px 14px 5px; color:#768998; }");/*float:left; */
		styleSheet
				.addRule(
						"h1 { margin: 0 0 10px; letter-spacing: 1px; font-size: 2em; }");
		styleSheet.addRule("h1 a { color: #768998; }");
		styleSheet.addRule("h1 a:hover { color: #FFFCB3; }");
		styleSheet
				.addRule(
						"#nav { border:none; padding:0; margin: 47px 10px 60px 0; }"); /*  float:right; */
		styleSheet
				.addRule(
						"#nav li { list-style:none; margin:0 4px 0 0; padding:0; font-size: 1.1em; }"); /* float:left;  */
		styleSheet
				.addRule(
						"#nav li a { display:block; padding: 10px 12px 15px; color:#768998; text-decoration:none; border: 0; margin:0 1px 0 0; }");
		styleSheet
				.addRule(
						"#nav li a:hover, #nav li a.current { background: url(../images/barbg.gif) no-repeat bottom center; color:#879aa9; }");
		styleSheet.addRule(
				".akey { border-bottom: 1px dotted #4B5761; }");
		styleSheet.addRule(
				".left { width: 570px; margin: 0 0 20px 0; }"); /*float: left; */
		styleSheet
				.addRule(
						".left h2 { float: left; font: 2.1em \"Trebuchet MS\", Arial; background: url(../images/h2bg.gif) repeat-x bottom; color:#121212; letter-spacing:-1px; margin: 0px 0 20px 0; clear:left; }");
		styleSheet
				.addRule(
						".left h2 a { color:#121212; text-decoration:none; border: 0; }");
		styleSheet
				.addRule(
						".left p { clear: both; margin: 5px 0 20px 0; font-size:110%; line-height:21px; }");
		styleSheet.addRule(
				"#right {	width:230px; float:right; margin: 0 0 20px 0; }");
		styleSheet
				.addRule(
						"#right h2 { color:#ffffff; margin: 0 0 5px 0; font: bold 1.2em \"Trebuchet MS\", Arial; background: #ffffff url(../images/sideh2bg.gif) repeat-x; padding: 4px 10px; border-bottom: 2px solid #000; }");
		styleSheet
				.addRule(
						"#right ul { list-style:none; 	border-top:1px solid #eeeeee; border:none; padding:0; margin:0 0 15px 0; }");
		styleSheet
				.addRule(
						"#right ul li { border-bottom:1px solid #eeeeee; padding:5px; margin:0; }");
		styleSheet.addRule("#right ul li a { border: 0; }");
		styleSheet.addRule("#tagcloud { margin: 0 0 20px; }");
		styleSheet.addRule(
				"#tagcloud a { margin: 0 5px 0 0; color: #25771F; }");
		styleSheet.addRule(
				"#tagcloud a:hover { background: #E7FEE5; color: #007898; }");
		styleSheet
				.addRule(
						"#info { border: 1px solid #ccc; padding: 3px 5px; margin: 0 0 10px; }");
		styleSheet
				.addRule(
						"#footer { clear: both; border-top: 1px solid #cccccc; padding: 20px 0; color:#808080; }");
		styleSheet.addRule("#footer p { line-height: 2.5em; } ");
	}
}
