package com.nursinghome.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nursinghome.common.Result;
import com.nursinghome.entity.Bill;
import com.nursinghome.entity.Payment;
import com.nursinghome.service.BillService;
import com.nursinghome.service.ElderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private ElderService elderService;

    @GetMapping
    public String billPage(Model model) {
        model.addAttribute("bills", billService.findAll());
        model.addAttribute("elders", elderService.findAll());
        model.addAttribute("overdueBills", billService.findOverdueBills());
        return "bill";
    }

    @GetMapping("/list")
    @ResponseBody
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Bill> list = billService.findAll();
        PageInfo<Bill> pageInfo = new PageInfo<>(list);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageNum);
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @PostMapping("/generate")
    @ResponseBody
    public Result<String> generateBill(@RequestBody Bill bill) {
        if (billService.generateBill(bill)) {
            return Result.success("账单生成成功");
        }
        return Result.error("生成失败");
    }

    @PostMapping("/pay")
    @ResponseBody
    public Result<String> payBill(@RequestBody Payment payment, HttpSession session) {
        com.nursinghome.entity.User user = (com.nursinghome.entity.User) session.getAttribute("user");
        payment.setOperatorId(user.getId());
        try {
            if (billService.payBill(payment)) {
                return Result.success("缴费成功");
            }
            return Result.error("缴费失败");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<String> deleteBill(@PathVariable Integer id) {
        if (billService.deleteBill(id)) {
            return Result.success("账单已删除");
        }
        return Result.error("删除失败");
    }
}