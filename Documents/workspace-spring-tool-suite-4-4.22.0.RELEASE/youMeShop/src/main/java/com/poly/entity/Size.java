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
@Table(name = "Size")
public class Size {
    @Id
    private String idSize;

    @Column(name = "nameSize", columnDefinition = "nvarchar(50)")
    private String nameSize;

    @ManyToMany(mappedBy = "sizes")
    List<Product> products;

    @PrePersist
    public void prePersist() {
        if (this.idSize == null || this.idSize.isEmpty()) {
            this.idSize = "size" + getNextId();
        }
    }

    private static long nextId = 1;

    private synchronized long getNextId() {
        return nextId++;
    }
}