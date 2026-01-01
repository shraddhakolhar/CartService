package com.scaler.cartservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "cart_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_email", "product_id"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * Snapshot of price from ProductService
     */
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    // STORED total price (unitPrice * quantity)
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        this.totalPrice = this.unitPrice * this.quantity;
    }
}
