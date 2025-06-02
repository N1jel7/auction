package by.n1jel.auction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lots_table")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "sold_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime soldAt;

    @Column(name = "is_sold")
    private boolean sold;

    @ManyToOne
    @JoinColumn(name = "buyer")
    private Trader buyer;

    @ManyToOne
    @JoinColumn(name = "seller")
    private Trader seller;
}
