package presentacion;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import excepciones.ClienteNoExisteException;
import excepciones.ClienteSinSaldoException;
import negocio.BancoNegocioInterface;

@Component
public class BancoVista implements BancoVistaInterface {
	
	@Autowired
	private BancoNegocioInterface bancoNegocio;
	
	@SuppressWarnings("resource")
	public void iniciarAplicacion() 
	{
		Scanner teclado = new Scanner(System.in);
		System.out.print("Primer dni:");
		long dni1 = Long.parseLong(teclado.nextLine());
		System.out.print("Segundo dni:");
		long dni2 = Long.parseLong(teclado.nextLine());
		System.out.print("Cantidad a transferir:");
		double cantidad = Double.parseDouble(teclado.nextLine());
		System.out.println("----------- INICIO TRANSFERENCIA ------");
		try {
			bancoNegocio.transferencia(dni1, dni2, cantidad);
			System.out.print("Transferencia realizada correctamente");
		} catch (ClienteSinSaldoException e) {
			System.out.print("Transferencia NO REALIZADA: "+e.getMessage());
		} catch (ClienteNoExisteException e) {
			System.out.print("Transferencia NO REALIZADA: "+e.getMessage());
		}
		
		System.out.println("----------- FIN TRANSFERENCIA ------");
	}
	

	
	
	
	
}
