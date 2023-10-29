package com.jz.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * (HgTbRolePermission)实体类
 *
 * @author makejava
 * @since 2023-10-01 21:31:33
 */
@Data
public class HgTbRolePermission implements Serializable {
    private static final long serialVersionUID = -40051111765886043L;
    
    private Integer roleId;
    
    private Integer permissionId;

}

