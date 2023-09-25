package jdbc.modelo;

import java.sql.Date;

public class Huespedes {
	private Integer Id;
	private String Nombre;
	private String Apellido;
	private Date FechaNacimiento;
	private Integer Nacionalidad;
	private String emailHuesped;
	private String Telefono;

	public Huespedes(String nombre, String apellido, Date fechaNacimiento, Integer nacionalidad, String email,
			String telefono) {
		super();
		Nombre = nombre;
		Apellido = apellido;
		FechaNacimiento = fechaNacimiento;
		Nacionalidad = nacionalidad;
		emailHuesped = email;
		Telefono = telefono;
	}

	public Huespedes(Integer id, String nombre, String apellido, Date fechaNacimiento, Integer nacionalidad,
			String email, String telefono) {
		super();
		Id = id;
		Nombre = nombre;
		Apellido = apellido;
		FechaNacimiento = fechaNacimiento;
		Nacionalidad = nacionalidad;
		emailHuesped = email;
		Telefono = telefono;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public Integer getNacionalidad() {
		return Nacionalidad;
	}

	public void setNacionalidad(Integer nacionalidad) {
		Nacionalidad = nacionalidad;
	}

	public Date getFechaNacimiento() {
		return FechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}

	public String getEmailHuesped() {
		return emailHuesped;
	}

	public void setEmailHuesped(String email) {
		emailHuesped = email;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

}
