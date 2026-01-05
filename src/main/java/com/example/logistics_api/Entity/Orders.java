package com.example.logistics_api.Entity;

import com.example.logistics_api.Enumeration.OrderStatus;
import com.example.logistics_api.Enumeration.ShippingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "\"orders\"") //mapping it to the database table named "orders"
@Entity
@Getter
@Setter
public class Orders{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Integer id;

    @Column(nullable = false, length=128)
    private String customerName;

    @Column(nullable=false)
    private double weight;

    @Column(nullable = false,length=128)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false,length=30)
    private ShippingType shippingType;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=20)
    private OrderStatus status;

    @Column(nullable = false)
    private double cost;

    //Constructors
    protected Orders(String customerName,
                     double weight,
                     String destination,
                     ShippingType ShippingType,
                     double cost) {
        this.customerName=customerName;
        this.weight = weight;
        this.destination=destination;
        this.shippingType=shippingType;
        this.cost=cost;
        this.status=OrderStatus.PENDING;
    }
    public Orders() {}
}