package com.aeyacin.todolist.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.aeyacin.todolist.data.db.AppDatabase;
import com.aeyacin.todolist.data.db.entities.User;
import com.aeyacin.todolist.ui.base.BaseActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class HomeActivityTest extends BaseActivity {
    AppDatabase db;

    User user;

    public HomeActivityTest() {
        user = new User();
        user.setName("ali can");
        user.setPassword("12345");
        user.setUserName("admin");
        user.setCreateDate(new Date().getTime());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getDatabase(this.getApplication());

    }

    @Before
    public void setUp() throws Exception {

        db.getUserDao().upsert(user);
    }

    @After
    public void tearDown() throws Exception {
        LiveData<User> liveUser = db.getUserDao().getUser(user.getUserName(), user.getPassword());

        assertSame("Db Nesnesi Null Döndü", liveUser.getValue() == null);


        User tempUser = liveUser.getValue();

        assert tempUser != null;
        assertEquals(tempUser.getCreateDate(), user.getCreateDate());
        assertSame("Db User Id Oluşturmamış", tempUser.getUserId() < 1);
        assertSame("Sonucta Hata Var DB Id oluşturmamış", user.getUserId(), tempUser.getUserId());

    }


}