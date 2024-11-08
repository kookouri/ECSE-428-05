package com.mcgillmart.McGillMart.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.mcgillmart.McGillMart.model.Transaction;

public class TransactionListDTO {
    List<TransactionResponseDTO> transactions = new ArrayList<>();
    String errorMessage;

    public TransactionListDTO() {

    }

    public TransactionListDTO(List<Transaction> transactions) {
        this.transactions = transactionListToTransactionResponseDTOList(transactions);
    }

    public TransactionListDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static List<TransactionResponseDTO> transactionListToTransactionResponseDTOList(List<Transaction> transactions) {
        return transactions.stream().map(TransactionResponseDTO::new).collect(Collectors.toList());
    }

    public void setTransactions(List<TransactionResponseDTO> transactions) {
        for (TransactionResponseDTO transaction : transactions) {
            this.transactions.add(transaction);
        }
    }

    public List<TransactionResponseDTO> getTransactions() {return transactions;}
}

