package com.kfriday.kevin.repository;

import com.kfriday.kevin.entity.Image;
import com.kfriday.kevin.entity.ImageType;
import com.kfriday.kevin.entity.Package;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PackageJpaRepositoryTest {

    @Autowired
    PackageJpaRepository packageJpaRepository;

    @Autowired
    ImageJpaRepository imageJpaRepository;

    @BeforeEach
    @Transactional
    void setup(){

        List<Package> packages = new ArrayList<>();

        Package package1 = Package.builder()
                .trackingNo(1L)
                .build();

        Package package2 = Package.builder()
                .trackingNo(2L)
                .build();

        Package package3 = Package.builder()
                .trackingNo(3L)
                .build();

        packages.add(package1);
        packages.add(package2);
        packages.add(package3);

        packageJpaRepository.saveAll(packages);

    }

    @Test
    @DisplayName("Offset과 limit을 이용한 페이지네이션")
    @Transactional
    public void find_packages_offset_limit(){

        // given
        PageRequest pageRequest = PageRequest.of(0, 2);

        // when
        List<Package> packages = packageJpaRepository.findPackagesWithOffsetAndLimit(pageRequest);

        // then
        assertThat(packages).isNotNull();
        assertThat(packages.size()).isEqualTo(2);
    }


}
