package com.example.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

// 用于存储 Echarts 图表数据的类，实现了 Serializable 接口以便序列化
public class EchartsData implements Serializable {

    // 图表标题信息
    private Map title;
    // 图表图例信息
    private Map legend;
    // 图表提示框信息
    private Map tooltip;
    // 图表 X 轴信息
    private Map xAxis;
    // 图表 Y 轴信息
    private Object yAxis;
    // 图表系列数据
    private List<Series> series;

    // 以下是各个属性的 getter 和 setter 方法
    public Map getTitle() {
        return title;
    }

    public void setTitle(Map title) {
        this.title = title;
    }

    public Map getLegend() {
        return legend;
    }

    public void setLegend(Map legend) {
        this.legend = legend;
    }

    public Map getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map tooltip) {
        this.tooltip = tooltip;
    }

    public Map getxAxis() {
        return xAxis;
    }

    public void setxAxis(Map xAxis) {
        this.xAxis = xAxis;
    }

    public Object getyAxis() {
        return yAxis;
    }

    public void setyAxis(Object yAxis) {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    // 内部静态类，用于存储数据信息
    public static class Data {
        // 方向
        private String orient;
        // 顶部距离
        private Integer top;
        // X 轴位置
        private String x;
        // 数据列表
        private List<Object> data;

        // 以下是各个属性的 getter 和 setter 方法
        public String getOrient() {
            return orient;
        }

        public void setOrient(String orient) {
            this.orient = orient;
        }

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }

    // 内部静态类，用于存储图表系列信息
    public static class Series {
        // 系列名称
        private String name;
        // 系列类型
        private String type;
        // 半径
        private String radius;
        // 数据列表
        private List<Object> data;

        // 以下是各个属性的 getter 和 setter 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }
}