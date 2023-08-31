package org.example.model;

import java.math.BigDecimal;

public class Account {
    private final int owner;
    private final BigDecimal balance;
    private final long accountNumber;
    private final String created;
    private final String accountName;

    Account(AccountBuilder accountBuilder) {
        this.owner = accountBuilder.owner;
        this.balance = accountBuilder.balance;
        this.accountNumber = accountBuilder.accountNumber;
        this.accountName = accountBuilder.accountName;
        this.created = accountBuilder.accountName;
    }

    @Override
    public String toString() {
        return accountNumber + "\t\t" + accountName + "\t\t\t\t" + balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return String.valueOf(accountNumber);
    }

    public static class AccountBuilder {
        private final String accountName;
        private int owner;
        private BigDecimal balance = BigDecimal.ZERO;
        private long accountNumber;
        private String created = "";


        public AccountBuilder(String accountName) {
            this.accountName = accountName;
        }

        AccountBuilder owner(int owner) {
            this.owner = owner;
            return this;
        }

        AccountBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        AccountBuilder accountNumber(long accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        AccountBuilder created(String created) {
            this.created = created;
            return this;
        }

        public Account build() {
            Account account = new Account(this);
            verifyAccount(account);
            return account;
        }

        void verifyAccount(Account account) {

        }
    }


}
