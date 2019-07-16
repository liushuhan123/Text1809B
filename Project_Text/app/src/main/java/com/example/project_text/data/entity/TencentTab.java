package com.example.project_text.data.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class TencentTab {
    @Id(autoincrement = true)
    private Long mid;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int customOrder;// 本地用户自己排序的字段
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
    @Generated(hash = 1793347869)
    public TencentTab(Long mid, int courseId, int id, String name, int order,
            int customOrder, int parentChapterId, boolean userControlSetTop,
            int visible) {
        this.mid = mid;
        this.courseId = courseId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.customOrder = customOrder;
        this.parentChapterId = parentChapterId;
        this.userControlSetTop = userControlSetTop;
        this.visible = visible;
    }
    @Generated(hash = 1001324610)
    public TencentTab() {
    }
    public Long getMid() {
        return this.mid;
    }
    public void setMid(Long mid) {
        this.mid = mid;
    }
    public int getCourseId() {
        return this.courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getCustomOrder() {
        return this.customOrder;
    }
    public void setCustomOrder(int customOrder) {
        this.customOrder = customOrder;
    }
    public int getParentChapterId() {
        return this.parentChapterId;
    }
    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }
    public boolean getUserControlSetTop() {
        return this.userControlSetTop;
    }
    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }
    public int getVisible() {
        return this.visible;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }
    
  


}
