package com.example.springsec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String userId;
    private String value;
}
