package cn.lchospital.baby.exception;

import cn.lchospital.baby.enumtype.ServerCode;

/**
 * @author n3verl4nd
 * @date 2020/3/25
 */
public class BizException extends RuntimeException {

    private int code;

    private String message;

    private Object data;

    public BizException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(ServerCode serverCode) {
        super(serverCode.getMessage());
        this.code = serverCode.getCode();
        this.message = serverCode.getMessage();
    }

    public BizException(ServerCode serverCode, Object data) {
        super(serverCode.getMessage());
        this.code = serverCode.getCode();
        this.message = serverCode.getMessage();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}