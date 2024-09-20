package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.CollectInfo;
import com.example.service.CollectInfoService;
import com.example.vo.CollectInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/collectInfo")
public class CollectInfoController {
    // 注入收藏信息服务类
    @Resource
    private CollectInfoService collectInfoService;

    // 添加收藏信息，传入请求对象用于获取相关信息
    @PostMapping
    public Result<CollectInfo> add(@RequestBody CollectInfoVo collectInfo, HttpServletRequest request) {
        collectInfoService.add(collectInfo, request);
        return Result.success(collectInfo);
    }

    // 删除收藏信息
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        collectInfoService.delete(id);
        return Result.success();
    }

    // 更新收藏信息
    @PutMapping
    public Result update(@RequestBody CollectInfoVo collectInfo) {
        collectInfoService.update(collectInfo);
        return Result.success();
    }

    // 获取收藏信息详情
    @GetMapping("/{id}")
    public Result<CollectInfo> detail(@PathVariable Long id) {
        CollectInfo collectInfo = collectInfoService.findById(id);
        return Result.success(collectInfo);
    }

    // 获取所有收藏信息
    @GetMapping
    public Result<List<CollectInfoVo>> all() {
        return Result.success(collectInfoService.findAll());
    }

    // 分页查询收藏信息
    @GetMapping("/page/{name}")
    public Result<PageInfo<CollectInfoVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(collectInfoService.findPage(name, pageNum, pageSize, request));
    }
}