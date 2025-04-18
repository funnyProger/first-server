package com.tregubov.firstserver.entities.order;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(catalog = "store", schema = "public", name = "order_status")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description", nullable = false)
    private String description;

}
