package com.tregubov.firstserver.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tregubov.firstserver.entities.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(catalog = "store", schema = "public", name = "comment")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "advantage")
    private String advantage;

    @Column(name = "disadvantage")
    private String disadvantage;

    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER)
    private Set<CommentImage> images = new HashSet<>();

    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER)
    private Set<CommentVideo> videos = new HashSet<>();

}
