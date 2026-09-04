package com.delivery.repository;

import com.delivery.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository; // ADICIONE ESTE IMPORT
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> { // EXTEGUE JPAREPOSITORY AQUI
    List<Banner> findByActiveTrueOrderByDisplayOrderAsc();
}
