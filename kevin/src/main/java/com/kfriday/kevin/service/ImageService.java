package com.kfriday.kevin.service;

import com.kfriday.kevin.dto.ImageDTO;
import com.kfriday.kevin.dto.PackageEventDTO;
import com.kfriday.kevin.entity.Image;
import com.kfriday.kevin.repository.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class ImageService {

    private final ImageJpaRepository imageJpaRepository;

    @Transactional
    public void saveImages(PackageEventDTO packageEventDTO){

        for (ImageDTO.RequestDTO requestDTO : packageEventDTO.getImages()) {
            imageJpaRepository.save(
                    Image.of(packageEventDTO.getPackageEntity(), requestDTO));
        }
    }

    @Transactional(readOnly = true)
    public List<ImageDTO.ResponseDTO> getImages(Long packageId){
        return imageJpaRepository.findImagesByPackageId(packageId)
                .stream()
                .map(ImageDTO.ResponseDTO::of).toList();
    }

}
