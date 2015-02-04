package fmsSoft.A09_Spring_Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class PoolConexiones {

	public static void main(String[] args) throws SQLException {
		@SuppressWarnings("resource")
		BeanFactory ctx=new ClassPathXmlApplicationContext("E03applicationContext.xml");
		
		/*
		 * POOL DE CONEXIONES JAVA
		 * 
		BasicDataSource pool=new BasicDataSource();
		pool.setDriverClassName("com.mysql.jdbc.Driver");
		pool.setUrl("jdbc:mysql://192.168.2.6:3306/DATOS");
		pool.setUsername("alumno1");
		pool.setPassword("java");*/
		
		// POOL DE CONEXIONES SPRING
		BasicDataSource pool=(BasicDataSource) ctx.getBean("dataSource");
	
		Connection con= pool.getConnection();
		String sql="SELECT * FROM clientes WHERE dni<10";
		ResultSet rs=con.createStatement().executeQuery(sql);
		
		while(rs.next()) {
			System.out.println(rs.getString("nombre")+"**"+rs.getLong("dni"));
		}
		// esto no cierra la conexion, sino que avisa al pool del final de la transaccion
		// para que el pool la utilice en otro proceso cualquiera
		con.close();
		// aqui si estamos cerrando el pool y por tanto las conexiones
		pool.close();
	}

}
