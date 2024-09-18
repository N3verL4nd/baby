package cn.lchospital.baby;

import cn.lchospital.baby.config.CustomProperties;
import cn.lchospital.baby.dto.LoginRespDto;
import cn.lchospital.baby.dto.StudentDto;
import cn.lchospital.baby.service.BabyService;
import cn.lchospital.baby.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class BabyApplicationTests {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private BabyService babyService;

    @Test
    void login() {
        // 创建要发送的Map对象
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userType", 3);
        requestBody.put("oauthType", 1);
        requestBody.put("oauthId", customProperties.getOauthId());
        requestBody.put("unionId", customProperties.getUnionId());

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("App-Type", "3");
        headers.add("Brand", "2");

        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // 将Map对象和请求头包装成HttpEntity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送POST请求
        String response = restTemplate.postForObject("https://kid-app-api.ancda.com/v1/auth/oauth/tokens", requestEntity, String.class);

        LoginRespDto loginRespDto = JsonUtils.fromJson(response, LoginRespDto.class);

        log.info(JsonUtils.toJson(loginRespDto));
    }

    @Test
    void studentInfo() {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "FH7hP8z0uOiuGu3nXHD5UrnMvPj84_JETIDheD60JKs=");

        // 创建 HttpEntity 并附加 headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("https://kid-app-api.ancda.com/v1/contact/students/{studentId}", HttpMethod.GET, requestEntity, String.class, "87712868429987853");
        // 获取响应体
        String responseBody = response.getBody();

        log.info(responseBody);
    }

    @Test
    void testRefresh() {
        StudentDto loginStudent = babyService.getLoginStudent();
        if (loginStudent == null) {
            babyService.login();
        }
        loginStudent = babyService.getLoginStudent();

        System.out.println(loginStudent);
    }
}
