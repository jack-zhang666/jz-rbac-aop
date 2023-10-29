package com.jz.controller;

import com.jz.annotation.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author jack zhang
 * @version 1.0
 * @description: TODO
 * @date 2023/10/2 10:31
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Permission(roles = {"user","admin","superAdmin"})
    @GetMapping("/testUser")
    public String testUser(){
        return "this is user role";
    }

    @Permission(roles = {"admin","superAdmin"})
    @GetMapping("/testAdmin")
    public String testAdmin(){
        return "this is admin role";
    }

    @Permission(roles = "superAdmin")
    @GetMapping("/testSuperAdmin")
    public String testSuperAdmin(){
        return "this is superAdmin role";
    }

    @Permission(permission = {"select","save","update","delete"})
    @GetMapping("/testSelect")
    public String testSelect(){
        return "this is select permission";
    }

    @Permission(permission = {"save","update","delete"})
    @GetMapping("/testUpdate")
    public String testUpdate(){
        return "this is update permission";
    }

    @Permission(permission = {"save","delete"})
    @GetMapping("/testSave")
    public String testSave(){
        return "this is save permission";
    }

    @Permission(permission = {"delete"})
    @GetMapping("/testDelete")
    public String testDelete(){
        return "this is delete permission";
    }

}
