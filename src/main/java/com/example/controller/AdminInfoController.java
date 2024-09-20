package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.AdminInfo;
import com.example.exception.CustomException;
import com.example.service.AdminInfoService;
import com.example.vo.AdminInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/adminInfo")
public class AdminInfoController {
    // 注入管理员信息服务类
    @Autowired
    private AdminInfoService adminInfoService;

    // 添加管理员信息
    @PostMapping
    public Result<AdminInfo> add(@RequestBody AdminInfoVo adminInfo) {
        adminInfoService.add(adminInfo);
        return Result.success(adminInfo);
    }

    // 删除管理员信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        adminInfoService.delete(id);
        return Result.success();
    }

    // 更新管理员信息
    @PutMapping
    public Result update(@RequestBody AdminInfoVo adminInfo) {
        adminInfoService.update(adminInfo);
        return Result.success();
    }

    // 获取管理员详细信息
    @GetMapping("/{id}")
    public Result<AdminInfo> detail(@PathVariable Long id) {
        AdminInfo adminInfo = adminInfoService.findById(id);
        return Result.success(adminInfo);
    }

    // 获取所有管理员信息
    @GetMapping
    public Result<List<AdminInfoVo>> all() {
        return Result.success(adminInfoService.findAll());
    }

    // 分页查询管理员信息
    @GetMapping("/page/{name}")
    public Result<PageInfo<AdminInfoVo>> page(@PathVariable String name,
                                              @RequestParam(defaultValue = "1") Integer pageNum,
                                              @RequestParam(defaultValue = "5") Integer pageSize,
                                              HttpServletRequest request) {
        return Result.success(adminInfoService.findPage(name, pageNum, pageSize, request));
    }

    // 管理员注册
    @PostMapping("/register")
    public Result<AdminInfo> register(@RequestBody AdminInfo adminInfo) {
        // 校验参数
        if (StrUtil.isBlank(adminInfo.getName()) || StrUtil.isBlank(adminInfo.getPassword())) {
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        return Result.success(adminInfoService.add(adminInfo));
    }

    // 批量通过 Excel 添加管理员信息
    @PostMapping("/upload")
    public Result upload(@RequestPart MultipartFile file) throws IOException {
        // 读取 Excel 文件中的管理员信息并处理
        List<AdminInfo> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(AdminInfo.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            List<AdminInfo> resultList = infoList.stream().filter(x ->!StrUtil.isBlank(x.getName())).collect(Collectors.toList());
            for (AdminInfo info : resultList) {
                adminInfoService.add(info);
            }
        }
        return Result.success();
    }

    // 获取 Excel 模板
    @GetMapping("/getExcelModel")
    public void getExcelModel(HttpServletResponse response) throws IOException {
        // 创建模拟数据行
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("name", "admin");
        row.put("password", "123456");
        row.put("nickName", "管理员");
        row.put("sex", "男");
        row.put("age", 22);
        row.put("birthday", "TIME");
        row.put("phone", "18843232356");
        row.put("address", "上海市");
        row.put("code", "111");
        row.put("email", "aa@163.com");
        row.put("cardId", "342425199001116372");
        row.put("level", 1);
        List<Map<String, Object>> list = Collections.singletonList(row);
        // 创建 Excel 写入器并写入数据
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        // 设置响应头和内容类型，输出 Excel 文件
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=adminInfoModel.xlsx");
        OutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
    }
}