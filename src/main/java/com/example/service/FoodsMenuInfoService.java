package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.dao.CollectInfoDao;
import com.example.dao.FoodsMenuInfoDao;
import com.example.dao.PraiseInfoDao;
import com.example.entity.FoodsMenuInfo;
import com.example.vo.FoodsMenuInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodsMenuInfoService {

    @Resource
    private FoodsMenuInfoDao foodsMenuInfoDao;
    @Resource
    private PraiseInfoDao praiseInfoDao;
    @Resource
    private CollectInfoDao collectInfoDao;

    // 添加菜谱信息
    public FoodsMenuInfo add(FoodsMenuInfo info) {
        foodsMenuInfoDao.insertSelective(info);
        return info;
    }

    // 删除菜谱信息
    public void delete(Long id) {
        foodsMenuInfoDao.deleteByPrimaryKey(id);
    }

    // 更新菜谱信息
    public void update(FoodsMenuInfo info) {
        foodsMenuInfoDao.updateByPrimaryKeySelective(info);
    }

    // 根据 ID 获取菜谱详细信息，包括点赞数和收藏数
    public FoodsMenuInfoVo findById(Long id) {
        List<FoodsMenuInfoVo> list = foodsMenuInfoDao.findByNameAndId(null, id, null);
        if (!CollectionUtil.isEmpty(list)) {
            FoodsMenuInfoVo foodsMenuInfoVo = list.get(0);
            Integer count = praiseInfoDao.count(null, id);
            foodsMenuInfoVo.setPraiseCount(count);

            Integer collectCount = collectInfoDao.count(null, id);
            foodsMenuInfoVo.setCollectCount(collectCount);
            return foodsMenuInfoVo;
        }
        return new FoodsMenuInfoVo();
    }

    // 获取所有菜谱信息列表
    public List<FoodsMenuInfoVo> findAll() {
        return foodsMenuInfoDao.findByNameAndId("all", null, null);
    }

    // 分页查询菜谱信息列表，包括点赞数和收藏数，并按点赞数排序
    public PageInfo<FoodsMenuInfoVo> findPage(String name, Long classifyId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FoodsMenuInfoVo> info = foodsMenuInfoDao.findByNameAndId(name, null, classifyId);
        for (FoodsMenuInfoVo foodsMenuInfoVo : info) {
            Long id = foodsMenuInfoVo.getId();
            Integer count = praiseInfoDao.count(null, id);
            foodsMenuInfoVo.setPraiseCount(count);

            Integer collectCount = collectInfoDao.count(null, id);
            foodsMenuInfoVo.setCollectCount(collectCount);
        }
        info = info.stream().sorted(Comparator.comparing(FoodsMenuInfoVo::getPraiseCount).reversed()).collect(Collectors.toList());
        return PageInfo.of(info);
    }

    // 根据用户分页查询菜谱信息列表
    public PageInfo<FoodsMenuInfoVo> findPageByUser(String name, String username, Integer level, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FoodsMenuInfoVo> info = foodsMenuInfoDao.findByNameAndUser(name, username, level);
        return PageInfo.of(info);
    }
}