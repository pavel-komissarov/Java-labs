package org.komissarov.shop.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CatDTO {
    private long id;
    private String name;
    private ColorDto color;
    private String breed;
    private LocalDate birthDate;

    public CatDTO() {
    }

    public CatDTO(String name, ColorDto color, String breed, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.breed = breed;
        this.birthDate = birthDate;
    }
}
