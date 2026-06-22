package com.nursinghome.controller;

import com.nursinghome.common.Result;
import com.nursinghome.entity.User;
import com.nursinghome.service.ElderService;
import com.nursinghome.service.MedicineService;
import com.nursinghome.service.RoomService;
import com.nursinghome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ElderService elderService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session) {
        User user = userService.login(username, password);
        if (user != null && user.getStatus() == 1) {
            // 登录成功，将用户信息存入session
            session.setAttribute("user", user);
            return Result.success(user);
        } else if (user == null) {
            return Result.error("用户名或密码错误");
        } else {
            return Result.error("账号已被禁用，请联系管理员");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String index(Model model) {
        int activeElderCount = elderService.countActive();
        int availableRoomCount = roomService.countAvailable();
        int totalCapacity = roomService.sumCapacity();
        int medicineCount = medicineService.findAll().size();

        // 计算入住率 = 在住老人数 / 总床位数
        String occupancyRate = totalCapacity > 0
                ? new DecimalFormat("#%").format((double) activeElderCount / totalCapacity)
                : "0%";

        model.addAttribute("activeElderCount", activeElderCount);
        model.addAttribute("availableRoomCount", availableRoomCount);
        model.addAttribute("medicineCount", medicineCount);
        model.addAttribute("occupancyRate", occupancyRate);

        return "index";
    }
}