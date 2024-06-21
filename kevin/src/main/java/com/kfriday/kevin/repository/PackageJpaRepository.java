package com.kfriday.kevin.repository;

import com.kfriday.kevin.entity.Package;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PackageJpaRepository extends JpaRepository<Package, Long> {

    @Query("SELECT p " +
            "FROM Package p " +
            "ORDER BY p.id DESC")
    List<Package> findPackagesWithOffsetAndLimit(Pageable pageable);

}
