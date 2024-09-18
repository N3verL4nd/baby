package cn.lchospital.baby.controller;

import cn.lchospital.baby.service.BabyService;
import cn.lchospital.baby.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/baby")
public class BabyController {

    @Autowired
    private BabyService babyService;


    @GetMapping("/token")
    public ApiResult<?> token() {
        return ApiResult.wrapSuccess(babyService.token());
    }

    @GetMapping(value = "/teacherList", produces = "application/json;charset=UTF-8")
    public String teacherList() {
        return babyService.teacherList();
    }

    @GetMapping("/loginStudent")
    public ApiResult<?> loginStudent() {
        return ApiResult.wrapSuccess(babyService.getLoginStudent());
    }

    @GetMapping("/studentInfo")
    public ApiResult<?> studentInfo() {
        return ApiResult.wrapSuccess(babyService.getStudent());
    }

    @GetMapping(value = "/moments")
    public ModelAndView moments(ModelAndView mv, @RequestParam(value = "lastMomentId", required = false, defaultValue = "0") String lastMomentId) {
        mv.addObject("moments", babyService.getMoments(lastMomentId, 1));
        mv.setViewName("baby");
        return mv;
    }


    @GetMapping(value = "/momentList")
    public ApiResult<?> momentList(@RequestParam(value = "lastMomentId", required = false, defaultValue = "0") String lastMomentId,
                                @RequestParam(value = "momentCategory", required = false, defaultValue = "1") Integer momentCategory) {
        return ApiResult.wrapSuccess(babyService.getMoments(lastMomentId, momentCategory));
    }

    @GetMapping(value = "/momentsApi", produces = "application/json;charset=UTF-8")
    public String momentsApi(@RequestParam(value = "lastMomentId", required = false, defaultValue = "0") String lastMomentId,
                             @RequestParam(value = "momentCategory", required = false, defaultValue = "1") Integer momentCategory) {
        return babyService.getMomentsApi(lastMomentId, momentCategory);
    }
}
