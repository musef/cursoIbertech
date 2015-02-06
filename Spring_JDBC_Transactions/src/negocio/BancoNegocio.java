package negocio;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import excepciones.ClienteSinSaldoException;
import persistencia.BancoDaoInterface;

@Component
public class BancoNegocio implements BancoNegocioInterface 
{
	@Autowired
	private BancoDaoInterface bancoDao;
	@Autowired
	private DataSource dataSource;
	
	public void transferencia(long dni1, long dni2, double cantidad) 
	{
		Connection con=null;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			System.out.println("Inicio de la transferencia");
			
			if (bancoDao.cambiarSaldo(dni1, cantidad,con)==1){
				if (bancoDao.cambiarSaldo(dni2, -cantidad,con)==1) {
					con.commit();
					con.close();
				} else {
					con.rollback();
					con.close();
				}
			} else {
				con.rollback();
				con.close();
			}
			
		} catch (ClienteSinSaldoException e) {
			//con.rollback();
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw e;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} 


	}
	

	
	public BancoDaoInterface getBancoDao() {
		return bancoDao;
	}
	public void setBancoDao(BancoDaoInterface bancoDao) {
		this.bancoDao = bancoDao;
	}

}
