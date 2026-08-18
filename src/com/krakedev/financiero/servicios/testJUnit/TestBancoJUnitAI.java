package com.krakedev.financiero.servicios.testJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBancoJUnitAI {

	/**
	 * Valida que el banco inicie con el código configurado por defecto.
	 */
	@Test
	public void testCodigoInicial() {

		Banco banco = new Banco();

		assertEquals(1000, banco.getUltimoCodigo());
	}

	/**
	 * Valida el funcionamiento del setter y getter de ultimoCodigo.
	 */
	@Test
	public void testSetterUltimoCodigo() {

		Banco banco = new Banco();

		banco.setUltimoCodigo(2000);

		assertEquals(2000, banco.getUltimoCodigo());
	}

	/**
	 * Valida que al crear una cuenta se asigne el código inicial y se incremente el
	 * contador.
	 */
	@Test
	public void testCrearPrimeraCuenta() {

		Banco banco = new Banco();
		Cliente cliente = new Cliente();

		Cuenta cuenta = banco.crearCuenta(cliente);

		assertNotNull(cuenta);
		assertEquals("1000", cuenta.getId());
		assertEquals(cliente, cuenta.getPropietario());

		assertEquals(1001, banco.getUltimoCodigo());
	}

	/**
	 * Valida que las cuentas generen códigos consecutivos.
	 */
	@Test
	public void testCrearCuentasConsecutivas() {

		Banco banco = new Banco();

		Cliente cliente1 = new Cliente();
		Cliente cliente2 = new Cliente();
		Cliente cliente3 = new Cliente();

		Cuenta cuenta1 = banco.crearCuenta(cliente1);
		Cuenta cuenta2 = banco.crearCuenta(cliente2);
		Cuenta cuenta3 = banco.crearCuenta(cliente3);

		assertEquals("1000", cuenta1.getId());
		assertEquals("1001", cuenta2.getId());
		assertEquals("1002", cuenta3.getId());

		assertEquals(1003, banco.getUltimoCodigo());
	}

	/**
	 * Valida que si se cambia el último código, las nuevas cuentas continúen la
	 * secuencia desde el valor configurado.
	 */
	@Test
	public void testCrearCuentaConCodigoPersonalizado() {

		Banco banco = new Banco();
		banco.setUltimoCodigo(5000);

		Cliente cliente = new Cliente();

		Cuenta cuenta = banco.crearCuenta(cliente);

		assertEquals("5000", cuenta.getId());
		assertEquals(5001, banco.getUltimoCodigo());
	}
}