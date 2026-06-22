package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.Medicine;
import com.nursinghome.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public String medicinePage(Model model) {
        model.addAttribute("medicines", medicineService.findAll());
        model.addAttribute("lowStock", medicineService.findLowStock());
        return "medicine";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Medicine> list = medicineService.findAll();
        PageInfo<Medicine> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/lowstock")
    @ResponseBody
    public Result<List<Medicine>> lowStock() {
        return Result.success(medicineService.findLowStock());
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody Medicine medicine) {
        if (medicineService.add(medicine)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody Medicine medicine) {
        if (medicineService.update(medicine)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        if (medicineService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}