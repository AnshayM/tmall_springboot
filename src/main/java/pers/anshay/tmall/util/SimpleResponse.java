package pers.anshay.tmall.util;

/**
 * 返回数据的包装类
 *
 * @author: Anshay
 * @date: 2018/12/3
 */
public class SimpleResponse {
    public static final boolean SUCCESS = true;
    public static final boolean FAIL = false;

    /**
     * 返回状态 True:SUCCESS，False:FAIL
     */
    private boolean status;

    /**
     * 提示性文字
     */
    private String message;

    /**
     * 内容
     */
    private Object content;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
}
