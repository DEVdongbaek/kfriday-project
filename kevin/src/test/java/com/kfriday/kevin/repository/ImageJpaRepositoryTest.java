package com.kfriday.kevin.repository;

import com.kfriday.kevin.entity.Image;
import com.kfriday.kevin.entity.ImageType;
import com.kfriday.kevin.entity.Package;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageJpaRepositoryTest {

    @Autowired
    PackageJpaRepository packageJpaRepository;

    @Autowired
    ImageJpaRepository imageJpaRepository;

    @BeforeEach
    @Transactional
    void setup(){

        Package packageOp = Package.builder()
                .trackingNo(1L)
                .build();

        Package packageEntity = packageJpaRepository.save(packageOp);

        Image image1 = Image.builder()
                .type(ImageType.PKG)
                .filename("filename1")
                .packageEntity(packageEntity)
                .build();

        Image image2 = Image.builder()
                .type(ImageType.PKG)
                .filename("filename2")
                .packageEntity(packageEntity)
                .build();

        imageJpaRepository.save(image1);
        imageJpaRepository.save(image2);

    }

    @Test
    @DisplayName("Package Id를 통한 Image 조회")
    @Transactional
    public void find_images_by_package_id(){

        // given
        // already given
        long fakeId = 1L;

        // when
        List<Image> images = imageJpaRepository.findImagesByPackageId(fakeId);

        // then
        assertThat(images).isNotNull();
        assertThat(images.size()).isEqualTo(2);
        assertThat(images.get(0).getFilename()).isNotEqualTo("filename1");

    }

}
