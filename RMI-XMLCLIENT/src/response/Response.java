package response;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import common.CalcException;

public class Response {

	private double resultado;

	private Response() {

	}

	public Response(double resultado) {

		this.resultado = resultado;
	}

	public double getResultado() {
		return resultado;
	}

	public static Response fromData(double resultado) {
		return new Response(resultado);
	}

	public static Response fromXML(String xmlResponse) throws CalcException {
		try {
			Response response = new Response();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(xmlResponse)));

			Element elementRoot = document.getDocumentElement();

			Element elementResult = (Element) elementRoot.getElementsByTagName("result").item(0);
			response.resultado = Double.parseDouble(elementResult.getTextContent());

			return response;

		} catch (Exception e) {
			throw new CalcException(e);
		}

	}

}
