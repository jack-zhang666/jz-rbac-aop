package com.jz.config;

import cn.hutool.json.JSONUtil;
import com.jz.annotation.Permission;
import com.jz.exception.NoPermissionException;
import com.jz.exception.NoRoleException;
import com.jz.pojo.constant.RedisKey;
import com.jz.pojo.vo.ResultMsg;
import com.jz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 21:43
 */
@Aspect
@Component
@Slf4j
public class PermissionAopConfig {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut(value = "@annotation(com.jz.annotation.Permission)")
    public void pt1(){};

    @Before(value = "pt1()")
    public void permission(JoinPoint joinPoint){

        String uuid = request.getHeader("token");

        Boolean aBoolean = stringRedisTemplate.hasKey(RedisKey.LOGIN_KEY + uuid);
        if (!aBoolean){
            sendMessage(ResultMsg.FAIL("请先登录"));
            log.info("用户尚未登录");
            throw new NoRoleException("用户尚未登录");
        }

        String token = stringRedisTemplate.opsForValue().get(RedisKey.LOGIN_KEY + uuid);
        String userId = JwtUtil.getTokenInfo(token).getClaim("userId").asString();
        if (userId == null || userId.equals("")){
            sendMessage(ResultMsg.FAIL("token不合法"));
            log.info("token校验未通过");
            throw new NoRoleException("token校验未通过");
        }

        List<String> roleList = redisTemplate.opsForList().range(RedisKey.USER_ROLE_LIST + userId, 0, -1);
        List<String> permissionList = redisTemplate.opsForList().range(RedisKey.USER_PERMISSION_LIST + userId, 0, -1);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(Permission.class)){
            Permission permission = method.getAnnotation(Permission.class);
            List<String> roles = Arrays.asList(permission.roles());
            List<String> permissions = Arrays.asList(permission.permission());
            String r = roles.get(0);
            String p = permissions.get(0);
            if (r.equals("")){
                if (permissions.size() >= 1){
                    if (!permissionList.containsAll(permissions)){
                        sendMessage(ResultMsg.FAIL("对不起，您尚未拥有访问该接口的权限"));
                        log.info("用户权限不足");
                        throw new NoPermissionException("用户权限不足");
                    }
                }
            }else if (p.equals("")){
                if (roles.size() >= 1){
                    if (!roles.containsAll(roleList)){
                        sendMessage(ResultMsg.FAIL("对不起，您尚未拥有访问该接口的角色"));
                        log.info("用户不具备角色");
                        throw new NoRoleException("用户不具备角色");
                    }
                }
            }else if (!r.equals("") && !p.equals("")){
                if (roles.size() >= 1 && permissions.size() >= 1){
                    if (!permissionList.containsAll(permissions)){
                        sendMessage(ResultMsg.FAIL("对不起，您尚未拥有访问该接口的权限"));
                        log.info("用户权限不足");
                        throw new NoPermissionException("用户权限不足");
                    }
                    if (!roles.containsAll(roleList)){
                        sendMessage(ResultMsg.FAIL("对不起，您尚未拥有访问该接口的角色"));
                        log.info("用户不具备角色");
                        throw new NoRoleException("用户不具备角色");
                    }
                }
            }
        }
    }

    public void sendMessage(ResultMsg resultMsg){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(resultMsg));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.flush();
            writer.close();
        }
    }

}
