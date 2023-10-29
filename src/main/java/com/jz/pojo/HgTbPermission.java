package com.jz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (HgTbPermission)实体类
 *
 * @author makejava
 * @since 2023-10-01 21:31:31
 */
@Data
@TableName(value = "permission", keepGlobalPrefix = true)
public class HgTbPermission implements Serializable {
    private static final long serialVersionUID = -63245061773970910L;

    @TableId(value = "permission_id",type = IdType.AUTO)
    private Integer permissionId;

    @TableField(value = "permission_name")
    private String permissionName;

    @TableField(value = "permission_desc")
    private String permissionDesc;

}

