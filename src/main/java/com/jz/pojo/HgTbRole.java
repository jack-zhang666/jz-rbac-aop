package com.jz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (HgTbRole)实体类
 *
 * @author makejava
 * @since 2023-10-01 21:31:33
 */
@Data
@TableName(value = "role", keepGlobalPrefix = true)
public class HgTbRole implements Serializable {
    private static final long serialVersionUID = -94214493978776704L;

    @TableId(value = "role_id",type = IdType.AUTO)
    private Integer roleId;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "role_desc")
    private String roleDesc;

}

