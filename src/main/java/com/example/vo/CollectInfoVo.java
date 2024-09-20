package com.example.vo;

import com.example.entity.CollectInfo;

// 收藏信息的视图对象，继承自收藏实体类
public class CollectInfoVo extends CollectInfo {

	// 收藏的食物名称
	private String foodsName;

	// 获取收藏食物名称的方法
	public String getFoodsName() {
		return foodsName;
	}

	// 设置收藏食物名称的方法
	public void setFoodsName(String foodsName) {
		this.foodsName = foodsName;
	}
}