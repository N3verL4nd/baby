package cn.lchospital.baby.handler;

import cn.lchospital.baby.enumtype.ServerCode;
import cn.lchospital.baby.exception.BizException;
import cn.lchospital.baby.vo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author n3verl4nd
 * @date 2020/3/25
 */
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    protected <T> ApiResult<T> defaultExceptionHandler(Exception e, HttpServletRequest request) {
        LOGGER.error("controller error, request: {}", request.getRequestURI(), e);
        return ApiResult.newResult(ServerCode.OPERATION_FAIL);
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    protected ApiResult<?> bizExceptionHandler(BizException e, HttpServletRequest request) {
        LOGGER.warn("controller error, request: {}, code：{}, msg：{}", request.getRequestURI(), e.getCode(), e.getMessage());
        return ApiResult.newResult(e.getCode(), e.getMessage(), e.getData());
    }
}