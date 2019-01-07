package pers.anshay.tmall.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录状态拦截器
 * 在涉及到已登录状态的界面时检查用户是否登录。已登录则访问目标页面，未登录则跳转到登录界面。
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

//        待重构，登录完成后返回原访问界面
        if (beginWith(page, requireAuthPages)) {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                httpServletResponse.sendRedirect("login");
                return false;
            } else {
                //   先确定不产生bug，再优化业务逻辑。看看shiro文档找找过滤的解决办法
                //   isAuthenticated()是true但是没有用户信息
                Object user = subject.getSession().getAttribute("user");
                if (null == user) {
                    httpServletResponse.sendRedirect("login");
                    return false;
                }
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
