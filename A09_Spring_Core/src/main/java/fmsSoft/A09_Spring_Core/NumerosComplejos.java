package fmsSoft.A09_Spring_Core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import agenda.NumeroComplejo;

public class NumerosComplejos {

	public static void main(String args[]) {
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("E01applicationContext.xml");
		NumeroComplejo num=(NumeroComplejo) ctx.getBean("numComplejo");
		System.out.println("NUM: "+num.getX()+"**"+num.getY()+"-->"+num.getModulo());
		
		NumeroComplejo num2=(NumeroComplejo) ctx.getBean("num2Complejo");
		System.out.println("NUM2: "+num.getX()+"**"+num.getY()+"-->"+num.getModulo());
		
		NumeroComplejo res1=num.sumar(num2);
		NumeroComplejo res2=num.multiplicar(num2);
		
		System.out.println("RES: "+res1.getX()+"**"+res1.getY()+"-->"+res1.getModulo());
		System.out.println("RES2: "+res2.getX()+"**"+res2.getY()+"-->"+res2.getModulo());
		
	}
}
