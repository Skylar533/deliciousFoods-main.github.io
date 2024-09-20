package com.example.vo;

import com.example.entity.NotesInfo;

// 笔记信息的视图对象类，继承自笔记实体类 NotesInfo。
public class NotesInfoVo extends NotesInfo {

	// 发布笔记的用户名称
	private String userName;

	// 获取用户名称的方法
	public String getUserName() {
		return userName;
	}

	// 设置用户名称的方法
	public void setUserName(String userName) {
		this.userName = userName;
	}
}