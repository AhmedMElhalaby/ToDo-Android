package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import AhmedMElhalaby_University.com.thingstodo.CallBack.InstallTwoStringCallback;
import AhmedMElhalaby_University.com.thingstodo.CallBack.OnItemClickListener;
import AhmedMElhalaby_University.com.thingstodo.Medules.Category;
import AhmedMElhalaby_University.com.thingstodo.R;
import AhmedMElhalaby_University.com.thingstodo.Ui.Adapters.AdapterCategory;
import AhmedMElhalaby_University.com.thingstodo.Unit.AppDialogManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_add_new_category;
    private EditText edittext_search;
    private RecyclerView recycler_view;
    private AdapterCategory adapter;
    private List<Category> categories;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    FirebaseFirestore db;
    CollectionReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        ref = db.collection("Categories::").document("List").collection("AllCategories");
        text_add_new_category = findViewById(R.id.text_add_new_category);
        edittext_search = findViewById(R.id.edittext_search);
        recycler_view = findViewById(R.id.recycler_view);
        setRcyclerAdapter();
        text_add_new_category.setOnClickListener(this::onClick);
    }

    private void setRcyclerAdapter() {
        categories = new ArrayList<>();
        ref.whereEqualTo("userId", currentUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots.size() > 0) {
                            List<Category> data = documentSnapshots.toObjects(Category.class);
                            if (data.size() > 0) {
                                categories = data ;
                                adapter = new AdapterCategory(HomeActivity.this, categories, R.layout.item_category, new OnItemClickListener() {
                                    @Override
                                    public void onClick(View view, int postion) {
                                        AdapterCategory.ItemSelect = postion;
                                        adapter.notifyDataSetChanged();
                                        GoToTaskListActivity(categories.get(postion));

                                    }
                                });
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                                recycler_view.setLayoutManager(linearLayoutManager);
                                recycler_view.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } else {

                        }

                    }
                });

    }

    private void GoToTaskListActivity(Category category) {
        String str_category = new Gson().toJson(category);
        Intent intent = new Intent(getApplicationContext() ,ListTaskActivity.class);
        intent.putExtra("str_category", str_category);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_add_new_category:
                CreateNewCategory();
                break;
            case R.id.layout_toolbar_menu:
                FirebaseAuth.getInstance().signOut();
                updateUI(null);
                break;
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (eventListenerCategory != null) {
            eventListenerCategory.remove();
        }
    }


    ListenerRegistration eventListenerCategory;
    private void CreateNewCategory() {
        AppDialogManager.showAddNewCategoryDialog(HomeActivity.this, new InstallTwoStringCallback() {
            @Override
            public void onStatusDone(String title, String color) {
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(color)){
                    if (eventListenerCategory != null) {
                        eventListenerCategory.remove();
                    }
                     eventListenerCategory = ref.whereEqualTo("name", title).whereEqualTo("userId", currentUser.getUid())
                         .addSnapshotListener(new EventListener<QuerySnapshot>() {
                             @Override
                             public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                 if (documentSnapshots.size() > 0) {
                                     Toast.makeText(HomeActivity.this, getResources().getText(R.string.Cateogry_exists), Toast.LENGTH_SHORT).show();
                                 } else {
                                     CreateCateogryFireBase(title, color);

                                 }

                                 eventListenerCategory.remove();
                             }
                         });
                }
            }
        });
    }

    private void CreateCateogryFireBase(String title, String color) {
        String id = db.collection("Categories::").document().getId();
        String UserId = currentUser.getUid() ;
        Category category = new Category(id ,title, color  , UserId);
        ref.add(category).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(HomeActivity.this, "Create Success", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Failure " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}