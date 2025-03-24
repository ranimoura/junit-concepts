package br.com.rmo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.rmo.entities.Account;
import br.com.rmo.factory.AccountFactory;

public class AccountTests {

	Account emptyAccount;
	Account account;

	@BeforeEach
	public void initializeParameters() {
		emptyAccount = AccountFactory.createNewEmptyAccount();
		account = AccountFactory.createNewAccount(100.0);
	}

	@Test
	@DisplayName("Depósito deve aumentar o saldo quando a quantia (amount) for positiva")
	public void depositShouldIncreaseBalanceWhenPositiveAmount() {

		double amount = 200.0;
		double expectedValue = 196.0;

		emptyAccount.deposit(amount);

		Assertions.assertEquals(expectedValue, emptyAccount.getBalance(),
				() -> "The assertion did not find the expected result!");
	}

	@Test
	@DisplayName("Depósito NÃO deve alterar o saldo quando a quantia (amount) for negativa")
	public void depositShouldDoNothingWhenNegativeAmount() {

		double amount = -200.0;
		double expectedValue = 100.0;

		account.deposit(amount);

		Assertions.assertEquals(expectedValue, account.getBalance(),
				() -> "The assertion did not find the expected result!");

	}

	@Test
	@DisplayName("A operação de saque total deve limpar o saldo da conta")
	public void fullWithdrawShouldClearBalance() {

		double expectedValue = 0.0;
		double initialBalance = account.getBalance();

		double result = account.fullwithdraw();

		Assertions.assertTrue(expectedValue == account.getBalance());
		Assertions.assertTrue(result == initialBalance);

	}

	@Test
	@DisplayName("A operação de saque comum deve diminuir o saldo havendo saldo suficiente")
	public void withdrawShouldDecreaseBalanceWhenSufficientBalance() {

		double amount = 40.0;
		double expectedValue = 60.0;

		account.withdraw(amount);

		Assertions.assertEquals(expectedValue, account.getBalance());

	}

	@Test
	@DisplayName("A operação de saque comum deve lançar uma exceção quando não houver saldo suficiente")
	public void withdrawShouldThrowExceptionWhenInsufficientBalance() {

		double amount = 110.0;

		Assertions.assertThrows(IllegalArgumentException.class, () -> {

			account.withdraw(amount);
		});

	}

}
