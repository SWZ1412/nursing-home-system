package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.Elder;
import com.nursinghome.service.ElderService;
import com.nursinghome.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/elder")
public class ElderController {

    private static final Logger log = LoggerFactory.getLogger(ElderController.class);

    @Autowired
    private ElderService elderService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String elderPage(Model model) {
        model.addAttribute("elders", elderService.findAll());
        return "elder";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Elder> list = elderService.findAll();
        PageInfo<Elder> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("rooms", roomService.findAvailableRooms());
        return "elder-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody Elder elder) {
        try {
            log.info("收到添加老人请求: name={}, gender={}, birthday={}, roomId={}",
                    elder.getName(), elder.getGender(), elder.getBirthday(), elder.getRoomId());
            if (elderService.add(elder)) {
                return Result.success("添加成功");
            }
            return Result.error("添加失败");
        } catch (Exception e) {
            log.error("添加老人失败: {}", e.getMessage(), e);
            return Result.error("添加失败: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result<Elder> getById(@PathVariable Integer id) {
        Elder elder = elderService.findById(id);
        if (elder != null) {
            return Result.success(elder);
        }
        return Result.error("老人不存在");
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("elder", elderService.findById(id));
        model.addAttribute("rooms", roomService.findAll());
        return "elder-edit";
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody Elder elder) {
        if (elderService.update(elder)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        if (elderService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}