package com.angeliquehenry.tabsinlive.data;

import android.content.Context;

import com.angeliquehenry.tabsinlive.entity.Sheet;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class SheetDao {

    private DatabaseHelper db;
    Dao<Sheet, Integer> sheetDao;

    public SheetDao(Context ctx)
    {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            db = dbManager.getHelper(ctx);
            sheetDao = db.getSheetDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }

    }

    public int create(Sheet comment)
    {
        try {
            return sheetDao.create(comment);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int update(Sheet comment)
    {
        try {
            return sheetDao.update(comment);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int delete(Sheet comment)
    {
        try {
            return sheetDao.delete(comment);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public List getAll()
    {
        try {
            return sheetDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
}
