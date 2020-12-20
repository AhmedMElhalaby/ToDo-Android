package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import AhmedMElhalaby_University.com.thingstodo.CallBack.OnItemClickListener;
import AhmedMElhalaby_University.com.thingstodo.Medules.Task;
import AhmedMElhalaby_University.com.thingstodo.R;
import AhmedMElhalaby_University.com.thingstodo.Ui.Adapters.AdapterTask;

public class ListTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_bar_search;
    private EditText edittext_search;
    private RecyclerView recycler_view;
    private AdapterTask adapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        initSetUp();
    }

    private void initSetUp() {
        img_bar_search = findViewById(R.id.img_bar_search);
        img_bar_search.setVisibility(View.VISIBLE);
        img_bar_search.setOnClickListener(this::onClick);
        recycler_view = findViewById(R.id.recycler_view);
        setRcyclerAdapter();

        edittext_search = findViewById(R.id.edittext_search);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bar_search:
                toggleSearch();
                break;
        }

    }

    private void setRcyclerAdapter() {
        taskList = new ArrayList<>();
        taskList.add(new Task(1, "Home", "Red"));
        taskList.add(new Task(2, "Work", "Red"));
        taskList.add(new Task(3, "Personal", "Red"));
        taskList.add(new Task(4, "Meet", "Red"));
        adapter = new AdapterTask(ListTaskActivity.this, taskList, R.layout.item_task, new OnItemClickListener() {
            @Override
            public void onClick(View view, int postion) {
                taskList.get(postion).setSelect(true);

                adapter.notifyDataSetChanged();
                GoToTaskDetialsActivity(taskList.get(postion));

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(adapter);
    }

    private void GoToTaskDetialsActivity(Task task) {
        String str_task = new Gson().toJson(task);
        Intent intent =new Intent(getApplicationContext() , TaskDitealsActivity.class);
        intent.putExtra("tast_str",str_task);
        startActivity(intent);
    }

    private void toggleSearch() {
        if (TextUtils.equals("0", edittext_search.getTag().toString())) {
            edittext_search.setVisibility(View.VISIBLE);
            img_bar_search.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.DST_IN);
            edittext_search.setTag("1");
        } else {
            edittext_search.setVisibility(View.GONE);
            img_bar_search.setColorFilter(getResources().getColor(R.color.offwhite), PorterDuff.Mode.DST_IN);
            edittext_search.setTag("0");
        }
    }
}