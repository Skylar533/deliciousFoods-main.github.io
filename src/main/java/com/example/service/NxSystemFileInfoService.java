package com.example.service;

import com.example.dao.NxSystemFileInfoDao;
import com.example.entity.NxSystemFileInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NxSystemFileInfoService {

    @Value("${authority.info}")
    private String authorityInfo;

    @Resource
    private NxSystemFileInfoDao nxSystemFileInfoDao;

    // 添加系统文件信息
    public NxSystemFileInfo add(NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoDao.insertSelective(nxSystemFileInfo);
        return nxSystemFileInfo;
    }

    // 删除系统文件信息
    public void delete(Long id) {
        nxSystemFileInfoDao.deleteByPrimaryKey(id);
    }

    // 更新系统文件信息
    public void update(NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoDao.updateByPrimaryKeySelective(nxSystemFileInfo);
    }

    // 根据 ID 获取系统文件详细信息
    public NxSystemFileInfo findById(Long id) {
        return nxSystemFileInfoDao.selectByPrimaryKey(id);
    }

    // 根据文件名获取系统文件信息
    public NxSystemFileInfo findByFileName(String name) {
        return nxSystemFileInfoDao.findByFileName(name);
    }

    // 获取所有系统文件信息列表
    public List<NxSystemFileInfo> findAll() {
        return nxSystemFileInfoDao.findByName("all");
    }

    // 分页查询系统文件信息列表
    public PageInfo<NxSystemFileInfo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<NxSystemFileInfo> all = nxSystemFileInfoDao.findByName(name);
        return PageInfo.of(all);
    }
}