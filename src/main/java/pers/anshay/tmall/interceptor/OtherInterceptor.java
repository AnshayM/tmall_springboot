package pers.anshay.tmall.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.anshay.tmall.pojo.Category;
import pers.anshay.tmall.pojo.OrderItem;
import pers.anshay.tmall.pojo.User;
import pers.anshay.tmall.service.ICategoryService;
import pers.anshay.tmall.service.IOrderItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 其他拦截器
 *
 * @author: Anshay
 * @date: 2019/1/2
 */
public class OtherInterceptor implements HandlerInterceptor {
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IOrderItemService orderItemService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if (user != null) {
            List<OrderItem> orderItems = orderItemService.listByUser(user);
            for (OrderItem orderItem : orderItems) {
                cartTotalItemNumber += orderItem.getNumber();
            }
        }

        List<Category> categories = categoryService.list();
        String contextPath = httpServletRequest.getServletContext().getContextPath();

        httpServletRequest.getServletContext().setAttribute("categories_below_search", categories);
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        httpServletRequest.getServletContext().setAttribute("contextPath", contextPath);

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }
}
