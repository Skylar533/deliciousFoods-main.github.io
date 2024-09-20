package com.example.vo;

import com.example.entity.NotesInfoComment;

// 笔记评论信息的视图对象，继承自笔记评论实体类
public class NotesInfoCommentVo extends NotesInfoComment {

    // 关联对象的名称，例如关联的笔记名称等
    private String foreignName;

    // 获取关联对象名称的方法
    public String getForeignName() {
        return foreignName;
    }

    // 设置关联对象名称的方法
    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }
}