package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService{
    private final BannerMapper bannerMapper;
    private final BannerRepository bannerRepository;


    @Override
    public boolean add(BannerInput parameter) { // 정보추가
        System.out.println(parameter.toString());;
        bannerRepository.save(Banner.builder()
                .bannerName(parameter.getBannerName())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .link(parameter.getLink())
                .target(parameter.getTarget())
                .sortValue(parameter.getSortValue())
                .usingYn(parameter.isUsingYn())
                .regDt(LocalDateTime.now())
                .build());

        return true;
    }

    @Override
    public boolean update(BannerInput parameter) {
        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getId());
        if(!optionalBanner.isPresent()){ // 데이터없는경우
            return false;
        }

        Banner banner = optionalBanner.get();
        banner.setBannerName(parameter.getBannerName());
        banner.setFilename(parameter.getFilename());
        banner.setUrlFilename(parameter.getUrlFilename());
        banner.setLink(parameter.getLink());
        banner.setTarget(parameter.getTarget());
        banner.setSortValue(parameter.getSortValue());
        banner.setUsingYn(parameter.isUsingYn());
        banner.setRegDt(LocalDateTime.now());

        bannerRepository.save(banner);

        return true;
    }

    @Override
    public List<BannerDto> list(BannerParam parameter) {
        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i=0;
            for (BannerDto x:list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount-parameter.getPageStart()-i);
                i++;
            }
        }
        return list;
    }

    @Override
    public BannerDto getById(long id) {
        return bannerRepository.findById(id).map(BannerDto::of).orElse(null);
    }

    @Override
    public boolean del(long id) {
        bannerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<BannerDto> frontList() {
        return bannerMapper.selectFrontList();
    }



}
