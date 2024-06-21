package com.kfriday.kevin.dto;

import com.kfriday.kevin.entity.Image;
import com.kfriday.kevin.entity.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDTO {

        private String filename;

        private ImageType type;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateDTO {

        private String filename;

        private ImageType type;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDTO {

        private String filename;

        private ImageType type;

        public static ResponseDTO of(Image image){
            return ResponseDTO.builder()
                    .filename(image.getFilename())
                    .type(image.getType())
                    .build();
        }

    }

}