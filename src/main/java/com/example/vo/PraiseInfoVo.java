package com.example.vo;

import com.example.entity.PraiseInfo;

// 点赞信息的视图对象类，继承自点赞实体类 PraiseInfo。
public class PraiseInfoVo extends PraiseInfo {

	// 关联的笔记名称
	private String notesName;

	// 获取关联笔记名称的方法。
	public String getNotesName() {
		return notesName;
	}

	// 设置关联笔记名称的方法。
	public void setNotesName(String notesName) {
		this.notesName = notesName;
	}
}