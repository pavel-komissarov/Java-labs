package org.komissarov.shop.app.services;

import org.komissarov.shop.app.dtos.CatDTO;
import org.komissarov.shop.app.dtos.ColorDto;
import org.komissarov.shop.da.entity.Cat;
import org.komissarov.shop.da.entity.CatOwner;
import org.komissarov.shop.da.entity.Colors;
import org.komissarov.shop.da.repositories.CatOwnerRepository;
import org.komissarov.shop.da.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@ComponentScan("org.komissarov.shop.da.repositories")
public class CatService {
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatOwnerRepository catOwnerRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Transactional()
    public void addCat(CatDTO catDTO) {
        catRepository.save(new Cat(catDTO.getName(), transferDtoToColor(catDTO.getColor()), catDTO.getBreed(), catDTO.getBirthDate()));
    }

    public boolean CheckOwner(Cat cat) {
        String catOwnerName = cat.getCatOwner().getName();


        String currentOwnerName = "";
        try {
            currentOwnerName = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e){
        }

        return Objects.equals(currentOwnerName, catOwnerName);
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

    public CatDTO getCat(long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow();
        return new CatDTO(cat.getId(), cat.getName(), transferColorToDto(cat.getColor()), cat.getBreed(), cat.getBirthDate());
    }

    @Transactional
    public void updateCat(CatDTO catDTO) {
        Cat cat = catRepository.findById((catDTO.getId())).orElseThrow();

        if (CheckOwner(cat)) {
            return;
        }

        cat.setName(catDTO.getName());
        cat.setColor(transferDtoToColor(catDTO.getColor()));
        cat.setBreed(catDTO.getBreed());
        cat.setBirthDate(catDTO.getBirthDate());

        catRepository.save(cat);
    }

    @Transactional
    public void removeCat(long catId) {
        Cat cat = catRepository.findById(catId).orElseThrow();

        if (CheckOwner(cat)) {
            return;
        }

        List<Cat> delCat = cat.getFriends();

        for (Cat fiend : delCat) {
            List<Cat> friends = fiend.getFriends();
            friends.remove(cat);
        }

        catRepository.deleteById(catId);
    }

    @Transactional
    public void addOwner(long catId, long ownerId) {
        Cat cat = catRepository.findById(catId).orElseThrow();

        CatOwner catOwner = catOwnerRepository.findById(ownerId).orElseThrow();
        catOwner.getCats().add(cat);
        catOwnerRepository.save(catOwner);
    }

    public List<CatDTO> getAllCats() {
        List<Cat> cats = new ArrayList<>();
        List<Cat> cats1 = catRepository.findAll();

        for (Cat cat : cats1) {
            if (CheckOwner(cat)) {
                cats.add(cat);
            }
        }

        return cats.stream().map(Cat -> new CatDTO(
                Cat.getId(),
                Cat.getName(),
                transferColorToDto(Cat.getColor()),
                Cat.getBreed(),
                Cat.getBirthDate()
        )).toList();
    }

    @Transactional
    public void addFriend(long catId, long friendId) {
        Cat cat = catRepository.findById(catId).orElseThrow();

        if (CheckOwner(cat)) {
            return;
        }

        Cat friend = catRepository.findById(friendId).orElseThrow();

        cat.getFriends().add(friend);
        friend.getFriends().add(cat);

        catRepository.save(cat);
        catRepository.save(friend);
    }

    @Transactional
    public void removeFriend(long catId, long friendId) {
        Cat cat = catRepository.findById(catId).orElseThrow();

        if (CheckOwner(cat)) {
            return;
        }

        Cat friend = catRepository.findById(friendId).orElseThrow();

        cat.getFriends().remove(friend);
        friend.getFriends().remove(cat);

        catRepository.save(cat);
        catRepository.save(friend);
    }

    public List<CatDTO> getAllCatsByColor(ColorDto color) {
        return catRepository.findAll().stream().map(Cat ->
                new CatDTO(
                        Cat.getId(),
                        Cat.getName(),
                        transferColorToDto(Cat.getColor()),
                        Cat.getBreed(),
                        Cat.getBirthDate()
                )).filter(cat -> cat.getColor().equals(color)).toList();
    }
}
