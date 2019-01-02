package pers.anshay.tmall.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.anshay.tmall.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 *
 * @author: Anshay
 * @date: 2018/12/29
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String contextPath = session.getServletContext().getContextPath();
        // 需要登录才可以访问的路径
        String[] requireAuthPages = new String[]{
                "buy",
                "alipay",
                "payed",
                "cart",
                "bought",
                "confirmPay",
                "orderConfirmed",

                "foreBuyOne",
                "foreBuy",
                "foreAddCart",
                "foreCart",
                "foreChangeOrderItem",
                "foreDeleteOrderItem",
                "foreCreateOrder",
                "forePayed",
                "foreBought",
                "foreConfirmPay",
                "foreOrderConfirmed",
                "foreDeleteOrder",
                "foreReview",
                "foreDoReview"
        };

        String uri = httpServletRequest.getRequestURI();
        uri = StringUtils.remove(uri, contextPath + "/");
        String page = uri;
        if (beginWith(page, requireAuthPages)) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                //重定向到登录界面
                httpServletResponse.sendRedirect("login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    /**
     * 判断字符串数组是否以指定字符串开头(待重构到util)
     *
     * @param string  指定字符串
     * @param strings 字符串数组
     * @return Boolean 是/否
     */
    private boolean beginWith(String string, String[] strings) {
        for (String str : strings) {
            if (StringUtils.startsWith(string, str)) {
                return true;
            }
        }
        return false;
    }

}
