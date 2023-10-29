package com.jz.pojo.dto;

import lombok.Data;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/2 17:35
 */
@Data
public class UserRolePermissionDTO {

    private Integer userId;

    private Integer roleId;

    private Integer permissionId;

    private String roleName;

    private String roleDesc;

    private String permissionName;

    private String permissionDesc;
}
