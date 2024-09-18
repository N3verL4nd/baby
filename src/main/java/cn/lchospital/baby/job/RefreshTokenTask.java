package cn.lchospital.baby.job;

import cn.lchospital.baby.dto.StudentDto;
import cn.lchospital.baby.service.BabyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RefreshTokenTask {

    @Autowired
    private BabyService babyService;

    /**
     * 60s 轮训一次
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void executeTask() {
        StudentDto loginStudent = babyService.getStudent();
        if (loginStudent == null) {
            babyService.login();
            log.info("登录 token 失效，已刷新");
        } else {
            log.info("登录 token 未失效, token: {}", babyService.token());
        }
    }
}
