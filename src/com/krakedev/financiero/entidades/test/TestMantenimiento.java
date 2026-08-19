package com.krakedev.financiero.entidades.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestMantenimiento {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Cuenta cuenta1 = new Cuenta("CT001");
		Cliente cliente = new Cliente("100809050", "Andres", "Perez");
		cuenta1.setPropietario(cliente);
		cliente.getDireccionDomicilio().setCallePrincipal("Av Los Pinos 1234");
		Banco banco = new Banco();
		banco.depositar(50, cuenta1);

		boolean prueba1 = banco.cobrarMantenimiento(cuenta1, 10);
		System.out.println(prueba1);
		System.out.println(cuenta1.getSaldoActual());

		boolean prueba2 = banco.cobrarMantenimiento(cuenta1, 100);
		System.out.println(prueba2);
		System.out.println(cuenta1.getSaldoActual());

	}

}
