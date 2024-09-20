package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.MessageInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.MessageInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.MessageInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class MessageInfoService {

    @Resource
    private MessageInfoDao messageInfoDao;

    // 添加趣味答题信息
    public MessageInfo add(MessageInfo messageInfo) {
        messageInfoDao.insertSelective(messageInfo);
        return messageInfo;
    }

    // 删除趣味答题信息
    public void delete(Long id) {
        messageInfoDao.deleteByPrimaryKey(id);
    }

    // 更新趣味答题信息
    public void update(MessageInfo messageInfo) {
        messageInfoDao.updateByPrimaryKeySelective(messageInfo);
    }

    // 根据 ID 获取趣味答题详细信息
    public MessageInfo findById(Long id) {
        return messageInfoDao.selectByPrimaryKey(id);
    }

    // 获取所有趣味答题信息列表
    public List<MessageInfoVo> findAll() {
        return messageInfoDao.findByName("all");
    }

    // 分页查询趣味答题信息列表
    public PageInfo<MessageInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<MessageInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    // 辅助方法，根据名称分页查询趣味答题信息列表
    public List<MessageInfoVo> findAllPage(HttpServletRequest request, String name) {
        return messageInfoDao.findByName(name);
    }
}