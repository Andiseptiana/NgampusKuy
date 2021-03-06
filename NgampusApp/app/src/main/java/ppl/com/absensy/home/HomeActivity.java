package ppl.com.absensy.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import ppl.com.absensy.DialogConfirmation;
import ppl.com.absensy.Login;
import ppl.com.absensy.R;
import ppl.com.absensy.about.AboutActivity;
import ppl.com.absensy.absencedetails.AbsenceDetailsActivity;
import ppl.com.absensy.app.AbsensyApp;
import ppl.com.absensy.app.AbsensyAppComponent;
import ppl.com.absensy.base.BaseActivity;
import ppl.com.absensy.home.dialogabsence.DialogAbsence;
import ppl.com.absensy.home.dialogchooseoption.DialogChooseOption;
import ppl.com.absensy.home.dialogdelete.DialogDeleteSubject;
import ppl.com.absensy.home.recyclerviewsubject.RecyclerViewSubjectAdapter;
import ppl.com.absensy.home.recyclerviewsubject.RecyclerViewSubjectModule;
import ppl.com.absensy.model.Subject;
import ppl.com.absensy.setting.SettingActivity;
import ppl.com.absensy.submitsubject.SubmitSubjectActivity;

public class HomeActivity
        extends BaseActivity
        implements
        HomeContract.View, Button.OnClickListener, RecyclerViewSubjectAdapter.Listener,
        DialogChooseOption.Listener, DialogAbsence.Listener, DialogDeleteSubject.Listener,
        DialogConfirmation.Listener {

    public static final String LOG_TAG = HomeActivity.class.getSimpleName();

    String id, username;
    SharedPreferences sharedpreferences;

    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    private static final String DIALOG_CHOOSE_OPTION_TAG = "dialogChooseOption";
    private static final String DIALOG_ABSENCE_TAG = "dialogAbsence";
    private static final String DIALOG_DELETE_SUBJECT_TAG = "dialogDeleteSubject";

    private static final String DIALOG_CONFIRMATION_TITLE = "Yakin?";
    private static final String DIALOG_CONFIRMATION_BODY = "Kamu yakin mau hapus kuliah ini?";
    private static final String DIALOG_CONFIRMATION_TAG = "dialogConfirmation";

    @Inject
    HomeContract.Presenter presenter;
    @Inject
    RecyclerViewSubjectAdapter recyclerViewSubjectAdapter;

    private DialogFragment dialogAbsence;

    private Subject longClickedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        AbsensyAppComponent absensyAppComponent = ((AbsensyApp) getApplication()).getAbsensyAppComponent();

        DaggerHomeComponent.builder()
                .absensyAppComponent(absensyAppComponent)
                .homeModule(new HomeModule(this))
                .recyclerViewSubjectModule(new RecyclerViewSubjectModule(this))
                .build()
                .inject(this);

        FloatingActionButton btnAddSubject = findViewById(R.id.btnAddSubject);
        btnAddSubject.setOnClickListener(this);

        RecyclerView rvSubjects = findViewById(R.id.rvSubjects);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvSubjects.setAdapter(recyclerViewSubjectAdapter);

        setToolbarTitle(getString(R.string.home), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllSubjects();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.logout:
                logoutKan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showSubjectList(List<Subject> subjectList) {
        recyclerViewSubjectAdapter.updateData(subjectList);
    }

    @Override
    public void dismissDialogAbsence() {
        if (dialogAbsence != null)
            dialogAbsence.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddSubject:
                Intent intent = new Intent(this, SubmitSubjectActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onItemClick(Subject subject) {
        DialogFragment dialogChooseOption = DialogChooseOption.newInstance(subject);
        dialogChooseOption.show(getSupportFragmentManager(), DIALOG_CHOOSE_OPTION_TAG);
    }

    @Override
    public void onItemLongClick(Subject subject) {
        longClickedSubject = subject;
        DialogFragment dialogDelete = DialogDeleteSubject.newInstance();
        dialogDelete.show(getSupportFragmentManager(), DIALOG_DELETE_SUBJECT_TAG);
    }

    @Override
    public void onOptionClick(Subject subject, DialogChooseOption.Option option) {
        Intent intent;
        switch (option) {
            case VIEW_ABSENCE_DETAILS:
                intent = new Intent(this, AbsenceDetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.subject), subject);
                startActivity(intent);
                break;
            case EDIT:
                intent = new Intent(this, SubmitSubjectActivity.class);
                intent.putExtra(getResources().getString(R.string.subject), subject);
                startActivity(intent);
                break;
            case ABSENCE:
                dialogAbsence = DialogAbsence.newInstance(subject);
                dialogAbsence.show(getSupportFragmentManager(), DIALOG_ABSENCE_TAG);
                break;
            default:
                break;
        }
    }

    private void logoutKan() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Login.session_status, false);
        editor.putString(TAG_ID, null);
        editor.putString(TAG_USERNAME, null);
        editor.commit();
        Intent intent = new Intent(HomeActivity.this, Login.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onAbsence(Subject subject) {
        presenter.absenceSubject(subject);
    }

    @Override
    public void onDelete() {
        DialogFragment dialogConfirmation = DialogConfirmation.newInstance(DIALOG_CONFIRMATION_TITLE, DIALOG_CONFIRMATION_BODY);
        dialogConfirmation.show(getSupportFragmentManager(), DIALOG_CONFIRMATION_TAG);
    }

    @Override
    public void onConfirm() {
        presenter.deleteSubject(longClickedSubject);
    }
}
