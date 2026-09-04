package com.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_fidelities")
@Data
public class Fidelity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Integer orderCount = 0;

    @Column(nullable = false)
    private Boolean rewardAvailable = false;
}
