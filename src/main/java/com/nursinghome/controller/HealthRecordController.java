package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.HealthRecord;
import com.nursinghome.service.ElderService;
import com.nursinghome.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/health")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @Autowired
    private ElderService elderService;

    @GetMapping
    public String healthPage(Model model) {
        model.addAttribute("records", healthRecordService.findAll());
        model.addAttribute("elders", elderService.findAll());
        return "health";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HealthRecord> list = healthRecordService.findAll();
        PageInfo<HealthRecord> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/elder/{elderId}")
    @ResponseBody
    public Result<List<HealthRecord>> getByElder(@PathVariable Integer elderId) {
        return Result.success(healthRecordService.findByElderId(elderId));
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody HealthRecord healthRecord, HttpSession session) {
        com.nursinghome.entity.User user = (com.nursinghome.entity.User) session.getAttribute("user");
        healthRecord.setNurseId(user.getId());
        if (healthRecordService.add(healthRecord)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        if (healthRecordService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}