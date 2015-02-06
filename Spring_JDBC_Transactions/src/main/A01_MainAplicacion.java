package main;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import presentacion.BancoVistaInterface;

public class A01_MainAplicacion 
{
	public static void main(String[] args) 
	{
		// Obtener el contexto
		@SuppressWarnings("resource")
		BeanFactory ctx = new ClassPathXmlApplicationContext("A01applicationContext.xml");
		// Obtener la clase de inicio de la aplicación
		BancoVistaInterface bancoVista = (BancoVistaInterface) ctx.getBean("bancoVista");
		// Ejecutar el método de inicio de la aplicación
		bancoVista.iniciarAplicacion();
	}
}
