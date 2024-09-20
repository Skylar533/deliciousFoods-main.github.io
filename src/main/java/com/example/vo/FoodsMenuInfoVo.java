package com.example.vo;

import com.example.entity.FoodsMenuInfo;

// 菜谱信息的视图对象类，继承自菜谱实体类
public class FoodsMenuInfoVo extends FoodsMenuInfo {

	// 小类名称
	private String subName;
	// 点赞数量
	private Integer praiseCount;
	// 收藏数量
	private Integer collectCount;

	// 获取点赞数量的方法
	public Integer getPraiseCount() {
		return praiseCount;
	}

	// 设置点赞数量的方法
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	// 获取收藏数量的方法
	public Integer getCollectCount() {
		return collectCount;
	}

	// 设置收藏数量的方法
	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	// 获取小类名称的方法
	public String getSubName() {
		return subName;
	}

	// 设置子小类名称的方法
	public void setSubName(String subName) {
		this.subName = subName;
	}
}