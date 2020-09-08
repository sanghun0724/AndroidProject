package com.ogrg.techtown.vocavocca;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class FavoriteDatabase_Impl extends FavoriteDatabase {
  private volatile FavoriteDao _favoriteDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `favoriteList` (`id` INTEGER NOT NULL, `prengWord` TEXT, `prkorMean` TEXT, `prkorMean2` TEXT, `prexKor` TEXT, `prexEng` TEXT, `prexEngHidden` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"2c54027883688b4ce56b025fc986fac2\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `favoriteList`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFavoriteList = new HashMap<String, TableInfo.Column>(7);
        _columnsFavoriteList.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsFavoriteList.put("prengWord", new TableInfo.Column("prengWord", "TEXT", false, 0));
        _columnsFavoriteList.put("prkorMean", new TableInfo.Column("prkorMean", "TEXT", false, 0));
        _columnsFavoriteList.put("prkorMean2", new TableInfo.Column("prkorMean2", "TEXT", false, 0));
        _columnsFavoriteList.put("prexKor", new TableInfo.Column("prexKor", "TEXT", false, 0));
        _columnsFavoriteList.put("prexEng", new TableInfo.Column("prexEng", "TEXT", false, 0));
        _columnsFavoriteList.put("prexEngHidden", new TableInfo.Column("prexEngHidden", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoriteList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoriteList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoriteList = new TableInfo("favoriteList", _columnsFavoriteList, _foreignKeysFavoriteList, _indicesFavoriteList);
        final TableInfo _existingFavoriteList = TableInfo.read(_db, "favoriteList");
        if (! _infoFavoriteList.equals(_existingFavoriteList)) {
          throw new IllegalStateException("Migration didn't properly handle favoriteList(com.ogrg.techtown.vocavocca.FavoriteList).\n"
                  + " Expected:\n" + _infoFavoriteList + "\n"
                  + " Found:\n" + _existingFavoriteList);
        }
      }
    }, "2c54027883688b4ce56b025fc986fac2", "f49ae8886e7d2b4c3bfcae0264bc64ad");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "favoriteList");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `favoriteList`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public FavoriteDao favoriteDao() {
    if (_favoriteDao != null) {
      return _favoriteDao;
    } else {
      synchronized(this) {
        if(_favoriteDao == null) {
          _favoriteDao = new FavoriteDao_Impl(this);
        }
        return _favoriteDao;
      }
    }
  }
}
