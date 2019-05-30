package ppl.com.absensy.repository;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EmptyResultSetException;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import io.reactivex.Single;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import java.util.concurrent.Callable;
import ppl.com.absensy.model.Subject;

@SuppressWarnings("unchecked")
public class SubjectDao_Impl implements SubjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSubject;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfSubject;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfSubject;

  public SubjectDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubject = new EntityInsertionAdapter<Subject>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `subjects`(`id`,`name`,`absence_amount`,`class_schedule`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subject value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getAbsenceAmount());
        final Long _tmp;
        _tmp = Converter.toTimestamp(value.getClassSchedule());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
      }
    };
    this.__deletionAdapterOfSubject = new EntityDeletionOrUpdateAdapter<Subject>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `subjects` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subject value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfSubject = new EntityDeletionOrUpdateAdapter<Subject>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `subjects` SET `id` = ?,`name` = ?,`absence_amount` = ?,`class_schedule` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subject value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getAbsenceAmount());
        final Long _tmp;
        _tmp = Converter.toTimestamp(value.getClassSchedule());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        if (value.getId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getId());
        }
      }
    };
  }

  @Override
  public void save(Subject subject) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubject.insert(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Subject subject) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfSubject.handle(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Subject subject) {
    __db.beginTransaction();
    try {
      __updateAdapterOfSubject.handle(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Single<List<Subject>> findAll() {
    final String _sql = "select * from subjects order by class_schedule asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return Single.fromCallable(new Callable<List<Subject>>() {
      @Override
      public List<Subject> call() throws Exception {
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfAbsenceAmount = _cursor.getColumnIndexOrThrow("absence_amount");
          final int _cursorIndexOfClassSchedule = _cursor.getColumnIndexOrThrow("class_schedule");
          final java.util.List<ppl.com.absensy.model.Subject> _result = new java.util.ArrayList<ppl.com.absensy.model.Subject>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ppl.com.absensy.model.Subject _item;
            _item = new ppl.com.absensy.model.Subject();
            final java.lang.String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final java.lang.String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final int _tmpAbsenceAmount;
            _tmpAbsenceAmount = _cursor.getInt(_cursorIndexOfAbsenceAmount);
            _item.setAbsenceAmount(_tmpAbsenceAmount);
            final java.util.Date _tmpClassSchedule;
            final java.lang.Long _tmp;
            if (_cursor.isNull(_cursorIndexOfClassSchedule)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfClassSchedule);
            }
            _tmpClassSchedule = ppl.com.absensy.repository.Converter.fromTimestamp(_tmp);
            _item.setClassSchedule(_tmpClassSchedule);
            _result.add(_item);
          }
          if(_result == null) {
            throw new EmptyResultSetException("Query returned empty result set: " + _statement.getSql());
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Single<Subject> findById(String subjectId) {
    final String _sql = "select * from subjects where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (subjectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subjectId);
    }
    return Single.fromCallable(new Callable<Subject>() {
      @Override
      public Subject call() throws Exception {
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfAbsenceAmount = _cursor.getColumnIndexOrThrow("absence_amount");
          final int _cursorIndexOfClassSchedule = _cursor.getColumnIndexOrThrow("class_schedule");
          final ppl.com.absensy.model.Subject _result;
          if(_cursor.moveToFirst()) {
            _result = new ppl.com.absensy.model.Subject();
            final java.lang.String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _result.setId(_tmpId);
            final java.lang.String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _result.setName(_tmpName);
            final int _tmpAbsenceAmount;
            _tmpAbsenceAmount = _cursor.getInt(_cursorIndexOfAbsenceAmount);
            _result.setAbsenceAmount(_tmpAbsenceAmount);
            final java.util.Date _tmpClassSchedule;
            final java.lang.Long _tmp;
            if (_cursor.isNull(_cursorIndexOfClassSchedule)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfClassSchedule);
            }
            _tmpClassSchedule = ppl.com.absensy.repository.Converter.fromTimestamp(_tmp);
            _result.setClassSchedule(_tmpClassSchedule);
          } else {
            _result = null;
          }
          if(_result == null) {
            throw new EmptyResultSetException("Query returned empty result set: " + _statement.getSql());
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
