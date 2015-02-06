package persistencia;

import java.sql.Connection;

public interface BancoDaoInterface 
{
	public int cambiarSaldo(long dni, double incrementoSaldo,Connection con);
}
