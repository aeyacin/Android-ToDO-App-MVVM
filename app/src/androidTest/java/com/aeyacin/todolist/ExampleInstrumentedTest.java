package com.aeyacin.todolist;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.aeyacin.todolist.data.db.AppDatabase;
import com.aeyacin.todolist.data.db.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    static User user;

    @BeforeClass
    public static void init() {
        System.out.println("launchActivity Start");
        user = new User();
        user.setName("ali can");
        user.setPassword("12345");
        user.setUserName("admin");
        user.setCreateDate(new Date().getTime());
    }

    @Before
    public void beforePerTest() {
        System.out.println("launchActivity Start");


    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.aeyacin.todolist", appContext.getPackageName());
    }

    @Test
    public void useAppDB() {
        System.out.println("useAppDB Test Start");

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        AppDatabase db = AppDatabase.getDatabase(appContext.getApplicationContext());


        db.getUserDao().AddUser(user);

        User result = db.getUserDao().getUser(user.getUserName());

        assertEquals("Data Not Equal", result.getName(), user.getName());

        user.setUserId(result.getUserId());
        System.out.println("useAppDB Test END");

    }




    @After
    public void afterPerTest() {
        System.out.println("afterPerTest end");
    }
}
