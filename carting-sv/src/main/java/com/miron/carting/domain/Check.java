package com.miron.carting.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checks")
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int payingSum;
}
