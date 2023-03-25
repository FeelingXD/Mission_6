package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BannerMapper {
    long selectListCount(BannerParam id);
    List<BannerDto> selectList(BannerParam parameter);
    List<BannerDto> selectFrontList(); // 프론트 노출배너
}
