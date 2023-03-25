package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.BannerStatus;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter){
        parameter.init();

        List<BannerDto> bannerList = bannerService.list(parameter);

        long totalCount =0;
        if(!CollectionUtils.isEmpty(bannerList)){
            totalCount=bannerList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml =getPaperHtml(totalCount,parameter.getPageSize(), parameter.getPageIndex(), queryString);


        //Todo :banner list 불러온후 삽입
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("list",bannerList);
        model.addAttribute("pager",pagerHtml);
        return "admin/banner/list";
    }
    @GetMapping(value = {"/admin/banner/add.do","/admin/banner/edit.do"})
    public String add(Model model , HttpServletRequest request, BannerInput parameter){ // add edit 같은동작 하되 edit일 경우 추가처리

        boolean isEdit = request.getRequestURI().contains("edit");
        BannerDto detail = new BannerDto();

        if(isEdit){ // edit일 경우 수정
            long id =parameter.getId();
            BannerDto banner =bannerService.getById(id);
            if(banner==null){
                model.addAttribute("message", "배너 정보없음.");
                return "common/error";
            }
            detail=banner;
        }
      model.addAttribute("detail",detail);
      model.addAttribute("isEdit",isEdit);
      model.addAttribute("status", BannerStatus.values());
        return "admin/banner/add";
    }

    /**
     * 
     * @param model 첨부할내용
     * @param request 요청 
     * @param file 이미지파일
     * @param parameter get으로 넘어온 파라미터
     * @return
     */
    @PostMapping(value = {"/admin/banner/add.do","/admin/banner/edit.do"})
    public String submitBanner(Model model, HttpServletRequest request, MultipartFile file, BannerInput parameter){

        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {
            String realPath=request.getServletContext().getRealPath("/");
            String originalFilename = file.getOriginalFilename();

            String baseLocalPath = realPath.substring(0,realPath.indexOf("src")).replaceAll("\\\\","/").concat("files");
            log.info(baseLocalPath);
            String baseUrlPath = "/files";

            String[] arrFilenames = getNewSaveFile(baseLocalPath , baseUrlPath, originalFilename);

            saveFilename = arrFilenames[0];
            urlFilename = arrFilenames[1];

            try {
                log.warn(saveFilename);
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }

        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);

        boolean editMode = request.getRequestURI().contains("edit");

        if (editMode) {
            long id = parameter.getId();
            BannerDto exitBanner = bannerService.getById(id);
            if (exitBanner == null) {
                model.addAttribute("message", "배너정보가 존재하지 않습니다.");
                return "common/error";
            }

            boolean result = bannerService.update(parameter);

        } else {
            boolean result = bannerService.add(parameter);
        }

        return "redirect:/admin/banner/list.do";
    }


    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs ={
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir: dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;

        }


        return new String[]{newFilename, newUrlFilename};
    }

    @PostMapping("/admin/banner/delete.do")
    public String delete(Model model, HttpServletRequest request ,BannerInput parameter){
        boolean result= bannerService.del(parameter.getId());
        return"redirect:/admin/banner/list.do";
    }
}
