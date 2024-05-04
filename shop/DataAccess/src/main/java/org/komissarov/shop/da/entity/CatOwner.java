package org.komissarov.shop.da.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "owners", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@NoArgsConstructor
public class CatOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "catOwner",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Cat> cats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public CatOwner(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
}
