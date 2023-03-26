package com.zerobase.fastlms.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BannerStatus {
    LINK_BLANK("_blank","새창으로 열기"),
    LINK_SELF("_self","창에서 열기");

    private final String value;
    private String text;
}
