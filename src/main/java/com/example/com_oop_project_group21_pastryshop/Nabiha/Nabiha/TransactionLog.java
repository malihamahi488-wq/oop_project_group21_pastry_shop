package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

public class TransactionLog {
    private String transactionType, transactionId, reciverName, reciverID;
    private int Amount;

    public TransactionLog() {
    }

    public TransactionLog(String transactionType, String transactionId, String reciverName, String reciverID, int amount) {
        this.transactionType = transactionType;
        this.transactionId = transactionId;
        this.reciverName = reciverName;
        this.reciverID = reciverID;
        Amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getReciverName() {
        return reciverName;
    }

    public String getReciverID() {
        return reciverID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public void setReciverID(String reciverID) {
        this.reciverID = reciverID;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "transactionType='" + transactionType + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", reciverName='" + reciverName + '\'' +
                ", reciverID='" + reciverID + '\'' +
                ", Amount=" + Amount +
                '}';
    }
}
