package com.zerobase.fastlms.admin.model;

import com.zerobase.fastlms.admin.dto.BannerStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BannerInput {
    long id;
    String bannerName;

    String filename;
    String urlFilename;

    String link;
    BannerStatus target;
    int sortValue;
    boolean usingYn;

    LocalDate regDt;
}
