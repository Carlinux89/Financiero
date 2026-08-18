package com.krakedev.financiero.servicios.testJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	/**
	 * Valida que un depósito con monto positivo sea aceptado y que el saldo se
	 * incremente exactamente con el valor depositado.
	 */
	@Test
	public void testDepositarExitoso() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C001");

		boolean resultado = banco.depositar(100.0, cuenta);

		assertTrue(resultado);
		assertEquals(100.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que al depositar sobre una cuenta que ya tiene saldo, el nuevo saldo
	 * sea la suma del saldo anterior más el depósito.
	 */
	@Test
	public void testDepositarConSaldoPrevio() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C002");
		cuenta.setSaldoActual(200.0);

		boolean resultado = banco.depositar(50.0, cuenta);

		assertTrue(resultado);
		assertEquals(250.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que un depósito de valor cero sea rechazado y que el saldo de la
	 * cuenta no sea modificado.
	 */
	@Test
	public void testDepositarMontoCero() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C003");
		cuenta.setSaldoActual(300.0);

		boolean resultado = banco.depositar(0, cuenta);

		assertFalse(resultado);
		assertEquals(300.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que un depósito co* monto negativo sea rechazado y*que el saldo
	 * permanezca exactam*nte igual.
	 */
	@Test
	public void testDepositarMontoNegativo() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C004");
		cuenta.setSaldoActual(500.0);

		boolean resultado = banco.depositar(-50.0, cuenta);

		assertFalse(resultado);
		assertEquals(500.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que una cuenta creada quede asociada correctamente al cliente recibido
	 * como parámetro.
	 */
	@Test
	public void testCrearCuentaConPropietario() {

		Banco banco = new Banco();

		Cliente cliente = new Cliente("1723456789", "Carlos", "Chavez");

		Cuenta cuenta = banco.crearCuenta(cliente);

		assertEquals(cliente, cuenta.getPropietario());
	}

	/**
	 * Valida que un retiro con monto válido sea procesado correctamente y que el
	 * saldo disminuya en el valor retirado.
	 */
	@Test
	public void testRetirarExitoso() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C005");
		cuenta.setSaldoActual(500.0);

		boolean resultado = banco.retirar(100.0, cuenta);

		assertTrue(resultado);
		assertEquals(400.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida el caso límite donde el monto a retirar es exactamente igual al saldo
	 * disponible en la cuenta.
	 */
	@Test
	public void testRetirarSaldoCompleto() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C006");
		cuenta.setSaldoActual(300.0);

		boolean resultado = banco.retirar(300.0, cuenta);

		assertTrue(resultado);
		assertEquals(0.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que un retiro sea rechazado cuando el monto solicitado es mayor al
	 * saldo disponible en la cuenta.
	 */
	@Test
	public void testRetirarMontoMayorAlSaldo() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C007");
		cuenta.setSaldoActual(200.0);
		boolean resultado = banco.retirar(250.0, cuenta);

		assertFalse(resultado);
		assertEquals(200.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que un retiro con monto cero sea rechazado y que el saldo no cambie.
	 */
	@Test
	public void testRetirarMontoCero() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C008");
		cuenta.setSaldoActual(400.0);

		boolean resultado = banco.retirar(0.0, cuenta);
		assertFalse(resultado);
		assertEquals(400.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que un retiro con monto negativo sea rechazado y que el saldo
	 * permanezca sin modificaciones.
	 */
	@Test
	public void testRetirarMontoNegativo() {

		Banco banco = new Banco();

		Cuenta cuenta = new Cuenta("C009");
		cuenta.setSaldoActual(600.0);

		boolean resultado = banco.retirar(-5 * .0, cuenta);

		assertFalse(resultado);
		assertEquals(600.0, cuenta.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que una transferencia válida descuente el monto de la cuenta origen,
	 * incremente el saldo de la cuenta destino y retorne true.
	 */
	@Test
	public void testTransferenciaExitosa() {

		Banco banco = new Banco();

		Cuenta origen = new Cuenta("C010");
		Cuenta destino = new Cuenta("C011");

		origen.setSaldoActual(500.0);
		destino.setSaldoActual(100.0);

		boolean resultado = banco.transferir(origen, destino, 200.0);

		assertTrue(resultado);

		assertEquals(300.0, origen.getSaldoActual(), 0.0001);
		assertEquals(300.0, destino.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida el caso límite donde se transfiere exactamente todo el saldo
	 * disponible de la cuenta origen.
	 */
	@Test
	public void testTransferirSaldoCompleto() {

		Banco banco = new Banco();

		Cuenta origen = new Cuenta("C012");
		Cuenta destino = new Cuenta("C013");

		origen.setSaldoActual(400.0);
		destino.setSaldoActual(50.0);

		boolean resultado = banco.transferir(origen, destino, 400.0);

		assertTrue(resultado);

		assertEquals(0.0, origen.getSaldoActual(), 0.0001);
		assertEquals(450.0, destino.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que una transferencia sea rechazada cuando la cuenta origen no dispone
	 * de saldo suficiente para cubrir el monto solicitado.
	 */
	@Test
	public void testTransferenciaSaldoInsuficiente() {

		Banco banco = new Banco();

		Cuenta origen = new Cuenta("C014");
		Cuenta destino = new Cuenta("C015");

		origen.setSaldoActual(100.0);
		destino.setSaldoActual(200.0);

		boolean resultado = banco.transferir(origen, destino, 150.0);

		assertFalse(resultado);

		assertEquals(100.0, origen.getSaldoActual(), 0.0001);
		assertEquals(200.0, destino.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que una transferencia con monto cero sea rechazada y no modifique los
	 * saldos de ninguna de las cuentas.
	 */
	@Test
	public void testTransferenciaMontoCero() {

		Banco banco = new Banco();

		Cuenta origen = new Cuenta("C016");
		Cuenta destino = new Cuenta("C017");

		origen.setSaldoActual(300.0);
		destino.setSaldoActual(100.0);

		boolean resultado = banco.transferir(origen, destino, 0.0);

		assertFalse(resultado);

		assertEquals(300.0, origen.getSaldoActual(), 0.0001);
		assertEquals(100.0, destino.getSaldoActual(), 0.0001);
	}

	/**
	 * Valida que una transferencia con monto negativo sea rechazada y que los
	 * saldos permanezcan sin cambios.
	 */
	@Test
	public void testTransferenciaMontoNegativo() {

		Banco banco = new Banco();

		Cuenta origen = new Cuenta("C018");
		Cuenta destino = new Cuenta("C019");

		origen.setSaldoActual(500.0);
		destino.setSaldoActual(250.0);

		boolean resultado = banco.transferir(origen, destino, -50.0);

		assertFalse(resultado);

		assertEquals(500.0, origen.getSaldoActual(), 0.0001);
		assertEquals(250.0, destino.getSaldoActual(), 0.0001);
	}

}