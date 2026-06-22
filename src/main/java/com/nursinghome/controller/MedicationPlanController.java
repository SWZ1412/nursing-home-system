package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.MedicationPlan;
import com.nursinghome.service.ElderService;
import com.nursinghome.service.MedicationPlanService;
import com.nursinghome.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/medication-plan")
public class MedicationPlanController {

    @Autowired
    private MedicationPlanService medicationPlanService;

    @Autowired
    private ElderService elderService;

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public String planPage(Model model) {
        model.addAttribute("plans", medicationPlanService.findAll());
        model.addAttribute("elders", elderService.findAll());
        model.addAttribute("medicines", medicineService.findAll());
        return "medication-plan";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MedicationPlan> list = medicationPlanService.findAll();
        PageInfo<MedicationPlan> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/elder/{elderId}")
    @ResponseBody
    public Result<List<MedicationPlan>> getByElder(@PathVariable Integer elderId) {
        return Result.success(medicationPlanService.findByElderId(elderId));
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody MedicationPlan plan, HttpSession session) {
        com.nursinghome.entity.User user = (com.nursinghome.entity.User) session.getAttribute("user");
        plan.setDoctorId(user.getId());
        if (medicationPlanService.add(plan)) {
            return Result.success("用药计划添加成功");
        }
        return Result.error("添加失败");
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody MedicationPlan plan) {
        if (medicationPlanService.update(plan)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @PutMapping("/status/{id}")
    @ResponseBody
    public Result<String> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        if (medicationPlanService.updateStatus(id, status)) {
            return Result.success("状态更新成功");
        }
        return Result.error("状态更新失败");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        if (medicationPlanService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
