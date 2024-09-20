package com.example.vo;

import com.example.entity.SubClassifyInfo;

// 这个类是菜谱小类信息的视图对象类，继承自菜谱小类实体类 SubClassifyInfo。
public class SubClassifyInfoVo extends SubClassifyInfo {

	// 存储所属菜谱大类的名称。
	private String classifyName;

	// 获取所属菜谱大类名称的方法。
	public String getClassifyName() {
		return classifyName;
	}

	// 设置所属菜谱大类名称的方法。
	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
}