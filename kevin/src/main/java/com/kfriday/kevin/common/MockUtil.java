package com.kfriday.kevin.common;

import com.kfriday.kevin.dto.ImageDTO;
import com.kfriday.kevin.dto.PackageDTO;
import com.kfriday.kevin.entity.Image;
import com.kfriday.kevin.entity.ImageType;
import com.kfriday.kevin.entity.Package;

import java.util.ArrayList;
import java.util.List;

public class MockUtil { // 테스트간 Mock 객체 생성시 사용되는 Util 클래스

    public static PackageDTO.RequestDTO getPackageDtoRequestDTO(){

        List<ImageDTO.RequestDTO> images = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            images.add(getImageDtoRequestDto());
        }

        return new PackageDTO.RequestDTO(1L, images);
    }

    public static List<ImageDTO.RequestDTO> getImageDtoRequestDtos(){

        List<ImageDTO.RequestDTO> images = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            images.add(getImageDtoRequestDto());
        }

        return images;
    }

    public static List<ImageDTO.ResponseDTO> getImageDtoResponseDtos(){

        List<ImageDTO.ResponseDTO> images = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            images.add(getImageDtoResponseDto());
        }

        return images;
    }

    public static ImageDTO.ResponseDTO getImageDtoResponseDto(){
        return new ImageDTO.ResponseDTO("filnename", ImageType.PKG);
    }

    public static ImageDTO.RequestDTO getImageDtoRequestDto(){
        return new ImageDTO.RequestDTO("filnename", ImageType.PKG);
    }

    public static List<Package> getPackages(int offset, int limit){
        List<Package> packages = new ArrayList<>();

        for (int i = offset; i < limit; i++) {
            packages.add(getPackage());
        }

        return packages;
    }

    public static Package getPackage(){
        return Package.of(getPackageDtoRequestDTO());
    }

    public static List<Image> getImages(){
        List<Image> images = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            images.add(getImage());
        }

        return images;
    }

    public static Image getImage(){
        return Image.of(getPackage(), getImageDtoRequestDto());
    }
}
