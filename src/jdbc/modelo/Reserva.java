package jdbc.modelo;

import java.sql.Date;

public class Reserva {

	private Integer id;
	private Integer huesped_id;
	private Integer habitacion_id;
	private Date fechaE;
	private Date fechaS;
	private String precioReserva;
	private Integer formaPago;
	private String numeroReserva;

	public Reserva(Integer huesped_id, Integer habitacion_id, Date fechaE, Date fechaS, String valor, Integer formaPago,
			String reserva) {
		this.huesped_id = huesped_id;
		this.habitacion_id = habitacion_id;
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.precioReserva = valor;
		this.formaPago = formaPago;
		this.numeroReserva = reserva;
	}

	public Reserva(Integer id, Integer huesped_id, Integer habitacion_id, Date fechaE, Date fechaS, String valor,
			Integer formaPago, String reserva) {
		this.id = id;
		this.huesped_id = huesped_id;
		this.habitacion_id = habitacion_id;
		this.fechaE = fechaE;
		this.fechaS = fechaS;
		this.precioReserva = valor;
		this.formaPago = formaPago;
		this.numeroReserva = reserva;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHuesped_id() {
		return huesped_id;
	}

	public void setHuesped_id(Integer huesped_id) {
		this.huesped_id = huesped_id;
	}

	public Integer getHabitacion_id() {
		return habitacion_id;
	}

	public void setHabitacion_id(Integer habitacion_id) {
		this.habitacion_id = habitacion_id;
	}

	public Date getFechaE() {
		return fechaE;
	}

	public void setFechaE(Date fechaE) {
		this.fechaE = fechaE;
	}

	public Date getFechaS() {
		return fechaS;
	}

	public void setFechaS(Date fechaS) {
		this.fechaS = fechaS;
	}

	public String getPrecioReserva() {
		return precioReserva;
	}

	public void setPrecioReserva(String precioReserva) {
		this.precioReserva = precioReserva;
	}

	public Integer getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(Integer formaPago) {
		this.formaPago = formaPago;
	}

	public String getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(String numeroReserva) {
		this.numeroReserva = numeroReserva;
	}

}
