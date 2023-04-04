package com.zerobase.fastlms.admin.entity;

import com.zerobase.fastlms.admin.dto.BannerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bannerName;

    private String filename;
    private String urlFilename;

    @Enumerated(EnumType.STRING)
    private BannerStatus target;
    private String link; //이미지링크
    private int sortValue; //정렬기준

    private boolean usingYn; //사용여부

    private LocalDateTime regDt;
}
