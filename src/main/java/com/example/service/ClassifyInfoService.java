package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.ClassifyInfoDao;
import com.example.dao.SubClassifyInfoDao;
import com.example.vo.SubClassifyInfoVo;
import org.springframework.stereotype.Service;
import com.example.entity.ClassifyInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.ClassifyInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ClassifyInfoService {

    @Resource
    private ClassifyInfoDao classifyInfoDao;

    @Resource
    private SubClassifyInfoDao subClassifyInfoDao;

    // 添加菜谱大类信息
    public ClassifyInfo add(ClassifyInfo classifyInfo) {
        classifyInfoDao.insertSelective(classifyInfo);
        return classifyInfo;
    }

    // 删除菜谱大类信息
    public void delete(Long id) {
        classifyInfoDao.deleteByPrimaryKey(id);
    }

    // 更新菜谱大类信息
    public void update(ClassifyInfo classifyInfo) {
        classifyInfoDao.updateByPrimaryKeySelective(classifyInfo);
    }

    // 根据 ID 获取菜谱大类详细信息
    public ClassifyInfo findById(Long id) {
        return classifyInfoDao.selectByPrimaryKey(id);
    }

    // 获取所有菜谱大类信息列表，并关联子分类信息
    public List<ClassifyInfoVo> findAll() {
        List<ClassifyInfoVo> all = classifyInfoDao.findByName("all");
        for (ClassifyInfoVo classifyInfoVo : all) {
            List<SubClassifyInfoVo> subList = subClassifyInfoDao.findByClassifyId(classifyInfoVo.getId());
            classifyInfoVo.setSubList(subList);
        }
        return all;
    }

    // 分页查询菜谱大类信息列表
    public PageInfo<ClassifyInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClassifyInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    // 辅助方法，根据名称分页查询菜谱大类信息列表
    public List<ClassifyInfoVo> findAllPage(HttpServletRequest request, String name) {
        return classifyInfoDao.findByName(name);
    }
}