package client;

import common.Calc;
import common.CalcException;
import common.CalcProxy;

public class Aplicacao {

	public static void main(String[] args) throws CalcException {
		
		Calc calc = new CalcProxy();

		System.out.println(calc.somar(10, 20));
		System.out.println(calc.subtrair(50, 10));
		System.out.println(calc.multiplicar(10, 2));
		System.out.println(calc.dividir(100, 10));
	}

}
