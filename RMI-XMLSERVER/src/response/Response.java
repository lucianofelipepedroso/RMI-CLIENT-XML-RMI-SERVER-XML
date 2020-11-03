package response;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import common.CalcException;

public class Response {

	private double resultado;

	

	public Response(double resultado) {

		this.resultado = resultado;
	}

	public double getResultado() {
		return resultado;
	}

	public static Response fromData(double resultado) {
		return new Response(resultado);
	}

	public String toXML() throws CalcException {
		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element elementResponse = document.createElement("response");
			document.appendChild(elementResponse);

			Element elementResult = document.createElement("result");
			elementResult.setTextContent(String.valueOf(resultado));
			elementResponse.appendChild(elementResult);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			DOMSource source = new DOMSource(document);
			transformer.transform(source, streamResult);

			return stringWriter.toString();

		} catch (Exception e) {
			throw new CalcException(e);
		}

	}

}
