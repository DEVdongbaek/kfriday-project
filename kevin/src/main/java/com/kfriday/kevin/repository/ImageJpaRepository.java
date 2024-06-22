package com.kfriday.kevin.repository;

import com.kfriday.kevin.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    @Query("select i " +
            "from Image i " +
            "join fetch i.packageEntity " +
            "where i.packageEntity.id = :packageId " +
            "order by i.createdAt desc")
    List<Image> findImagesByPackageId(Long packageId);

}
