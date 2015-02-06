package excepciones;

@SuppressWarnings("serial")
public class ClienteNoExisteException extends RuntimeException 
{
	public String getMessage() {
		return "No existe uno de los clientes implicados en la operación";
	}
}
