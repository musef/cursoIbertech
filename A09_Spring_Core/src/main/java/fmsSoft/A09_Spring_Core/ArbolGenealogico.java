package fmsSoft.A09_Spring_Core;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import agenda.PersonaInterface;

public class ArbolGenealogico {

	public static void main(String[] args) 
	{
		@SuppressWarnings("resource")
		BeanFactory ctx = new ClassPathXmlApplicationContext("E02applicationContext.xml");
		PersonaInterface p = (PersonaInterface) ctx.getBean("yoPersona");
		System.out.println(p.imprimirArbol("   "));
	}

}
