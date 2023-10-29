package com.jz.service;

import com.jz.pojo.dto.UserDTO;
import com.jz.pojo.dto.UserRolePermissionDTO;
import com.jz.pojo.vo.ResultMsg;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 22:04
 */
public interface UserService {

    ResultMsg login(UserDTO userDTO);

    ResultMsg saveUserRole(UserRolePermissionDTO userRolePermissionDTO,
                           HttpServletRequest request);

    ResultMsg saveUserPermission(UserRolePermissionDTO userRolePermissionDTO,
                                 HttpServletRequest request);

}
