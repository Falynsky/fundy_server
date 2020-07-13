package com.falynsky.smartmarkt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "id", nullable = false)
    public int id;
    @Column(name = "name", nullable = false)
    public String name;
    @Column(name = "quantity", nullable = false)
    public int quantity;
    @Column(name = "price", nullable = false)
    public float price;
    @Column(name = "currency", nullable = false)
    public String currency;
    @Column(name = "product_type_id", nullable = false)
    public int productTypeId;
    @Column(name = "productInfo", nullable = false)
    public String productInfo;
}

