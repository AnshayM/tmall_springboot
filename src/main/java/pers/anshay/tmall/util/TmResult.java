package pers.anshay.tmall.util;

/**
 * 自定义返回对象封装类(待重构——全部使用Result代替)
 *
 * @author: Anshay
 * @date: 2018/12/3
 */
public class TmResult {

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

    public TmResult() {
    }

    public TmResult(boolean success, String message, Object content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }

    public TmResult(boolean success) {
        this.success = success;
    }

    public TmResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
