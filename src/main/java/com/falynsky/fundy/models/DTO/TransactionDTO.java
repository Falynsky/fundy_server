package com.falynsky.fundy.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    public int id;
    public String name;
    public double amount;
    public String transactionInfo;
    public Integer documentId;
}

