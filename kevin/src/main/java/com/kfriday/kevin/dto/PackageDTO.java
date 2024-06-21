package com.kfriday.kevin.dto;

import com.kfriday.kevin.entity.Package;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

public class PackageDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDTO {

        @NotNull
        private Long trackingNo;

        @NotNull
        private List<ImageDTO.RequestDTO> images;

    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReqDTO {

        private Long trackingNo;

        private List<ImageDTO.UpdateDTO> images;

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDTO {

        private Long id;

        private Long trackingNo;

        private List<ImageDTO.ResponseDTO> images;

        public static ResponseDTO of(Package packageEntity, List<ImageDTO.ResponseDTO> images){
            return ResponseDTO.builder()
                    .id(packageEntity.getId())
                    .trackingNo(packageEntity.getTrackingNo())
                    .images(images)
                    .build();
        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDTOs {

        private List<ResponseDTO> responseDTOS;

        public static ResponseDTOs of(List<ResponseDTO> responseDTOS){
            return ResponseDTOs.builder()
                    .responseDTOS(responseDTOS)
                    .build();
        }

    }

}
