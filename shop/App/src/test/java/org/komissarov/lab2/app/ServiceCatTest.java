package org.komissarov.lab2.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.komissarov.shop.app.services.CatService;
import org.komissarov.shop.app.services.UserService;
import org.komissarov.shop.da.repositories.CatOwnerRepository;
import org.komissarov.shop.da.repositories.CatRepository;
import org.komissarov.shop.da.entity.Cat;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@ComponentScan("org.komissarov.shop.da.repositories")
@ComponentScan("org.komissarov.shop.app.services")
@SpringBootTest(classes = {CatService.class, CatRepository.class})
public class ServiceCatTest {

    @MockBean
    private CatRepository catRepository;

    @MockBean
    private CatOwnerRepository ownerRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private CatService catService;

    @Test
    public void addFriendTest() {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        cat1.setId(1L);
        cat2.setId(2L);
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat1));
        when(catRepository.findById(2L)).thenReturn(Optional.of(cat2));

        catService.addFriend(1L, 2L);

        verify(catRepository).findById(1L);
        verify(catRepository).findById(2L);
        verify(catRepository).save(cat1);
    }

    @Test
    public void removeFriendTest() {
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        cat1.setId(1L);
        cat2.setId(2L);
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat1));
        when(catRepository.findById(2L)).thenReturn(Optional.of(cat2));

        catService.removeFriend(1L, 2L);

        verify(catRepository).findById(1L);
        verify(catRepository).findById(2L);
        verify(catRepository).save(cat1);
    }
}
