package com.jz;

import com.jz.mapper.UserMapper;
import com.jz.pojo.HgTbRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HouguRbacAopApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelectUserRole(){
        Integer userId = 1;
        List<HgTbRole> userRole = userMapper.getUserRole(userId);
        userRole.forEach(System.out::println);
    }

    @Test
    public void testSelectUserRolePermission(){
        Integer userId = 1;
        userMapper.getUserRolePermission(userId).forEach(System.out::println);
    }

}
