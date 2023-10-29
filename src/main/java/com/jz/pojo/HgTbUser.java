package com.jz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (HgTbUser)实体类
 *
 * @author makejava
 * @since 2023-10-01 21:31:33
 */
@Data
@TableName(value = "user",keepGlobalPrefix = true)
public class HgTbUser implements Serializable {
    private static final long serialVersionUID = 777256986466274406L;

    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_pass")
    private String userPass;

    @TableField(value = "user_phone")
    private String userPhone;

    @TableField(value = "user_mail")
    private String userMail;

}

