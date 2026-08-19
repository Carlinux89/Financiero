package com.krakedev.financiero.entidades;

public class Cliente {

	private String cedula;
	private String nombre;
	private String apellido;
	private Direccion direccionDomicilio;

	public Cliente(String cedula, String nombre, String apellido) {

		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		direccionDomicilio = new Direccion();
	}

	public Cliente() {
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Direccion getDireccionDomicilio() {
		return direccionDomicilio;
	}

	public void setDireccionDomicilio(Direccion direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}

	public void imprimir() {
		String mensaje;
		mensaje = "Cédula: " + cedula + ", Nombre: " + nombre + ", Apellido: " + apellido;
		System.out.println(mensaje);
	}

}
