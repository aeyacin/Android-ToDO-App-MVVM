package com.aeyacin.todolist.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by aeyacin on 2019-09-12.
 */
@Entity(tableName = "ToDoRoster")
public class ToDoRoster implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer Id;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "UserId")
    private Integer UserId;

    @ColumnInfo(name = "CreateDate")
    private Long CreateDate;


    @ColumnInfo(name = "Color")
    private int Color;

    @ColumnInfo(name = "Show")
    private boolean Show;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Long getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Long createDate) {
        CreateDate = createDate;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public boolean isShow() {
        return Show;
    }

    public void setShow(boolean show) {
        this.Show = show;
    }
}
