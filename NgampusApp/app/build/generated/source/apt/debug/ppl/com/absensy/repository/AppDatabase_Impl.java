package ppl.com.absensy.repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDatabase_Impl extends AppDatabase {
  private volatile SubjectDao _subjectDao;

  private volatile AbsenceDetailDao _absenceDetailDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `subjects` (`id` TEXT NOT NULL, `name` TEXT, `absence_amount` INTEGER NOT NULL, `class_schedule` INTEGER, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `absence_details` (`id` TEXT NOT NULL, `absence_date` INTEGER, `subject_id` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`subject_id`) REFERENCES `subjects`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"11a7c9548e012ea05a3255ddebff3193\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `subjects`");
        _db.execSQL("DROP TABLE IF EXISTS `absence_details`");
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
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSubjects = new HashMap<String, TableInfo.Column>(4);
        _columnsSubjects.put("id", new TableInfo.Column("id", "TEXT", true, 1));
        _columnsSubjects.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsSubjects.put("absence_amount", new TableInfo.Column("absence_amount", "INTEGER", true, 0));
        _columnsSubjects.put("class_schedule", new TableInfo.Column("class_schedule", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubjects = new TableInfo("subjects", _columnsSubjects, _foreignKeysSubjects, _indicesSubjects);
        final TableInfo _existingSubjects = TableInfo.read(_db, "subjects");
        if (! _infoSubjects.equals(_existingSubjects)) {
          throw new IllegalStateException("Migration didn't properly handle subjects(ppl.com.absensy.model.Subject).\n"
                  + " Expected:\n" + _infoSubjects + "\n"
                  + " Found:\n" + _existingSubjects);
        }
        final HashMap<String, TableInfo.Column> _columnsAbsenceDetails = new HashMap<String, TableInfo.Column>(3);
        _columnsAbsenceDetails.put("id", new TableInfo.Column("id", "TEXT", true, 1));
        _columnsAbsenceDetails.put("absence_date", new TableInfo.Column("absence_date", "INTEGER", false, 0));
        _columnsAbsenceDetails.put("subject_id", new TableInfo.Column("subject_id", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAbsenceDetails = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAbsenceDetails.add(new TableInfo.ForeignKey("subjects", "CASCADE", "NO ACTION",Arrays.asList("subject_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAbsenceDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAbsenceDetails = new TableInfo("absence_details", _columnsAbsenceDetails, _foreignKeysAbsenceDetails, _indicesAbsenceDetails);
        final TableInfo _existingAbsenceDetails = TableInfo.read(_db, "absence_details");
        if (! _infoAbsenceDetails.equals(_existingAbsenceDetails)) {
          throw new IllegalStateException("Migration didn't properly handle absence_details(ppl.com.absensy.model.AbsenceDetail).\n"
                  + " Expected:\n" + _infoAbsenceDetails + "\n"
                  + " Found:\n" + _existingAbsenceDetails);
        }
      }
    }, "11a7c9548e012ea05a3255ddebff3193", "aced6d1afd1703041b1a242902aa8d20");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "subjects","absence_details");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `subjects`");
      _db.execSQL("DELETE FROM `absence_details`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SubjectDao subjectDao() {
    if (_subjectDao != null) {
      return _subjectDao;
    } else {
      synchronized(this) {
        if(_subjectDao == null) {
          _subjectDao = new SubjectDao_Impl(this);
        }
        return _subjectDao;
      }
    }
  }

  @Override
  public AbsenceDetailDao absenceDetailDao() {
    if (_absenceDetailDao != null) {
      return _absenceDetailDao;
    } else {
      synchronized(this) {
        if(_absenceDetailDao == null) {
          _absenceDetailDao = new AbsenceDetailDao_Impl(this);
        }
        return _absenceDetailDao;
      }
    }
  }
}
