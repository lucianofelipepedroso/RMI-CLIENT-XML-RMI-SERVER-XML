package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.Calc;
import common.CalcException;
import common.CalcImpl;
import request.Request;
import response.Response;

public class Server {

	private Calc calc;

	public static void main(String[] args) throws Exception {

		new Server().start();

	}

	private void start() throws Exception {

		try (ServerSocket serverSocket = new ServerSocket(3434)) {
			calc = new CalcImpl();

			while (true) {
				System.out.println("Server aguardando...");
				Socket socket = serverSocket.accept();

				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

				String xmlRequest = dis.readUTF();

				Request req = Request.fromXML(xmlRequest);

				Response resp = invoke(req);

				String xmlResponse = resp.toXML();

				dos.writeUTF(xmlResponse);
				dos.flush();
			}
		}
	}

	private Response invoke(Request req) throws CalcException {
		double resultado;

		// Com base na operação solicitada, chama o método correspondente de CalcImpl
		switch (req.getOperacao()) {
		case SOMAR:
			resultado = calc.somar(req.getN1(), req.getN2());
			break;
		case SUBTRAIR:
			resultado = calc.subtrair(req.getN1(), req.getN2());
			break;
		case MULTIPLICAR:
			resultado = calc.multiplicar(req.getN1(), req.getN2());
			break;
		case DIVIDIR:
			resultado = calc.dividir(req.getN1(), req.getN2());
			break;
		default:
			return null;
		}

		// Cria o objeto Response contendo o resultado da operação
		return Response.fromData(resultado);
	}

}
