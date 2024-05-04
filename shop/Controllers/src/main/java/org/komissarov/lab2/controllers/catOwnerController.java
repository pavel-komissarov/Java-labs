package org.komissarov.lab2.controllers;

import jakarta.validation.Valid;
import org.komissarov.shop.app.dtos.CatDTO;
import org.komissarov.shop.app.dtos.CatOwnerDTO;
import org.komissarov.shop.app.services.CatOwnerService;
import org.komissarov.shop.app.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/owner")
@ComponentScan("org.komissarov.shop.app.services")
public class catOwnerController {
    @Autowired
    protected CatOwnerService catOwnerService;

    @Autowired
    protected CatService catService;

    @PostMapping("/add")
    public void addOwner(@Valid @RequestBody CatOwnerDTO catOwnerDTO) {
        catOwnerService.addOwner(catOwnerDTO);
    }

    @PostMapping("/removeCat/{ownerId}/{catId}")
    public void removeCat(@PathVariable long ownerId, @PathVariable long catId) {
        catOwnerService.removeCat(ownerId, catId);
    }

    @PostMapping("/ownCat/{ownerId}/{catId}")
    public void ownCat(@PathVariable long ownerId, @PathVariable long catId) {
        catOwnerService.ownCat(ownerId, catId);
    }

    @PostMapping("/removeOwner/{ownerId}")
    public void removeOwner(@PathVariable long ownerId) {
        catOwnerService.removeOwner(ownerId);
    }

    @PostMapping("/updateOwner")
    public void updateOwner(@RequestBody CatOwnerDTO catOwnerDTO) {
        catOwnerService.updateOwner(catOwnerDTO);
    }

    @GetMapping("/{ownerId}")
    public CatOwnerDTO getOwner(@PathVariable long ownerId) {
        return catOwnerService.getOwner(ownerId);
    }

    @GetMapping("/allOwners")
    public List<CatOwnerDTO> getAllOwners() {
        return catOwnerService.getAllOwners();
    }

    @GetMapping("/cats")
    public List<CatDTO> getAllCats() {
        return catService.getAllCats();
    }

}
