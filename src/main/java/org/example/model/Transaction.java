package org.example.model;

public class Transaction {
    private final int id;
    private final String timestamp;
    private final String fromUser;
    private final String fromAccount;
    private final String toUser;
    private final String toAccount;
    private final float amount;
    private final String comment;

    Transaction(TransactionBuilder transactionBuilder) {
        this.id = transactionBuilder.id;
        this.timestamp = transactionBuilder.timestamp;
        this.fromUser = transactionBuilder.fromUser;
        this.fromAccount = transactionBuilder.fromAccount;
        this.toUser = transactionBuilder.toUser;
        this.toAccount = transactionBuilder.toAccount;
        this.amount = transactionBuilder.amount;
        this.comment = transactionBuilder.comment;

    }

    @Override
    public String toString() {
        return "timestamp='" + timestamp + '\'' +
                "fromUser='" + fromUser + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", toUser='" + toUser + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class TransactionBuilder {
        int id;
        String timestamp;
        String fromUser;
        String fromAccount;
        String toUser;
        String toAccount;
        float amount;
        String comment;

        TransactionBuilder id(int id) {
            this.id = id;
            return this;
        }

        TransactionBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        TransactionBuilder fromUser(String fromUser) {
            this.fromUser = fromUser;
            return this;
        }

        TransactionBuilder fromAccount(String fromAccount) {
            this.fromAccount = fromAccount;
            return this;
        }

        TransactionBuilder toUser(String toUser) {
            this.toUser = toUser;
            return this;
        }

        TransactionBuilder toAccount(String toAccount) {
            this.toAccount = toAccount;
            return this;
        }

        TransactionBuilder amount(float amount) {
            this.amount = amount;
            return this;
        }

        TransactionBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        Transaction build() {
            Transaction transaction = new Transaction(this);
            validateTransactionObject(transaction);
            return transaction;
        }

        private void validateTransactionObject(Transaction transaction) {
        }
    }
}
