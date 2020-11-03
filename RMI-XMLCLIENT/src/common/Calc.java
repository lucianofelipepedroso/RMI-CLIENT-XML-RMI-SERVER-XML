package common;

public interface Calc {

	double somar(double n1,double n2) throws CalcException;
	
	double subtrair(double n1,double n2) throws CalcException;
	
	double dividir(double n1,double n2) throws CalcException;
	
	double multiplicar(double n1,double n2) throws CalcException;
}
