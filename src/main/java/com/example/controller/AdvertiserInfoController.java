package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.AdvertiserInfo;
import com.example.service.AdvertiserInfoService;
import com.example.vo.AdvertiserInfoVo;
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
@RequestMapping(value = "/advertiserInfo")
public class AdvertiserInfoController {
    // 注入系统公告信息服务类
    @Autowired
    private AdvertiserInfoService advertiserInfoService;

    // 添加系统公告信息
    @PostMapping
    public Result<AdvertiserInfo> add(@RequestBody AdvertiserInfoVo advertiserInfo) {
        advertiserInfoService.add(advertiserInfo);
        return Result.success(advertiserInfo);
    }

    // 删除系统公告信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        advertiserInfoService.delete(id);
        return Result.success();
    }

    // 更新系统公告信息
    @PutMapping
    public Result update(@RequestBody AdvertiserInfoVo advertiserInfo) {
        advertiserInfoService.update(advertiserInfo);
        return Result.success();
    }

    // 获取系统公告详细信息
    @GetMapping("/{id}")
    public Result<AdvertiserInfo> detail(@PathVariable Long id) {
        AdvertiserInfo advertiserInfo = advertiserInfoService.findById(id);
        return Result.success(advertiserInfo);
    }

    // 获取所有系统公告信息
    @GetMapping
    public Result<List<AdvertiserInfoVo>> all() {
        return Result.success(advertiserInfoService.findAll());
    }

    // 分页查询系统公告信息
    @GetMapping("/page/{name}")
    public Result<PageInfo<AdvertiserInfoVo>> page(@PathVariable String name,
                                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                                   HttpServletRequest request) {
        return Result.success(advertiserInfoService.findPage(name, pageNum, pageSize, request));
    }

    // 批量通过 Excel 添加系统公告信息
    @PostMapping("/upload")
    public Result upload(@RequestPart MultipartFile file) throws IOException {
        // 读取 Excel 文件中的系统公告信息并处理空数据
        List<AdvertiserInfo> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(AdvertiserInfo.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            List<AdvertiserInfo> resultList = infoList.stream().filter(x ->!StrUtil.isBlank(x.getName())).collect(Collectors.toList());
            for (AdvertiserInfo info : resultList) {
                advertiserInfoService.add(info);
            }
        }
        return Result.success();
    }

    // 获取系统公告信息 Excel 模板文件
    @GetMapping("/getExcelModel")
    public void getExcelModel(HttpServletResponse response) throws IOException {
        // 创建模拟数据行
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("name", "系统公告");
        row.put("content", "这是系统公告，管理员可以在后台修改");
        row.put("time", "TIME");
        List<Map<String, Object>> list = Collections.singletonList(row);
        // 创建 Excel 写入器并写入数据
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        // 设置响应头和内容类型，输出 Excel 文件
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=advertiserInfoModel.xlsx");
        OutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
    }
}