package agenda;

public class Persona implements PersonaInterface
{
	private int id;
	private String nombre;
	private String telefono;
	private PersonaInterface padre;
	private PersonaInterface madre;
	public Persona()
	{
		System.out.println("Constructor sin parámetros");
	}
	
	public Persona(int id, String nombre, String telefono) 
	{
		super();
		System.out.println("Constructor 3 parámetros (int,String,String)");
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
	}
	public Persona(int id, String nombre, long telefono) 
	{
		super();
		System.out.println("Constructor 3 parámetros (int,String,long)");
		this.id = id;
		this.nombre = nombre;
		this.telefono = "" + telefono;
	}
	public String toString() 
	{
		return nombre + "(" + telefono + ")";
	}
	// ---- METODOS GET Y SET ----------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		System.out.println("->Set id");
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String imprimirArbol(String tab) 
	{
		String s = tab + this.getNombre() + "\n";
		if(padre!=null )
			s+= padre.imprimirArbol(tab + "\t");
		if(madre!=null )
			s+= madre.imprimirArbol(tab + "\t");
		return s;
	}

	public PersonaInterface getPadre() {
		return padre;
	}

	public void setPadre(PersonaInterface padre) {
		this.padre = padre;
	}

	public PersonaInterface getMadre() {
		return madre;
	}

	public void setMadre(PersonaInterface madre) {
		this.madre = madre;
	}
	

}
