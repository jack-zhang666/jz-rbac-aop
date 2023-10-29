package com.jz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jz.pojo.HgTbPermission;
import com.jz.pojo.HgTbRole;
import com.jz.pojo.HgTbUser;
import com.jz.pojo.dto.TestDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 21:36
 */
public interface UserMapper extends BaseMapper<HgTbUser> {

    @Select("select r.* from ${prefix}user_role ur, ${prefix}role r where ur.role_id = r.role_id and ur.user_id = #{userId}")
    List<HgTbRole> getUserRole(Integer userId);

    List<HgTbPermission> getUserPermission(Integer roleId);

    @Select("select distinct * " +
            "from " +
            "hg_tb_user u, " +
            "hg_tb_role r, " +
            "hg_tb_permission p, " +
            "hg_tb_user_role ur, " +
            "hg_tb_role_permission rp " +
            "where " +
            "u.user_id = ur.user_id " +
            "and r.role_id = rp.role_id " +
            "and u.user_id = #{user_id};")
    List<TestDTO> getUserRolePermission(Integer userId);
}
