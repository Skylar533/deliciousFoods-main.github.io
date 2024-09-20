package com.example.vo;

import com.example.entity.ClassifyInfo;
import java.util.List;

// 这个类是菜谱大类信息的视图对象类，继承自菜谱大类实体类 ClassifyInfo。
public class ClassifyInfoVo extends ClassifyInfo {

    // 存储该菜谱大类对应的小类信息列表，类型为 SubClassifyInfoVo 的列表。
    List<SubClassifyInfoVo> subList;

    // 获取小类信息列表的方法。
    public List<SubClassifyInfoVo> getSubList() {
        return subList;
    }

    // 设置小类信息列表的方法。
    public void setSubList(List<SubClassifyInfoVo> subList) {
        this.subList = subList;
    }
}