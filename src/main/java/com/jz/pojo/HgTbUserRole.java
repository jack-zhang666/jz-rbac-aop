package com.jz.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * (HgTbUserRole)实体类
 *
 * @author makejava
 * @since 2023-10-01 21:31:33
 */
@Data
public class HgTbUserRole implements Serializable {
    private static final long serialVersionUID = -57092728352527730L;

    private Integer userId;
    
    private Integer roleId;

}

