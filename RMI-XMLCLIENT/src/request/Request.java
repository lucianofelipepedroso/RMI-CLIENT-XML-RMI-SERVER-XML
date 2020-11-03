package request;

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

public class Request {

	public enum Op {

		SUBTRAIR, SOMAR, MULTIPLICAR, DIVIDIR
	}

	private Op operacao;

	private double n1;

	private double n2;

	private Request() {

	}

	private Request(Op operacao, double n1, double n2) {

		this.operacao = operacao;
		this.n1 = n1;
		this.n2 = n2;
	}

	public static Request fromData(Op op, double n1, double n2) {
		return new Request(op, n1, n2);

	}



	public String toXML() throws CalcException {

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element elementRoot = document.createElement("request");
			document.appendChild(elementRoot);

			Element elementOp = document.createElement("op");
			elementOp.setTextContent(operacao.toString());
			elementRoot.appendChild(elementOp);

			Element elementValor1 = document.createElement("valor1");
			elementValor1.setTextContent(String.valueOf(n1));
			elementRoot.appendChild(elementValor1);

			Element elementValor2 = document.createElement("valor2");
			elementValor2.setTextContent(String.valueOf(n2));
			elementRoot.appendChild(elementValor2);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);
			DOMSource domSource = new DOMSource(document);
			transformer.transform(domSource, streamResult);

			return stringWriter.toString();

		} catch (Exception e) {
			throw new CalcException(e);
		}

	}

	public Op getOperacao() {
		return operacao;
	}

	public double getN1() {
		return n1;
	}

	public double getN2() {
		return n2;
	}

}
