package com.aeyacin.todolist.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.aeyacin.todolist.data.db.entities.LoginTable;
import com.aeyacin.todolist.data.db.entities.ToDo;
import com.aeyacin.todolist.data.db.entities.ToDoRoster;
import com.aeyacin.todolist.data.db.entities.User;


/**
 * Created by aeyacin on 2019-09-12.
 */

@Database(entities = {LoginTable.class, User.class, ToDo.class, ToDoRoster.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;


    public abstract UserDoa getUserDao();

    public abstract LoginDao getLoginDao();

    public abstract ToDoDoa getToDoDao();

    public abstract RosterDoa getRosterDao();

    public static AppDatabase getDatabase(Context context) {

        synchronized (AppDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "todo-database")
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return INSTANCE;
    }


}
