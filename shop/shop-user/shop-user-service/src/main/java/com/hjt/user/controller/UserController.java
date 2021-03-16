package com.hjt.user.controller;

import com.hjt.user.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/14 23:12
 */
@Controller
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUser(@PathVariable("data")String data,@PathVariable("type")Integer type) {
        Boolean bool = userService.checkUser(data,type);
        if (bool!=null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bool);

    }

    @PostMapping("code")
    public ResponseEntity<Boolean> sentVerifyCode(@RequestParam("phone")String phone) {
        userService.sentVerifyCode(phone);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
