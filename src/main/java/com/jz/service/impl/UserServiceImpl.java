package com.jz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jz.mapper.*;
import com.jz.pojo.*;
import com.jz.pojo.constant.RedisKey;
import com.jz.pojo.dto.UserDTO;
import com.jz.pojo.dto.UserRolePermissionDTO;
import com.jz.pojo.vo.ResultMsg;
import com.jz.service.UserService;
import com.jz.util.GetUserUtil;
import com.jz.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 22:05
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Resource
    PermissionMapper permissionMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GetUserUtil getUserUtil;

    @Override
    public ResultMsg login(UserDTO userDTO) {
        LambdaQueryWrapper<HgTbUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HgTbUser::getUserName, userDTO.getUserName());

        HgTbUser hgTbUser = userMapper.selectOne(wrapper);
        Map<String,String> map = new HashMap<>();
        if (hgTbUser != null && hgTbUser.getUserPass().equals(userDTO.getUserPass())){
            map.put("userId",hgTbUser.getUserId().toString());
            String token = JwtUtil.getToken(map);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            stringRedisTemplate.opsForValue().set(RedisKey.LOGIN_KEY + uuid, token);

            List<String> roles = new ArrayList<>();
            List<String> permissions = new ArrayList<>();
            List<HgTbPermission> permissionList = new ArrayList<>();
            List<HgTbRole> userRole = userMapper.getUserRole(hgTbUser.getUserId());
            userRole.stream().forEach(role -> {
                List<HgTbPermission> userPermission = userMapper.getUserPermission(role.getRoleId());
                permissionList.addAll(userPermission);
                roles.add(role.getRoleName());
            });
            permissionList.stream().forEach(permission -> {
                permissions.add(permission.getPermissionName());
            });

            redisTemplate.opsForList().rightPushAll(RedisKey.USER_ROLE_LIST + hgTbUser.getUserId(),roles);
            redisTemplate.opsForList().rightPushAll(RedisKey.USER_PERMISSION_LIST + hgTbUser.getUserId(),permissions);
            return ResultMsg.SUCCESS("登录成功",uuid,null);
        }
        return ResultMsg.FAIL("登录失败");
    }

    @Override
    public ResultMsg saveUserRole(UserRolePermissionDTO userRolePermissionDTO,
                                  HttpServletRequest request) {
        Integer userId = getUserUtil.getUserId(request);
        Boolean aBoolean = false;
        Boolean aBoolean1 = false;

        aBoolean = redisTemplate.delete(RedisKey.USER_ROLE_LIST + userId);
        aBoolean1 = redisTemplate.delete(RedisKey.USER_PERMISSION_LIST + userId);

        if (aBoolean && aBoolean1){
            HgTbRole hgTbRole = new HgTbRole();
            BeanUtils.copyProperties(userRolePermissionDTO,hgTbRole);
            int insert = roleMapper.insert(hgTbRole);
            if (insert <= 0){
                return ResultMsg.FAIL("角色赋予失败");
            }
            HgTbUserRole hgTbUserRole = new HgTbUserRole();
            hgTbUserRole.setUserId(userId);
            hgTbUserRole.setRoleId(hgTbRole.getRoleId());
            int insert1 = userRoleMapper.insert(hgTbUserRole);
            if (insert1 <= 0){
                return ResultMsg.FAIL("角色赋予失败");
            }
            aBoolean = redisTemplate.delete(RedisKey.USER_ROLE_LIST + userId);
            aBoolean1 = redisTemplate.delete(RedisKey.USER_PERMISSION_LIST + userId);
            if (aBoolean && aBoolean1){
                List<String> roles = new ArrayList<>();
                List<String> permissions = new ArrayList<>();
                List<HgTbPermission> permissionList = new ArrayList<>();
                List<HgTbRole> userRole = userMapper.getUserRole(userId);
                userRole.stream().forEach(role -> {
                    List<HgTbPermission> userPermission = userMapper.getUserPermission(role.getRoleId());
                    permissionList.addAll(userPermission);
                    roles.add(role.getRoleName());
                });
                permissionList.stream().forEach(permission -> {
                    permissions.add(permission.getPermissionName());
                });

                redisTemplate.opsForList().rightPushAll(RedisKey.USER_ROLE_LIST + userId,roles);
                redisTemplate.opsForList().rightPushAll(RedisKey.USER_PERMISSION_LIST + userId,permissions);
                return ResultMsg.SUCCESS("角色赋予成功",null,null);
            }
        }
        return ResultMsg.FAIL("角色赋予失败");
    }

    @Override
    public ResultMsg saveUserPermission(UserRolePermissionDTO userRolePermissionDTO,
                                        HttpServletRequest request) {
        Integer userId = getUserUtil.getUserId(request);
        Boolean aBoolean = false;
        Boolean aBoolean1 = false;

        aBoolean = redisTemplate.delete(RedisKey.USER_ROLE_LIST + userId);
        aBoolean1 = redisTemplate.delete(RedisKey.USER_PERMISSION_LIST + userId);

        if (aBoolean && aBoolean1){
            HgTbPermission hgTbPermission = new HgTbPermission();
            BeanUtils.copyProperties(userRolePermissionDTO,hgTbPermission);
            int insert = permissionMapper.insert(hgTbPermission);
            if (insert <= 0){
                return ResultMsg.FAIL("权限赋予失败");
            }

            aBoolean = redisTemplate.delete(RedisKey.USER_ROLE_LIST + userId);
            aBoolean1 = redisTemplate.delete(RedisKey.USER_PERMISSION_LIST + userId);
            if (aBoolean && aBoolean1){
                List<String> roles = new ArrayList<>();
                List<String> permissions = new ArrayList<>();
                List<HgTbPermission> permissionList = new ArrayList<>();

                List<HgTbRole> userRole = userMapper.getUserRole(userId);
                userRole.stream().forEach(role -> {
                    List<HgTbPermission> userPermission = userMapper.getUserPermission(role.getRoleId());
                    permissionList.addAll(userPermission);
                    roles.add(role.getRoleName());
                    HgTbRolePermission hgTbRolePermission = new HgTbRolePermission();
                    hgTbRolePermission.setPermissionId(hgTbPermission.getPermissionId());
                    hgTbRolePermission.setRoleId(role.getRoleId());
                    rolePermissionMapper.insert(hgTbRolePermission);
                });

                permissionList.stream().forEach(perm -> {
                    permissions.add(perm.getPermissionName());
                });

                redisTemplate.opsForList().rightPushAll(RedisKey.USER_ROLE_LIST + userId,roles);
                redisTemplate.opsForList().rightPushAll(RedisKey.USER_PERMISSION_LIST + userId, permissions);
                return ResultMsg.SUCCESS("权限赋予成功",null,null);
            }
        }

        return ResultMsg.FAIL("权限赋予失败");
    }

}
