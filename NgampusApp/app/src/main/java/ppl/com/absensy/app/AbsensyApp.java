package ppl.com.absensy.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import ppl.com.absensy.reminder.ClassReminderModule;
import ppl.com.absensy.repository.RepositoryModule;

public class AbsensyApp extends Application {

    private AbsensyAppComponent absensyAppComponent;

    public static final String TAG = AbsensyApp.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AbsensyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        absensyAppComponent = DaggerAbsensyAppComponent.builder()
                .absensyAppModule(new AbsensyAppModule(this))
                .repositoryModule(new RepositoryModule())
                .classReminderModule(new ClassReminderModule())
                .build();
    }

    public AbsensyAppComponent getAbsensyAppComponent() {
        return absensyAppComponent;
    }

    public static synchronized AbsensyApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
