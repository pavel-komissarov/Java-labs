package org.komissarov.shop.da.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cats", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private String breed;

    @Enumerated(EnumType.STRING)
    private Colors color;

    @ManyToOne
    private CatOwner catOwner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Cat> friends;

    public Cat() {
        this.friends = new ArrayList<>();
    }

    public Cat(String name, Colors color, String breed, LocalDate birthDate) {
        this.name = name;
        this.color = color;
        this.breed = breed;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cat cat = (Cat) obj;
        return id.equals(cat.id);
    }
}
