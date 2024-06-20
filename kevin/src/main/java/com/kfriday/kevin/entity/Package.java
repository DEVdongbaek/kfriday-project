package com.kfriday.kevin.entity;

import com.kfriday.kevin.dto.PackageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Package extends Common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long id;

    private Long trackingNo;

    public static Package of(PackageDTO packageDTO) {
        return Package.builder()
                .build();
    }

}
