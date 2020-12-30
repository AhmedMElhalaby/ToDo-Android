package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import AhmedMElhalaby_University.com.thingstodo.CallBack.InstallTwoStringCallback;
import AhmedMElhalaby_University.com.thingstodo.CallBack.OnItemClickListener;
import AhmedMElhalaby_University.com.thingstodo.Medules.Category;
import AhmedMElhalaby_University.com.thingstodo.Medules.Task;
import AhmedMElhalaby_University.com.thingstodo.R;
import AhmedMElhalaby_University.com.thingstodo.Ui.Adapters.AdapterCategory;
import AhmedMElhalaby_University.com.thingstodo.Ui.Adapters.AdapterTask;
import AhmedMElhalaby_University.com.thingstodo.Unit.AppDialogManager;

public class ListTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_bar_search;
    private TextView text_add_new_task;
    private EditText edittext_search;
    private RecyclerView recycler_view;
    private AdapterTask adapter;
    private List<Task> taskList;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Category category;
    FirebaseFirestore db;
    CollectionReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        initSetUp();
    }
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
    private void initSetUp() {
        img_bar_search = findViewById(R.id.img_bar_search);
        text_add_new_task = findViewById(R.id.text_add_new_task);
        img_bar_search.setVisibility(View.VISIBLE);
        img_bar_search.setOnClickListener(this::onClick);
        text_add_new_task.setOnClickListener(this::onClick);
        recycler_view = findViewById(R.id.recycler_view);
        String str_category = getIntent().getStringExtra("str_category");
        category = new Gson().fromJson(str_category, Category.class);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        ref = db.collection("Tasks::").document("List").collection("AllTasks");
        setRcyclerAdapter();
        edittext_search = findViewById(R.id.edittext_search);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bar_search:
                toggleSearch();
                break;
            case R.id.text_add_new_task:
                CreateNewTask();
                break;
        }
    }

    private void setRcyclerAdapter() {
        taskList = new ArrayList<>();
        ref.whereEqualTo("categoryId", category.getId())
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (documentSnapshots.size() > 0) {
                        List<Task> data = documentSnapshots.toObjects(Task.class);
                        if (data.size() > 0) {
                            taskList = data ;
                            adapter = new AdapterTask(ListTaskActivity.this, taskList, R.layout.item_task, new OnItemClickListener() {
                                @Override
                                public void onClick(View view, int postion) {
                                    AdapterTask.ItemSelect = postion;
                                    adapter.notifyDataSetChanged();
                                    GoToTaskDetialsActivity(taskList.get(postion));
                                }
                            });
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListTaskActivity.this);
                            recycler_view.setLayoutManager(linearLayoutManager);
                            recycler_view.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                    }
                }
            });
    }

    private void GoToTaskDetialsActivity(Task task) {
        String str_task = new Gson().toJson(task);
        Intent intent =new Intent(getApplicationContext() , TaskDitealsActivity.class);
        intent.putExtra("str_task",str_task);
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

    ListenerRegistration eventListenerTask;
    private void CreateNewTask() {
        AppDialogManager.showAddNewTaskDialog(ListTaskActivity.this, new InstallTwoStringCallback() {
            @Override
            public void onStatusDone(String title, String description) {
                if(!TextUtils.isEmpty(title)){
                    CreateTaskFireBase(title, description);
                    if (eventListenerTask != null) {
                        eventListenerTask.remove();
                    }
                }
            }
        });
    }
    private void CreateTaskFireBase(String title, String description) {
        String id = db.collection("Tasks::").document().getId();
        Task task = new Task(id ,title, description  , category.getId());
        ref.add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(ListTaskActivity.this, "Create Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListTaskActivity.this, "Failure " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (eventListenerTask != null) {
            eventListenerTask.remove();
        }
    }
}