package com.aeyacin.todolist.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by aeyacin on 2019-09-12.
 */
@Entity(tableName = "ToDos")
public class ToDo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer Id;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "UserId")
    private Integer UserId;

    @ColumnInfo(name = "RosterId")
    private int RosterId;

    @ColumnInfo(name = "CreateDate")
    private Long CreateDate;

    @ColumnInfo(name = "DeadLine")
    private Long DeadLine;

    @ColumnInfo(name = "Status")
    private String Status;

    @ColumnInfo(name = "Color")
    private int Color;

    @ColumnInfo(name = "Identifier")
    private String Identifier;

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }


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

    public int getRosterId() {
        return RosterId;
    }

    public void setRosterId(int rosterId) {
        RosterId = rosterId;
    }

    public Long getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Long createDate) {
        CreateDate = createDate;
    }

    public Long getDeadLine() {
        return DeadLine;
    }

    public void setDeadLine(Long deadLine) {
        DeadLine = deadLine;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }
}
