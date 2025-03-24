package br.com.rmo.factory;

import br.com.rmo.entities.Account;

public class AccountFactory {

	public static Account createNewEmptyAccount() {

		return new Account(1L, 0.0);
	}

	public static Account createNewAccount(double initialBalance) {

		return new Account(1L, initialBalance);
	}

}
