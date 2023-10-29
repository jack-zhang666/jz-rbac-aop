package com.jz.controller;

import com.jz.annotation.Permission;
import com.jz.pojo.dto.UserDTO;
import com.jz.pojo.dto.UserRolePermissionDTO;
import com.jz.pojo.vo.ResultMsg;
import com.jz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 22:06
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResultMsg login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO);
    }

    @PostMapping("/saveUserRole")
    @Permission(roles = {"admin","superAdmin"})
    public ResultMsg saveUserRole(@RequestBody UserRolePermissionDTO userRolePermissionDTO,
                                  HttpServletRequest request){
        return userService.saveUserRole(userRolePermissionDTO,request);
    }

    @PostMapping("/saveRolePermission")
    @Permission(permission = {"save","delete"})
    public ResultMsg saveRolePermission(@RequestBody UserRolePermissionDTO userRolePermissionDTO,
                                        HttpServletRequest request){
        return userService.saveUserPermission(userRolePermissionDTO,request);
    }
}
