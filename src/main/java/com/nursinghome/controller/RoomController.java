package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.Room;
import com.nursinghome.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String roomPage(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "room";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Room> list = roomService.findAll();
        PageInfo<Room> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/add")
    public String addPage() {
        return "room-add";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        model.addAttribute("room", roomService.findById(id));
        return "room-edit";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody Room room) {
        if (roomService.add(room)) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody Room room) {
        if (roomService.update(room)) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        if (roomService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}