package com.zerobase.fastlms.member.model;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginHistoryDto {
    private long id;
    private String userId;
    private LocalDateTime loginDt;
    private String ip;
    private String userAgent;

    public static LoginHistoryDto of(LoginHistory loginHistory) { 
        return LoginHistoryDto.builder()
                .userId(loginHistory.getUserId())
                .loginDt(loginHistory.getLoginDt())
                .ip(loginHistory.getIp())
                .userAgent(loginHistory.getUserAgent())
                .build();
    }
    
    public static List<LoginHistoryDto> of(List<LoginHistory> loginsHistories){ //상세정보용 dto list 반환
        if(loginsHistories ==null){
            return null;
        }
        List<LoginHistoryDto> result= new ArrayList<>();
        int size =loginsHistories.size();
        for (LoginHistory x:loginsHistories){
            result.add(
                    LoginHistoryDto.builder()
                            .id(size--)
                            .userId(x.getUserId())
                            .loginDt(x.getLoginDt())
                            .ip(x.getIp())
                            .userAgent(x.getUserAgent())
                            .build());
        }
        
        return result;
    }
    

    public String getLoginDtText() { //로그인 일자
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd MM:mm");
        return loginDt != null ? loginDt.format(formatter) : "";
    }
}
