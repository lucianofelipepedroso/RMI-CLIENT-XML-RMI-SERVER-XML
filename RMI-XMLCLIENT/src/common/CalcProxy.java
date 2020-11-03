package common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import request.Request;
import request.Request.Op;
import response.Response;

public class CalcProxy implements Calc {

	private Socket socket;

	private DataInputStream dis;

	private DataOutputStream dos;

	private void initSocket() throws CalcException {
		try {
			socket = new Socket("localhost", 3434);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			throw new CalcException(e);
		}
	}

	private void closeSocket() throws CalcException {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				throw new CalcException(e);
			}
		}
	}

	private Response invoke(Request resq) throws CalcException {

		try {

			System.out.println("Req: " + resq.toXML());
			dos.writeUTF(resq.toXML());
			dos.flush();

			String xmlResponse = dis.readUTF();
			System.out.println("Resp: " + xmlResponse);

			return Response.fromXML(xmlResponse);

		} catch (Exception e) {
			throw new CalcException(e);

		}

	}

	@Override
	public double somar(double n1, double n2) throws CalcException {
		initSocket();
		try {

			Request request = Request.fromData(Op.SOMAR, n1, n2);
			Response response = invoke(request);
			return response.getResultado();
		} finally {
			closeSocket();
		}

	}

	@Override
	public double subtrair(double n1, double n2) throws CalcException {
		initSocket();
		try {

			Request request = Request.fromData(Op.SUBTRAIR, n1, n2);
			Response response = invoke(request);
			return response.getResultado();
		} finally{
			closeSocket();
		}
	}

	@Override
	public double dividir(double n1, double n2) throws CalcException {
		initSocket();
		try {

			Request request = Request.fromData(Op.DIVIDIR, n1, n2);
			Response response = invoke(request);
			return response.getResultado();
		}finally{
			closeSocket();
		}
	}

	@Override
	public double multiplicar(double n1, double n2) throws CalcException {
		initSocket();
		try {

			Request request = Request.fromData(Op.MULTIPLICAR, n1, n2);
			Response response = invoke(request);
			return response.getResultado();
		} finally{
			closeSocket();
		}
	}

}
