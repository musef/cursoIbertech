package persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import excepciones.ClienteSinSaldoException;

@Component
public class BancoDao implements BancoDaoInterface 
{
 
	
	public int cambiarSaldo(long dni, double incrementoSaldo,Connection con)  {
		String sql = "update clientes set saldo = saldo + " + incrementoSaldo 
				+ " where dni = " + dni; 
		int cuantos = 0;
		try {
			cuantos=con.createStatement().executeUpdate(sql);
			System.out.println("Se ha incrementado el saldo de " 
						+ dni + " en " + incrementoSaldo + " euros" );
			
		} catch (SQLException e) {
			if (e.getErrorCode()==1264)
				throw new ClienteSinSaldoException();
		}
		return cuantos;
	}

}





