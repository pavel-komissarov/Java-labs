package org.komissarov.shop.da.repositories;

import org.komissarov.shop.da.entity.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
}
