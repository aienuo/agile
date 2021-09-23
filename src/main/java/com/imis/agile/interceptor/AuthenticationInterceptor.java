package com.imis.agile.interceptor;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.enums.CommonResponseEnum;
import com.imis.agile.module.system.model.entity.Menu;
import com.imis.agile.module.system.model.entity.RoleMenu;
import com.imis.agile.module.system.model.entity.User;
import com.imis.agile.module.system.model.entity.UserRole;
import com.imis.agile.module.system.service.IMenuService;
import com.imis.agile.module.system.service.IRoleMenuService;
import com.imis.agile.module.system.service.IUserRoleService;
import com.imis.agile.module.system.service.IUserService;
import com.imis.agile.util.AgileUtil;
import com.imis.agile.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * AuthorityInterceptor<br>
 * 权限拦截器<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 系统用户 服务类
     */
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 菜单权限 服务类
     */
    private IMenuService menuService;

    @Autowired
    public void setMenuService(IMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 用户角色关联 服务类
     */
    private IUserRoleService userRoleService;

    @Autowired
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 角色菜单权限关联 服务类
     */
    private IRoleMenuService roleMenuService;

    @Autowired
    public void setRoleMenuService(IRoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    /**
     * 根据Token 获取用户信息
     *
     * @param token - Token
     * @return User - 系统用户
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    private User getUserByToken(final String token) {
        CommonResponseEnum.TOKEN_500.assertNotEmpty(token);
        CommonResponseEnum.TOKEN_500.assertIsFalse(JwtUtil.isDue(token));
        // 获取 Token 中的 username
        String username = JwtUtil.getUsername(token);
        CommonResponseEnum.TOKEN_500.assertNotEmpty(username);
        // 判断用户状态
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username), Boolean.FALSE);
        CommonResponseEnum.ERROR_500.assertNotNullWithMsg(user, "用户不存在");
        // 删除状态（0-正常，1-已删除）
        CommonResponseEnum.ERROR_500.assertIsTrueWithMsg(CommonConstant.DEL_FLAG_0.equals(user.getDelFlag()), "账号注销");
        // 冻结状态(0-正常，1-冻结）
        CommonResponseEnum.ERROR_500.assertIsTrueWithMsg(CommonConstant.USER_FREEZE_0.equals(user.getStatus()), "账号冻结");
        return user;
    }

    /**
     * 校验 Token 是否合法
     *
     * @param request - HttpServletRequest
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    private User verificationToken(HttpServletRequest request) {
        String uri = request.getRequestURI();
        Boolean aBoolean = Boolean.TRUE;
        // 获取 Token
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (AgileUtil.isEmpty(token)) {
            token = JwtUtil.getCookieValue(CommonConstant.X_COOKIE_NAME);
            aBoolean = Boolean.FALSE;
        }
        // 校验
        if (aBoolean) {
            // TODO: 基于 其他方式 进行 Token 校验
        }
        User user = getUserByToken(token);
        log.debug("URL：{}，username：{}，OldToken：{}", uri, user.getUsername(), token);
        return user;
    }

    /**
     * 校验 URL权限 是否合法
     *
     * @param request - HttpServletRequest
     * @param user    - User
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    private void verificationPermissions(HttpServletRequest request, final User user) {
        String uri = request.getRequestURI();
        Menu menu = this.menuService.getOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getUrl, uri), Boolean.FALSE);
        if (AgileUtil.isNotEmpty(menu)) {
            // 用户角色关联
            List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
            CommonResponseEnum.PERMISSIONS_500.assertNotEmpty(userRoleList);
            // 角色编号
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            CommonResponseEnum.PERMISSIONS_500.assertNotEmpty(roleIdList);
            // 角色菜单权限关联
            List<RoleMenu> roleMenuList = this.roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIdList).eq(RoleMenu::getMenuId, menu.getId()));
            CommonResponseEnum.PERMISSIONS_500.assertNotEmpty(roleMenuList);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 校验 Token 是否合法
        User user = verificationToken(request);
        // if (!"admin".equals(user.getUsername())) {
            // 校验 URL权限 是否合法
            verificationPermissions(request, user);
        // }
        return Boolean.TRUE;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        String uri = request.getRequestURI();
        // 获取 Token
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        Boolean aBoolean = Boolean.TRUE;
        if (AgileUtil.isEmpty(token)) {
            // Cookie 方式
            token = JwtUtil.getCookieValue(CommonConstant.X_COOKIE_NAME);
            aBoolean = Boolean.FALSE;
        }
        // 校验 Token
        User user = getUserByToken(token);
        token = JwtUtil.sign(user.getUsername(), user.getPassword());
        // 刷新 Token
        if (aBoolean) {
            // TODO: 基于 其他方式 进行 Token 刷新
        } else {
            // 往 Cookie 中 设置 新 Token， 从而达到刷新 Token 过期时间 的效果
            JwtUtil.updateCookieValue(CommonConstant.X_COOKIE_NAME, token);
        }
        log.debug("URL：{}，username：{}，NewToken：{}", uri, user.getUsername(), token);
    }

}
