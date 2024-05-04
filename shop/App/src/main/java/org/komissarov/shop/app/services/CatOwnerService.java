package org.komissarov.shop.app.services;

import org.komissarov.shop.app.dtos.CatDTO;
import org.komissarov.shop.app.dtos.CatOwnerDTO;
import org.komissarov.shop.app.dtos.ColorDto;
import org.komissarov.shop.da.entity.Cat;
import org.komissarov.shop.da.entity.CatOwner;
import org.komissarov.shop.da.entity.Colors;
import org.komissarov.shop.da.repositories.CatOwnerRepository;
import org.komissarov.shop.da.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ComponentScan("org.komissarov.shop.da.repositories")
public class CatOwnerService {
    @Autowired
    private CatOwnerRepository catOwnerRepository;
    @Autowired
    private CatRepository catRepository;


    @Transactional
    public void addOwner(CatOwnerDTO catOwnerDTO) {
        catOwnerRepository.save(new CatOwner(catOwnerDTO.getName(), catOwnerDTO.getBirthDate()));
    }

    @Transactional
    public void removeCat(long ownerId, long catId) {
        CatOwner catOwner = catOwnerRepository.findById(ownerId).orElseThrow();
        Cat cat = catRepository.findById(catId).orElseThrow();

        catOwner.getCats().remove(cat);
        cat.setCatOwner(null);

        catOwnerRepository.save(catOwner);
        catRepository.save(cat);
    }

    @Transactional
    public void ownCat(long ownerId, long catId) {
        CatOwner catOwner = catOwnerRepository.findById(ownerId).orElseThrow();
        Cat cat = catRepository.findById(catId).orElseThrow();

        cat.setCatOwner(catOwner);

        catRepository.save(cat);
    }

    @Transactional
    public void removeOwner(long ownerId) {
        CatOwner catOwner = catOwnerRepository.findById(ownerId).orElseThrow();

        for (Cat cat : catOwner.getCats()) {
            cat.setCatOwner(null);
            catRepository.save(cat);
        }

        catOwnerRepository.delete(catOwner);
    }

    @Transactional
    public void updateOwner(CatOwnerDTO catOwnerDTO) {
        CatOwner catOwner = catOwnerRepository.findById(catOwnerDTO.getId()).orElseThrow();
        catOwner.setName(catOwnerDTO.getName());
        catOwner.setBirthDate(catOwnerDTO.getBirthDate());
        catOwnerRepository.save(catOwner);
    }

    public CatOwnerDTO getOwner(long ownerId) {
        CatOwner catOwner = catOwnerRepository.findById(ownerId).orElseThrow();
        List<CatDTO> catDTOs = catOwner.getCats().stream()
                .map(cat -> new CatDTO(cat.getId(), cat.getName(), transferColorToDto(cat.getColor()), cat.getBreed(), cat.getBirthDate()))
                .toList();
        return new CatOwnerDTO(catOwner.getId(), catOwner.getName(), catOwner.getBirthDate(), catDTOs);
    }

    public ColorDto transferColorToDto(Colors color) {
        return switch (color) {
            case Black -> ColorDto.Black;
            case White -> ColorDto.White;
            case Orange -> ColorDto.Orange;
            default -> ColorDto.None;
        };
    }

    public Colors transferDtoToColor(ColorDto colorDto) {
        return switch (colorDto) {
            case Black -> Colors.Black;
            case White -> Colors.White;
            case Orange -> Colors.Orange;
            default -> Colors.None;
        };
    }

    public List<CatOwnerDTO> getAllOwners() {
        return catOwnerRepository.findAll().stream()
                .map(catOwner -> new CatOwnerDTO(catOwner.getId(), catOwner.getName(), catOwner.getBirthDate(), catOwner.getCats().stream()
                        .map(cat -> new CatDTO(cat.getId(), cat.getName(), transferColorToDto(cat.getColor()), cat.getBreed(), cat.getBirthDate()))
                        .toList()))
                .toList();
    }
}
