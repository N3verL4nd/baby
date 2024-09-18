package cn.lchospital.baby.service.impl;

import cn.lchospital.baby.config.CustomProperties;
import cn.lchospital.baby.dto.*;
import cn.lchospital.baby.service.BabyService;
import cn.lchospital.baby.utils.DateUtil;
import cn.lchospital.baby.utils.JsonUtils;
import cn.lchospital.baby.vo.MediaVo;
import cn.lchospital.baby.vo.MomentVo;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class BabyServiceImpl implements BabyService {

    private static final String LOGIN_URL = "https://kid-app-api.ancda.com/v1/auth/oauth/tokens";
    private static final String STUDENT_INFO_URL = "https://kid-app-api.ancda.com/v1/contact/students/{studentId}";
    private static final String TEACHER_LIST_URL = "https://kid-app-api.ancda.com/v1/contact/staffs";
    private static final String CHOICE_STUDENT_URL = "https://kid-app-api.ancda.com/v1/auth/students/{studentId}/choice";
    private static final String MOMENTS_URL = "https://kid-app-api.ancda.com/v1/moments/list";
    private static final String MOMENT_H5_URL = "https://baby-mobile.ancda.com/moment/detail?instId=";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomProperties customProperties;

    private String token;

    private StudentDto studentDto;

    @Override
    public void login() {
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
        String response = restTemplate.postForObject(LOGIN_URL, requestEntity, String.class);
        log.info("登录返回结果: {}", response);

        if (StringUtils.isEmpty(response)) {
            return;
        }

        LoginRespDto loginRespDto = JsonUtils.fromJson(response, LoginRespDto.class);
        if (loginRespDto == null) {
            return;
        }

        if (CollectionUtils.isNotEmpty(loginRespDto.getStudentList())) {
            studentDto = loginRespDto.getStudentList().get(0);
        }
        token = loginRespDto.getToken();


        // 需要选择学生才能登录
        headers.add("Device-Identity", "BF81E757-EB70-4213-96E2-293CF8288DAB");
        headers.add("Authorization", token);

        restTemplate.exchange(CHOICE_STUDENT_URL, HttpMethod.GET, requestEntity, String.class, studentDto.getStudentId());
    }


    @Override
    public String token() {
        return token;
    }

    @Override
    public StudentDto getLoginStudent() {
        return studentDto;
    }

    @Override
    public StudentDto getStudent() {

        if (StringUtils.isEmpty(token) || studentDto == null || StringUtils.isEmpty(studentDto.getStudentId())) {
            return null;
        }

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        // 创建 HttpEntity 并附加 headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(STUDENT_INFO_URL, HttpMethod.GET, requestEntity, String.class, studentDto.getStudentId());

        if (StringUtils.isEmpty(response.getBody())) {
            token = "";
            studentDto = null;
            return null;
        }

        log.info("学生信息: {}", response.getBody());

        // 获取响应体
        String responseBody = response.getBody();
        Map<String, Object> responseBodyMap = JsonUtils.fromJson(responseBody, new TypeReference<Map<String, Object>>() {
        });

        if (responseBodyMap == null || responseBodyMap.containsKey("code")) {
            token = "";
            studentDto = null;
        }

        StudentDto student = new StudentDto();
        student.setStudentId(responseBodyMap.get("id") == null ? "" : String.valueOf(responseBodyMap.get("id")));
        student.setStudentName(responseBodyMap.get("name") == null ? "" : String.valueOf(responseBodyMap.get("name")));
        student.setAvatar(responseBodyMap.get("avatar") == null ? "" : String.valueOf(responseBodyMap.get("avatar")));

        return student;
    }

    @Override
    public String teacherList() {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        // 创建 HttpEntity 并附加 headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(TEACHER_LIST_URL, HttpMethod.GET, requestEntity, String.class);

        return response.getBody();
    }

    @Override
    public List<MomentVo> getMoments(String lastMomentId, Integer momentCategory) {
        List<MomentVo> res = new ArrayList<>();

        if (StringUtils.isEmpty(token)) {
            return res;
        }

        // 创建要发送的Map对象
        Map<String, Object> requestBody = new HashMap<>();
        // 班级动态: 1 班级圈: 2
        requestBody.put("momentCategory", momentCategory);
        requestBody.put("lastMomentId", lastMomentId);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // 将Map对象和请求头包装成HttpEntity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        MomentsRespDto momentsRespDto = restTemplate.postForObject(MOMENTS_URL, requestEntity, MomentsRespDto.class);
        if (momentsRespDto == null || CollectionUtils.isEmpty(momentsRespDto.getData())) {
            return res;
        }

        List<MomentDto> momentDtos = momentsRespDto.getData();
        for (MomentDto momentDto : momentDtos) {
            MomentVo momentVo = new MomentVo();

            momentVo.setMomentId(momentDto.getMomentId());
            momentVo.setContent(momentDto.getContent());
            momentVo.setUserId(momentDto.getUserId());
            momentVo.setAvatar(momentDto.getAvatar());
            momentVo.setUserName(momentDto.getUserName());
            momentVo.setUrl(MOMENT_H5_URL + customProperties.getInstId() + "&id=" + momentDto.getMomentId());
            momentVo.setCreateTime(DateUtil.timeStampsToLocalDateTimeStr(momentDto.getCreateTime() * 1000));

            if (CollectionUtils.isNotEmpty(momentDto.getMediaDtos())) {

                List<MediaVo> mediaVos = new ArrayList<>();

                List<MediaDto> mediaDtos = momentDto.getMediaDtos();
                for (MediaDto mediaDto : mediaDtos) {
                    MediaVo mediaVo = new MediaVo();

                    mediaVo.setMediaId(mediaDto.getMediaId());
                    mediaVo.setUrl(mediaDto.getUrl());

                    mediaVos.add(mediaVo);
                }

                momentVo.setMediaVos(mediaVos);
            }


            res.add(momentVo);
        }

        return res;
    }

}
