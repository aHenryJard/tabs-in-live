package com.angeliquehenry.tabsinlive.data;

import android.content.Context;

import com.angeliquehenry.tabsinlive.entity.Sheet;
import com.angeliquehenry.tabsinlive.entity.Tab;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class TabDao {

    private DatabaseHelper db;
    Dao<Tab, Integer> tabDao;

    public TabDao(Context ctx)
    {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            db = dbManager.getHelper(ctx);
            tabDao = db.getTabDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }

    }

    public int create(Tab tab)
    {
        try {
            return tabDao.create(tab);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int update(Tab tab)
    {
        try {
            return tabDao.update(tab);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int delete(Tab tab)
    {
        try {
            return tabDao.delete(tab);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public List getAll()
    {
        try {
            return tabDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
}
