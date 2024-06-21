package com.kfriday.kevin.entity;

import com.kfriday.kevin.dto.PackageDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(indexes = @Index(name = "idx_createdAt", columnList = "createdAt"))
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

    public void updateTrackingNo(PackageDTO.UpdateReqDTO updateReqDTO){

        long newTrackingNo = updateReqDTO.getTrackingNo();

        if (trackingNo != newTrackingNo && newTrackingNo != 0){
            this.trackingNo = newTrackingNo;
        }
    }


    public static Package of(PackageDTO.RequestDTO requestDTO) {
        return Package.builder()
                .trackingNo(requestDTO.getTrackingNo())
                .build();
    }

}
