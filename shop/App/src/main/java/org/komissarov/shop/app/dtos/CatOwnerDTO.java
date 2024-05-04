package org.komissarov.shop.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CatOwnerDTO {
    private long id;
    private String name;
    private LocalDate birthDate;
    private List<CatDTO> cats;

    public CatOwnerDTO() {
        this.cats = new ArrayList<>();
    }

    public CatOwnerDTO(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = new ArrayList<>();
    }
}
