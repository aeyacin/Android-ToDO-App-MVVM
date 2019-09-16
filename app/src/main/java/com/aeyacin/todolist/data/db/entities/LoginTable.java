package com.aeyacin.todolist.data.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Login session table
 */
@Entity(tableName = "LoginDetails")
public class LoginTable {


    @PrimaryKey(autoGenerate = true)
    private Integer LoginId;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "Password")
    private String Password;


    @ColumnInfo(name = "Auto")
    private int Auto;


    @ColumnInfo(name = "UserId")
    private int UserID;


    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public Boolean getIsAutoLogin() {
        return Auto > 0;
    }

    public Integer getAuto() {
        return Auto;
    }

    public void setAuto(int auto) {
        Auto = auto;
    }

    public void setIsAutoLogin(Boolean autoLogin) {
        Auto = autoLogin ? 1 : 0;
    }


    public Integer getLoginId() {
        return LoginId;
    }

    public void setLoginId(Integer loginId) {
        LoginId = loginId;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}


