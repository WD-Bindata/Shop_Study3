package com.wangd.controller;

import com.wangd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wangd
 */
@Controller
@RequestMapping("/api/private/v1")
public class UserController {
    @Autowired
    private UserService userService;
}
