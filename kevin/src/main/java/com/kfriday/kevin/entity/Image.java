package com.kfriday.kevin.entity;

import com.kfriday.kevin.dto.ImageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Image extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package packageId;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    public static Image of(Package packageEntity, ImageDTO.RequestDTO requestDTO){
        return Image.builder()
                .packageId(packageEntity)
                .filename(requestDTO.getFilename())
                .type(requestDTO.getType())
                .build();
    }

}
