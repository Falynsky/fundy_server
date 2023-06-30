package com.falynsky.fundy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(nullable = false)
    public int id;
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public double amount;
    @Column(name = "transaction_info", nullable = false)
    public String transactionInfo;
    @OneToOne
    @JoinColumn(name = "document_id")
    public Document documentId;
    // @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    // @JoinColumn(name = "basket_id")

}
