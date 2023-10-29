package com.jz.util;

import com.jz.pojo.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/2 0:01
 */
@Component
public class GetUserUtil {

    private Integer userId;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public Integer getUserId(HttpServletRequest request){
        String uuid = request.getHeader("token");
        if (uuid != null){
            Boolean aBoolean = stringRedisTemplate.hasKey(RedisKey.LOGIN_KEY + uuid);
            if (aBoolean){
                String token = stringRedisTemplate.opsForValue().get(RedisKey.LOGIN_KEY + uuid);
                String s = JwtUtil.getTokenInfo(token).getClaim("userId").asString();
                Integer userId = Integer.parseInt(s);
                return userId;
            }
        }
        return null;
    }
}
