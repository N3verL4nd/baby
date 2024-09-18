package cn.lchospital.baby.enumtype;

/**
 * @author n3verl4nd
 * @date 2020/3/24
 */
public enum ServerCode {

    SUCCESS(0, "操作成功"),

    PARAM_ERR(1, "参数错误"),

    AUTH_ERR(2, "权限错误"),

    NO_LOGIN(3, "未登录"),

    NO_DATA(4, "找不到该数据"),


    /* ******************** 自定义状态码 ******************** */



    /* ******************** 自定义状态码 ******************** */


    // 兜底错误
    OPERATION_FAIL(9000, "服务器繁忙"),
    // 不用划分错误码的错误 须自己填写错误信息(如调用第三方服务尽量使用其提供的错误信息)
    CUSTOM_ERR(9999, "自定义错误");


    private int code;

    private String message;

    ServerCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ServerCode custom(String message) {
        ServerCode custom = CUSTOM_ERR;
        custom.setMessage(message);
        return CUSTOM_ERR;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}