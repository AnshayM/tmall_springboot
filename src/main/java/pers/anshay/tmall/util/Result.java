package pers.anshay.tmall.util;

/**
 * 自定义返回对象封装类
 *
 * @author: Anshay
 * @date: 2018/12/3
 */
public class Result {

    /**
     * 返回状态
     */
    private boolean success;

    /**
     * 提示性文字
     */
    private String message;

    /**
     * 内容
     */
    private Object content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Result() {
    }

    public Result(boolean success, String message, Object content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }
}
