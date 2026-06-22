package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.VisitorRecord;
import com.nursinghome.service.ElderService;
import com.nursinghome.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private ElderService elderService;

    @GetMapping
    public String visitorPage(Model model) {
        model.addAttribute("records", visitorService.findAll());
        model.addAttribute("elders", elderService.findAll());
        return "visitor";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<VisitorRecord> list = visitorService.findAll();
        PageInfo<VisitorRecord> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody VisitorRecord record, HttpSession session) {
        com.nursinghome.entity.User user = (com.nursinghome.entity.User) session.getAttribute("user");
        record.setOperatorId(user.getId());
        if (visitorService.add(record)) {
            return Result.success("访客登记成功");
        }
        return Result.error("登记失败");
    }

    @PutMapping("/leave/{id}")
    @ResponseBody
    public Result<String> leave(@PathVariable Integer id) {
        if (visitorService.leave(id, new Date())) {
            return Result.success("离开登记成功");
        }
        return Result.error("登记失败");
    }
}