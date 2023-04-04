package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;

    //로그인 성공시 동작정의
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName(); //id 받아오기
        String ip = RequestUtils.getIp(request); // request 에서 아이피 받아오기
        String userAgent= RequestUtils.getUserAgent(request); //사용기기

        log.info("ip :" +ip +"userAgent : "+ userAgent);

        boolean result = memberService.insertLoginHistory(username,ip,userAgent); // 아이디, ip ,사용기기 저장

        if(!result){
            log.info("저장실패 login successHandler error");
        }

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
