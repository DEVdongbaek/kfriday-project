package com.kfriday.kevin.dto;

import com.kfriday.kevin.entity.Package;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageEventDTO {

    private Package packageEntity;

    private List<ImageDTO.RequestDTO> images;

    public static PackageEventDTO of(Package packageEntity, List<ImageDTO.RequestDTO> images) {
        return PackageEventDTO.builder()
                .packageEntity(packageEntity)
                .images(images)
                .build();

    }

}
