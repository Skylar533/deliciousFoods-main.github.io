package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.dao.CollectInfoDao;
import com.example.vo.PraiseInfoVo;
import org.springframework.stereotype.Service;
import com.example.entity.CollectInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.CollectInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class CollectInfoService {

    @Resource
    private CollectInfoDao collectInfoDao;

    // 添加收藏信息，检查是否已收藏，设置用户信息和时间并插入数据
    public CollectInfo add(CollectInfo collectInfo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        CollectInfoVo collectInfoVo = collectInfoDao.findByUser(user, collectInfo);
        if (collectInfoVo!= null) {
            return null;
        }
        collectInfo.setUserId(user.getId());
        collectInfo.setLevel(user.getLevel());
        collectInfo.setTime(DateUtil.now());
        collectInfoDao.insertSelective(collectInfo);
        return collectInfo;
    }

    // 删除收藏信息
    public void delete(Long id) {
        collectInfoDao.deleteByPrimaryKey(id);
    }

    // 更新收藏信息
    public void update(CollectInfo collectInfo) {
        collectInfoDao.updateByPrimaryKeySelective(collectInfo);
    }

    // 根据 ID 获取收藏信息详情
    public CollectInfo findById(Long id) {
        return collectInfoDao.selectByPrimaryKey(id);
    }

    // 获取所有收藏信息列表
    public List<CollectInfoVo> findAll() {
        return collectInfoDao.findByName("all", null, null);
    }

    // 分页查询收藏信息列表
    public PageInfo<CollectInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<CollectInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    // 辅助方法，根据名称、用户 ID 和等级分页查询收藏信息列表
    public List<CollectInfoVo> findAllPage(HttpServletRequest request, String name) {
        String userId = request.getParameter("userId");
        String level = request.getParameter("level");
        return collectInfoDao.findByName(name, userId, level);
    }
}