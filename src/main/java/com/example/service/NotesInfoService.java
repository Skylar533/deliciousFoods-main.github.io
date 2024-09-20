package com.example.service;

import com.example.dao.CollectInfoDao;
import com.example.dao.NotesInfoDao;
import com.example.dao.PraiseInfoDao;
import com.example.entity.NotesInfo;
import com.example.exception.CustomException;
import com.example.vo.NotesInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class NotesInfoService {

    @Resource
    private NotesInfoDao notesInfoDao;
    @Resource
    private PraiseInfoDao praiseInfoDao;
    @Resource
    private CollectInfoDao collectInfoDao;

    // 添加笔记信息
    public NotesInfo add(NotesInfo info) {
        notesInfoDao.insertSelective(info);
        return info;
    }

    // 删除笔记信息
    public void delete(Long id) {
        notesInfoDao.deleteByPrimaryKey(id);
    }

    // 更新笔记信息，检查状态防止已审核的笔记被更新
    public void update(NotesInfo info) {
        NotesInfo notesInfo = findById(info.getId());
        if (notesInfo.getStatus()!= null && info.getStatus()!= null && notesInfo.getStatus()!= 0) {
            throw new CustomException("-1", "该笔记已审核");
        }
        notesInfoDao.updateByPrimaryKeySelective(info);
    }

    // 根据 ID 获取笔记详细信息，包括点赞数和收藏数
    public NotesInfo findById(Long id) {
        NotesInfo notesInfo = notesInfoDao.selectByPrimaryKey(id);
        Integer count = praiseInfoDao.count(id, null);
        notesInfo.setPraiseCount(count);

        Integer collectCount = collectInfoDao.count(id, null);
        notesInfo.setCollectCount(collectCount);
        return notesInfo;
    }

    // 获取所有笔记信息列表
    public List<NotesInfoVo> findAll() {
        return notesInfoDao.findByName("all", null, null);
    }

    // 分页查询笔记信息列表
    public PageInfo<NotesInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<NotesInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    // 辅助方法，根据名称、用户 ID 和状态分页查询笔记信息列表
    public List<NotesInfoVo> findAllPage(HttpServletRequest request, String name) {
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");
        return notesInfoDao.findByName(name, userId, status);
    }
}