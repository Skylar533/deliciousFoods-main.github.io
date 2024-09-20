package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.dao.FoodsMaterialInfoDao;
import com.example.entity.FoodsMaterialInfo;
import com.example.vo.FoodsMaterialInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FoodsMaterialInfoService {

    @Resource
    private FoodsMaterialInfoDao foodsMaterialInfoDao;

    // 添加食材信息
    public FoodsMaterialInfo add(FoodsMaterialInfo info) {
        foodsMaterialInfoDao.insertSelective(info);
        return info;
    }

    // 删除食材信息
    public void delete(Long id) {
        foodsMaterialInfoDao.deleteByPrimaryKey(id);
    }

    // 更新食材信息
    public void update(FoodsMaterialInfo info) {
        foodsMaterialInfoDao.updateByPrimaryKeySelective(info);
    }

    // 根据 ID 获取食材详细信息
    public FoodsMaterialInfoVo findById(Long id) {
        List<FoodsMaterialInfoVo> list = foodsMaterialInfoDao.findByNameAndId(null, id);
        if (!CollectionUtil.isEmpty(list)) {
            return list.get(0);
        }
        return new FoodsMaterialInfoVo();
    }

    // 获取所有食材信息列表
    public List<FoodsMaterialInfoVo> findAll() {
        return foodsMaterialInfoDao.findByNameAndId("all", null);
    }

    // 分页查询食材信息列表
    public PageInfo<FoodsMaterialInfoVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FoodsMaterialInfoVo> info = foodsMaterialInfoDao.findByNameAndId(name, null);
        return PageInfo.of(info);
    }
}