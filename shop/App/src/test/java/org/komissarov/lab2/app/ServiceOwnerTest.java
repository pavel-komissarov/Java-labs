package org.komissarov.lab2.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.komissarov.shop.app.dtos.CatOwnerDTO;
import org.komissarov.shop.app.services.CatOwnerService;
import org.komissarov.shop.da.entity.Cat;
import org.komissarov.shop.da.entity.CatOwner;
import org.komissarov.shop.da.repositories.CatOwnerRepository;
import org.komissarov.shop.da.repositories.CatRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {CatOwnerService.class, CatOwnerRepository.class, CatRepository.class})
public class ServiceOwnerTest {

    @MockBean
    private CatOwnerRepository catOwnerRepository;

    @MockBean
    private CatRepository catRepository;

    @Autowired
    private CatOwnerService catOwnerService;

    @Test
    public void addCattTest() {
        CatOwnerDTO catOwner = new CatOwnerDTO();
        catOwner.setName("Pasha");
        catOwner.setBirthDate(LocalDate.of(2004, 8, 6));

        catOwnerService.addOwner(catOwner);

        verify(catOwnerRepository).save(any(CatOwner.class));
    }

    @Test
    public void removeOwnerTest() {
        CatOwner catOwner = new CatOwner();
        catOwner.setId(1L);
        catOwner.setCats(new ArrayList<>());
        when(catOwnerRepository.findById(any())).thenReturn(Optional.of(catOwner));

        catOwnerService.removeOwner(1L);

        verify(catOwnerRepository).findById(1L);
        verify(catOwnerRepository).delete(catOwner);
    }

    @Test
    public void removeCatTest() {
        CatOwner catOwner = new CatOwner();
        Cat cat = new Cat();
        cat.setCatOwner(catOwner);
        List<Cat> cats = new ArrayList<>();
        cats.add(cat);
        catOwner.setCats(cats);

        when(catOwnerRepository.findById(1L)).thenReturn(Optional.of(catOwner));
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat));

        catOwnerService.removeCat(1L, 1L);

        verify(catOwnerRepository).findById(1L);
        verify(catRepository).findById(1L);
        verify(catOwnerRepository).save(catOwner);
        verify(catRepository).save(cat);
    }

    @Test
    public void ownCatTest() {
        CatOwner catOwner = new CatOwner();
        Cat cat = new Cat();
        catOwner.setId(1L);
        cat.setId(1L);
        when(catOwnerRepository.findById(1L)).thenReturn(Optional.of(catOwner));
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat));

        catOwnerService.ownCat(1L, 1L);

        verify(catRepository).save(cat);
        verify(catOwnerRepository).findById(1L);
        verify(catRepository).findById(1L);
    }
}
