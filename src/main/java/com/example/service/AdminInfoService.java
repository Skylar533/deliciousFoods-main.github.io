package com.example.service;

import com.example.dao.AdminInfoDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.entity.AdminInfo;
import com.example.exception.CustomException;
import com.example.common.ResultCode;
import com.example.vo.AdminInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.crypto.SecureUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class AdminInfoService {

    @Resource
    private AdminInfoDao adminInfoDao;

    // 添加管理员信息，进行唯一校验，设置密码并插入数据
    public AdminInfo add(AdminInfo adminInfo) {
        int count = adminInfoDao.checkRepeat("name", adminInfo.getName(), null);
        if (count > 0) {
            throw new CustomException("1001", "用户名\"" + adminInfo.getName() + "\"已存在");
        }
        if (StringUtils.isEmpty(adminInfo.getPassword())) {
            adminInfo.setPassword(SecureUtil.md5("123456"));
        } else {
            adminInfo.setPassword(SecureUtil.md5(adminInfo.getPassword()));
        }
        adminInfoDao.insertSelective(adminInfo);
        return adminInfo;
    }

    // 删除管理员信息
    public void delete(Long id) {
        adminInfoDao.deleteByPrimaryKey(id);
    }

    // 更新管理员信息
    public void update(AdminInfo adminInfo) {
        adminInfoDao.updateByPrimaryKeySelective(adminInfo);
    }

    // 根据 ID 获取管理员详细信息
    public AdminInfo findById(Long id) {
        return adminInfoDao.selectByPrimaryKey(id);
    }

    // 获取所有管理员信息列表
    public List<AdminInfoVo> findAll() {
        return adminInfoDao.findByName("all");
    }

    // 分页查询管理员信息列表
    public PageInfo<AdminInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<AdminInfoVo> all = adminInfoDao.findByName(name);
        return PageInfo.of(all);
    }

    // 根据用户名查找管理员信息
    public AdminInfoVo findByUserName(String name) {
        return adminInfoDao.findByUsername(name);
    }

    // 管理员登录验证
    public AdminInfo login(String username, String password) {
        AdminInfo adminInfo = adminInfoDao.findByUsername(username);
        if (adminInfo == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        if (!SecureUtil.md5(password).equalsIgnoreCase(adminInfo.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return adminInfo;
    }
}