package com.jz.pojo.dto;

import lombok.Data;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/17 20:45
 */
@Data
public class TestDTO {

    private Integer userId;

    private String userName;

    private String userPass;

    private String userPhone;

    private String userMail;

    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private Integer permissionId;

    private String permissionName;

    private String permissionDesc;
}
