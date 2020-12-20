package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import AhmedMElhalaby_University.com.thingstodo.Medules.Task;
import AhmedMElhalaby_University.com.thingstodo.R;
import AhmedMElhalaby_University.com.thingstodo.Unit.RootManager;

public class TaskDitealsActivity extends AppCompatActivity {
    private ImageView img_bar_search;
    private TextView text_bar_action;
    private EditText edittext_title, edittext_description;
    private TextView text_category, text_date;

    Task task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_diteals);


        initSetup();
    }

    private void initSetup() {
        text_bar_action = findViewById(R.id.text_bar_action);
        text_bar_action.setVisibility(View.VISIBLE);
        img_bar_search = findViewById(R.id.img_bar_search);
        img_bar_search.setVisibility(View.GONE);


        edittext_title = findViewById(R.id.edittext_title);
        edittext_description = findViewById(R.id.edittext_description);
        text_category = findViewById(R.id.text_category);
        text_date = findViewById(R.id.text_date);


        String tast_str = getIntent().getStringExtra("tast_str");
        if (!TextUtils.isEmpty(tast_str)) {
            task = new Gson().fromJson(tast_str, Task.class);
            if (task != null) {
                edittext_title.setText(task.getTitle());
                edittext_description.setText(task.getDescription());
                if (task.getCreateDate() != null)
                    text_date.setText(task.getCreateDate());
                if (task.getCategory() != null)
                    RootManager.SetColorBackgroundCateogry(getApplicationContext(), text_category, task.getCategory());

                text_bar_action.setText(getResources().getText(R.string.edit));
            } else {
                text_bar_action.setText(getResources().getText(R.string.Create));
            }
        } else {
            text_bar_action.setText(getResources().getText(R.string.Create));

        }
        text_bar_action.setOnClickListener(view -> {
            if (task != null) {
                if (TextUtils.equals(text_bar_action.getText().toString(), getResources().getString(R.string.edit))) {
                    text_bar_action.setText(getResources().getText(R.string.save));
                } else {
                    text_bar_action.setText(getResources().getText(R.string.edit));
                }
                toggleEnableMode();
            } else {
                CreateNewTask();
            }
        });


        toggleEnableMode();
    }

    private void CreateNewTask() {
    }

    private void toggleEnableMode() {
        if (TextUtils.equals(getResources().getString(R.string.edit), text_bar_action.getText().toString())) {
            DisEnableMode();
        } else if (TextUtils.equals(getResources().getString(R.string.Create), text_bar_action.getText().toString())) {
            EnableMode();
        } else if (TextUtils.equals(getResources().getString(R.string.save), text_bar_action.getText().toString())) {
            EnableMode();
        }
    }

    private void DisEnableMode() {
        edittext_title.setEnabled(false);
        edittext_description.setEnabled(false);
        text_category.setVisibility(View.VISIBLE);
    }

    private void EnableMode() {
        edittext_title.setEnabled(true);
        edittext_description.setEnabled(true);
        text_category.setVisibility(View.GONE);
    }
}