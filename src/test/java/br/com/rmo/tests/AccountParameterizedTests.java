package br.com.rmo.tests;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.rmo.entities.Account;
import br.com.rmo.factory.AccountFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountParameterizedTests {

	Account emptyAccount;
	Account account;

	@BeforeEach
	public void initializeParameters() {
		emptyAccount = AccountFactory.createNewEmptyAccount();
		account = AccountFactory.createNewAccount(100.0);
	}

	// PARÂMETROS PARA A QUANTIA POSITIVA:
	public static Stream<Arguments> depositShouldIncreaseBalanceWhenPositiveAmount() {

		double amount = 200.0;
		double expectedValue = 196.0;

		return Stream.of(Arguments.of(amount, expectedValue));
	}

	@ParameterizedTest
	@Order(1)
	@DisplayName("Depósito deve aumentar o saldo quando a quantia (amount) for positiva")
	@MethodSource()
	public void depositShouldIncreaseBalanceWhenPositiveAmount(double amount, double expectedValue) {

		emptyAccount.deposit(amount);

		Assertions.assertEquals(expectedValue, emptyAccount.getBalance(),
				() -> "The assertion did not find the expected result!");
	}

	// PARÂMETROS PARA A QUANTIA NEGATIVA:
	public static Stream<Arguments> deposit_NegativeParameters() {

		double amount = -200.0;
		double expectedValue = 100.0;

		return Stream.of(Arguments.of(amount, expectedValue));
	}

	@ParameterizedTest
	@Order(3)
	@DisplayName("Depósito NÃO deve alterar o saldo quando a quantia (amount) for negativa")
	@MethodSource("deposit_NegativeParameters")
	public void depositShouldDoNothingWhenNegativeAmount(double amount, double expectedValue) {

		account.deposit(amount);

		Assertions.assertEquals(expectedValue, account.getBalance(),
				() -> "The assertion did not find the expected result!");

	}

	@Test
	@Order(2)
	@DisplayName("A operação de saque total deve limpar o saldo da conta")
	public void fullWithdrawShouldClearBalance() {

		double expectedValue = 0.0;
		double initialBalance = account.getBalance();

		double result = account.fullwithdraw();

		Assertions.assertTrue(expectedValue == account.getBalance());
		Assertions.assertTrue(result == initialBalance);

	}

	@ParameterizedTest
	@Order(4)
	@Timeout(value = 30, unit = TimeUnit.MILLISECONDS)
	@DisplayName("A operação de saque comum deve diminuir o saldo havendo saldo suficiente")
	@CsvFileSource(resources = "/amount.csv")
	public void withdrawShouldDecreaseBalanceWhenSufficientBalance(double amount, double expectedValue) {

		account.withdraw(amount);

		Assertions.assertEquals(expectedValue, account.getBalance());

	}

	@ParameterizedTest
	@Order(0)
	@DisplayName("A operação de saque comum deve lançar uma exceção quando não houver saldo suficiente")
	@CsvSource({ "110.0", "120.0" })
	public void withdrawShouldThrowExceptionWhenInsufficientBalance(double amount) {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {

			account.withdraw(amount);
		});

	}

}
