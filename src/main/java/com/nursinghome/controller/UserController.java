package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.User;
import com.nursinghome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String userPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody User user) {
        if (userService.add(user)) {
            return Result.success("添加成功，初始密码123456");
        }
        return Result.error("添加失败");
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody User user) {
        if (userService.update(user)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        if (userService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @PostMapping("/reset/{id}")
    @ResponseBody
    public Result<String> resetPassword(@PathVariable Integer id) {
        if (userService.resetPassword(id)) {
            return Result.success("密码已重置为123456");
        }
        return Result.error("重置失败");
    }
}