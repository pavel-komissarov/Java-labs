package org.komissarov.shop.app.services;

import lombok.Getter;
import org.komissarov.shop.da.entity.CatOwner;
import org.komissarov.shop.da.entity.Role;
import org.komissarov.shop.da.entity.User;
import org.komissarov.shop.da.repositories.CatOwnerRepository;
import org.komissarov.shop.da.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatOwnerRepository catOwnerRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetail(user);
    }

    @Transactional
    public void addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }

        Role role = Role.USER;
        user.setRole(role);

        userRepository.save(user);
    }

    @Transactional
    public void addCatOwner(User user, Long catOwnerId) {
        CatOwner catOwner = catOwnerRepository.getReferenceById(catOwnerId);

        user.setCatOwner(catOwner);
        catOwner.setUser(user);

        catOwnerRepository.save(catOwner);
        userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
