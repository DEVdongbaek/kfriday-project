package com.kfriday.kevin.repository;

import com.kfriday.kevin.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    @Query("select i " +
            "from Image i " +
            "where i.packageId.id = :packageId " +
            "order by i.createdAt desc " +
            "join fetch i.packageId ")
    List<Image> findImagesByPackageId(Long packageId);

}
