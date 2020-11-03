package request;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import common.CalcException;

public class Request {
	
	public enum Op {

		SUBTRAIR, SOMAR, MULTIPLICAR, DIVIDIR
	}

	private Op operacao;
	
	
	
	public Op getOperacao() {
		return operacao;
	}

	public double getN1() {
		return n1;
	}

	public double getN2() {
		return n2;
	}

	private double n1;
	
	private double n2;
	
	public static Request fromXML(String xmlRequest) throws CalcException {
		try {
			Request request = new Request();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(xmlRequest)));

			Element elementRoot = document.getDocumentElement();

			Element elementOp = (Element) elementRoot.getElementsByTagName("op").item(0);
			request.operacao = Op.valueOf(elementOp.getTextContent());

			Element elementN1 = (Element) elementRoot.getElementsByTagName("valor1").item(0);
			request.n1 = Double.parseDouble(elementN1.getTextContent());

			Element elementN2 = (Element) elementRoot.getElementsByTagName("valor2").item(0);
			request.n2 = Double.parseDouble(elementN2.getTextContent());

			return request;

		} catch (Exception e) {
			throw new CalcException(e);
		}

	}

}
