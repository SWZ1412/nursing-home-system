package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.AccidentRecord;
import com.nursinghome.service.AccidentService;
import com.nursinghome.service.ElderService;
import com.nursinghome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/accident")
public class AccidentController {

    @Autowired
    private AccidentService accidentService;

    @Autowired
    private ElderService elderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String accidentPage(Model model) {
        model.addAttribute("records", accidentService.findAll());
        model.addAttribute("elders", elderService.findAll());
        model.addAttribute("employees", userService.findAll());
        return "accident";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AccidentRecord> list = accidentService.findAll();
        PageInfo<AccidentRecord> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody AccidentRecord record, HttpSession session) {
        com.nursinghome.entity.User user = (com.nursinghome.entity.User) session.getAttribute("user");
        record.setReporterId(user.getId());
        if (accidentService.add(record)) {
            return Result.success("事故报告提交成功");
        }
        return Result.error("提交失败");
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody AccidentRecord record) {
        if (accidentService.update(record)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}