package com.mlt.entity;

public class AccountArray {
	private Account[] accounts;
	private int size;

	public int getSize() {
		size = accounts.length;
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Account[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Account[] accounts) {
		this.accounts = accounts;
	}
}
