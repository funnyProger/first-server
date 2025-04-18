package com.tregubov.firstserver.entities.metadata;

import com.tregubov.firstserver.entities.order.AccountOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(catalog = "store", schema = "public", name = "order_metadata")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @Column(name = "order_id")
    private AccountOrder accountOrder;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
