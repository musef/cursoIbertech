package excepciones;

@SuppressWarnings("serial")
public class ClienteSinSaldoException extends RuntimeException
{
	public String getMessage() {
		return "Un cliente no tiene saldo suficiente";
	}
} 
