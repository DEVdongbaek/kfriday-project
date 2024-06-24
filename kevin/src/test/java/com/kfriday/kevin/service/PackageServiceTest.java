package com.kfriday.kevin.service;

import com.kfriday.kevin.common.MockUtil;
import com.kfriday.kevin.dto.ImageDTO;
import com.kfriday.kevin.dto.PackageDTO;
import com.kfriday.kevin.entity.Package;
import com.kfriday.kevin.repository.ImageJpaRepositoryTest;
import com.kfriday.kevin.repository.PackageJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {

    @InjectMocks
    private PackageService packageService;

    @Mock
    private ImageService imageService;

    @Mock
    private PackageJpaRepository packageJpaRepository;

    @Mock
    private ImageJpaRepositoryTest imageJpaRepositoryTest;

    @Mock
    private ApplicationEventPublisher eventPublisher;


    @Test
    public void get_package() {

        // given
        long fakeId = 1L;

        Package packageEntity = MockUtil.getPackage();

        List<ImageDTO.ResponseDTO> images = MockUtil.getImageDtoResponseDtos();

        // mocking
        given(packageJpaRepository.findById(any()))
                .willReturn(Optional.ofNullable(packageEntity));

        given(imageService.getImages(any()))
                .willReturn(images);

        // when
        PackageDTO.ResponseDTO dto = packageService.getPackage(fakeId);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getImages().size()).isEqualTo(3);
        assertThat(dto instanceof PackageDTO.ResponseDTO).isEqualTo(true);

    }

    @Test
    public void get_packages() {

        // given
        int offset = 0;

        int limit = 5;

        List<Package> packages = MockUtil.getPackages(offset, limit);

        // mocking
        given(packageJpaRepository.findPackagesWithOffsetAndLimit(any()))
                .willReturn(packages);

        // when
        PackageDTO.ResponseDTOs dto = packageService.getPackages(offset, limit);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getResponseDTOS().size()).isEqualTo(limit - offset);
        assertThat(dto instanceof PackageDTO.ResponseDTOs).isEqualTo(true);

    }
}
