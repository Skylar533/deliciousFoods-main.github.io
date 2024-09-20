package com.example.service;

import com.example.exception.CustomException;
import com.example.dao.NotesInfoCommentDao;
import org.springframework.stereotype.Service;
import com.example.entity.NotesInfoComment;
import com.example.vo.NotesInfoCommentVo;
import com.example.entity.Account;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NotesInfoCommentService {

    @Resource
    private NotesInfoCommentDao notesInfoCommentDao;

    // 添加笔记评论信息，检查用户是否登录，设置用户名和时间并插入数据
    public NotesInfoComment add(NotesInfoComment commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        commentInfo.setName(user.getName());
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        notesInfoCommentDao.insertSelective(commentInfo);
        return commentInfo;
    }

    // 删除笔记评论信息
    public void delete(Long id) {
        notesInfoCommentDao.deleteByPrimaryKey(id);
    }

    // 更新笔记评论信息，设置时间并更新数据
    public void update(NotesInfoComment commentInfo) {
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        notesInfoCommentDao.updateByPrimaryKeySelective(commentInfo);
    }

    // 根据 ID 获取笔记评论详细信息
    public NotesInfoComment findById(Long id) {
        return notesInfoCommentDao.selectByPrimaryKey(id);
    }

    // 获取所有笔记评论信息列表的视图对象
    public List<NotesInfoCommentVo> findAll() {
        return notesInfoCommentDao.findAllVo(null);
    }

    // 分页查询笔记评论信息列表的视图对象
    public PageInfo<NotesInfoCommentVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NotesInfoCommentVo> all = notesInfoCommentDao.findAllVo(name);
        return PageInfo.of(all);
    }

    // 根据外键 ID 获取笔记评论列表
    public List<NotesInfoComment> findByForeignId(Long id) {
        return notesInfoCommentDao.findByForeignId(id);
    }
}