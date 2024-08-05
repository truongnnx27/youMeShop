package com.poly.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Color")
public class Color {
    @Id
    private String idColor;

    @Column(name = "nameColor", columnDefinition = "nvarchar(50)")
    private String nameColor;

    @ManyToMany(mappedBy = "colors")
    List<Product> products;

    @PrePersist
    public void prePersist() {
        if (this.idColor == null || this.idColor.isEmpty()) {
            this.idColor = "color" + getNextId();
        }
    }

    private static long nextId = 1;

    private synchronized long getNextId() {
        return nextId++;
    }
}