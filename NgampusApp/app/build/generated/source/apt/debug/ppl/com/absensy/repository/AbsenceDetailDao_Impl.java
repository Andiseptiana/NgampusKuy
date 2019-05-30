package ppl.com.absensy.repository;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EmptyResultSetException;
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
import ppl.com.absensy.model.AbsenceDetail;

@SuppressWarnings("unchecked")
public class AbsenceDetailDao_Impl implements AbsenceDetailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfAbsenceDetail;

  public AbsenceDetailDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAbsenceDetail = new EntityInsertionAdapter<AbsenceDetail>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `absence_details`(`id`,`absence_date`,`subject_id`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AbsenceDetail value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        final Long _tmp;
        _tmp = Converter.toTimestamp(value.getAbsenceDate());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        if (value.getSubjectId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSubjectId());
        }
      }
    };
  }

  @Override
  public void save(AbsenceDetail absenceDetail) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfAbsenceDetail.insert(absenceDetail);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Single<List<AbsenceDetail>> findAllBySubjectId(String subjectId) {
    final String _sql = "select * from absence_details where subject_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (subjectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subjectId);
    }
    return Single.fromCallable(new Callable<List<AbsenceDetail>>() {
      @Override
      public List<AbsenceDetail> call() throws Exception {
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfAbsenceDate = _cursor.getColumnIndexOrThrow("absence_date");
          final int _cursorIndexOfSubjectId = _cursor.getColumnIndexOrThrow("subject_id");
          final java.util.List<ppl.com.absensy.model.AbsenceDetail> _result = new java.util.ArrayList<ppl.com.absensy.model.AbsenceDetail>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ppl.com.absensy.model.AbsenceDetail _item;
            _item = new ppl.com.absensy.model.AbsenceDetail();
            final java.lang.String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final java.util.Date _tmpAbsenceDate;
            final java.lang.Long _tmp;
            if (_cursor.isNull(_cursorIndexOfAbsenceDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfAbsenceDate);
            }
            _tmpAbsenceDate = ppl.com.absensy.repository.Converter.fromTimestamp(_tmp);
            _item.setAbsenceDate(_tmpAbsenceDate);
            final java.lang.String _tmpSubjectId;
            _tmpSubjectId = _cursor.getString(_cursorIndexOfSubjectId);
            _item.setSubjectId(_tmpSubjectId);
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
}
