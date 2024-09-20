package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.AdvertiserInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.AdvertiserInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.AdvertiserInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AdvertiserInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private AdvertiserInfoDao advertiserInfoDao;

    // 添加系统公告信息，设置时间并插入数据
    public AdvertiserInfo add(AdvertiserInfo advertiserInfo) {
        advertiserInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        advertiserInfoDao.insertSelective(advertiserInfo);
        return advertiserInfo;
    }

    // 删除系统公告信息
    public void delete(Long id) {
        advertiserInfoDao.deleteByPrimaryKey(id);
    }

    // 更新系统公告信息，设置时间并更新数据
    public void update(AdvertiserInfo advertiserInfo) {
        advertiserInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        advertiserInfoDao.updateByPrimaryKeySelective(advertiserInfo);
    }

    // 根据 ID 获取系统公告详细信息
    public AdvertiserInfo findById(Long id) {
        return advertiserInfoDao.selectByPrimaryKey(id);
    }

    // 获取所有系统公告信息列表
    public List<AdvertiserInfoVo> findAll() {
        return advertiserInfoDao.findByName("all");
    }

    // 分页查询系统公告信息列表
    public PageInfo<AdvertiserInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<AdvertiserInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    // 辅助方法，根据名称分页查询系统公告信息列表
    public List<AdvertiserInfoVo> findAllPage(HttpServletRequest request, String name) {
        return advertiserInfoDao.findByName(name);
    }
}
