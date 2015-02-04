package agenda;

public interface PersonaInterface 
{
	public int getId();
	public void setId(int id);
	public String getNombre();
	public void setNombre(String nombre);
	public String getTelefono();
	public void setTelefono(String telefono);
	public String imprimirArbol(String tab);
	public PersonaInterface getPadre();
	public void setPadre(PersonaInterface padre);
	public PersonaInterface getMadre();
	public void setMadre(PersonaInterface madre);
}
