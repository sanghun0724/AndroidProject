package com.ogrg.techtown.vocavocca;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfFavoriteList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfFavoriteList;

  public FavoriteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteList = new EntityInsertionAdapter<FavoriteList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `favoriteList`(`id`,`prengWord`,`prkorMean`,`prkorMean2`,`prexKor`,`prexEng`,`prexEngHidden`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteList value) {
        stmt.bindLong(1, value.getId());
        if (value.getEngWord() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEngWord());
        }
        if (value.getKorMean() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getKorMean());
        }
        if (value.getKorMean2() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getKorMean2());
        }
        if (value.getExKor() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getExKor());
        }
        if (value.getExEng() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getExEng());
        }
        if (value.getExEngHidden() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getExEngHidden());
        }
      }
    };
    this.__deletionAdapterOfFavoriteList = new EntityDeletionOrUpdateAdapter<FavoriteList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `favoriteList` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteList value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void addData(FavoriteList favoriteList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfFavoriteList.insert(favoriteList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(FavoriteList favoriteList) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfFavoriteList.handle(favoriteList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<FavoriteList> getFavoriteData() {
    final String _sql = "select * from favoritelist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfEngWord = _cursor.getColumnIndexOrThrow("prengWord");
      final int _cursorIndexOfKorMean = _cursor.getColumnIndexOrThrow("prkorMean");
      final int _cursorIndexOfKorMean2 = _cursor.getColumnIndexOrThrow("prkorMean2");
      final int _cursorIndexOfExKor = _cursor.getColumnIndexOrThrow("prexKor");
      final int _cursorIndexOfExEng = _cursor.getColumnIndexOrThrow("prexEng");
      final int _cursorIndexOfExEngHidden = _cursor.getColumnIndexOrThrow("prexEngHidden");
      final List<FavoriteList> _result = new ArrayList<FavoriteList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FavoriteList _item;
        _item = new FavoriteList();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpEngWord;
        _tmpEngWord = _cursor.getString(_cursorIndexOfEngWord);
        _item.setEngWord(_tmpEngWord);
        final String _tmpKorMean;
        _tmpKorMean = _cursor.getString(_cursorIndexOfKorMean);
        _item.setKorMean(_tmpKorMean);
        final String _tmpKorMean2;
        _tmpKorMean2 = _cursor.getString(_cursorIndexOfKorMean2);
        _item.setKorMean2(_tmpKorMean2);
        final String _tmpExKor;
        _tmpExKor = _cursor.getString(_cursorIndexOfExKor);
        _item.setExKor(_tmpExKor);
        final String _tmpExEng;
        _tmpExEng = _cursor.getString(_cursorIndexOfExEng);
        _item.setExEng(_tmpExEng);
        final String _tmpExEngHidden;
        _tmpExEngHidden = _cursor.getString(_cursorIndexOfExEngHidden);
        _item.setExEngHidden(_tmpExEngHidden);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int isFavorite(int id) {
    final String _sql = "SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
