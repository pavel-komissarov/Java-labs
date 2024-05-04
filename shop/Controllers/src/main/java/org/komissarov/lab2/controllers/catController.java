package org.komissarov.lab2.controllers;

import jakarta.validation.Valid;
import org.komissarov.shop.app.dtos.CatDTO;
import org.komissarov.shop.app.dtos.ColorDto;
import org.komissarov.shop.app.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cat")
@ComponentScan("org.komissarov.shop.app.services")
public class catController {
    @Autowired
    protected CatService catService;

    @PostMapping("/add")
    public void addCat(@Valid @RequestBody CatDTO catDTO) {
        catService.addCat(catDTO);
    }

    @GetMapping("/{catId}")
    public CatDTO getCat(@PathVariable long catId) {
        return catService.getCat(catId);
    }

    @PutMapping("/{catId}")
    public void updateCat(@PathVariable long catId, @RequestBody CatDTO catDTO) {
        catDTO.setId(catId);
        catService.updateCat(catDTO);
    }

    @PostMapping("/remove/{catId}")
    public void removeCat(@PathVariable long catId) {
        catService.removeCat(catId);
    }

    @GetMapping("/getAll")
    public List<CatDTO> getAllCats() {
        return catService.getAllCats();
    }

    @PostMapping("/addFriend/{catId}/{friendId}")
    public void addFriend(@PathVariable long catId, @PathVariable long friendId) {
        catService.addFriend(catId, friendId);
    }

    @PostMapping("/deleteFriend/{catId}/{friendId}")
    public void deleteFriend(@PathVariable long catId, @PathVariable long friendId) {
        catService.removeFriend(catId, friendId);
    }

    @GetMapping("/getCatsByColor")
    public List<CatDTO> getCatsByColor(@RequestParam String color) {
        return catService.getAllCatsByColor(ColorDto.valueOf(color));
    }
}
