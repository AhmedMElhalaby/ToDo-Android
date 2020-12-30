package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import AhmedMElhalaby_University.com.thingstodo.Medules.User;
import AhmedMElhalaby_University.com.thingstodo.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_login;
    private EditText edittext_name, edittext_email, edittext_password;
    private Button btn_action;
    private FirebaseAuth mAuth;

    FirebaseFirestore db;
    CollectionReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        initSetUp();
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }
    private void initSetUp() {
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        ref = db.collection("Users::").document("List").collection("AllUsers");


        text_login = findViewById(R.id.text_login);
        edittext_name = findViewById(R.id.edittext_name);
        edittext_email = findViewById(R.id.edittext_email);
        edittext_password = findViewById(R.id.edittext_password);
        btn_action = findViewById(R.id.btn_action);

        btn_action.setOnClickListener(this::onClick);
        text_login.setOnClickListener(this::onClick);
    }

    ListenerRegistration eventListener;

    private void AddFireBaseNewUser(User user) {


        String id = db.collection("Users::").document().getId();
        user.setId(id);
        ref.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity.this, "Create Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Failure " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action:
                CreateNewAccount();
                break;

            case R.id.text_login:
                GoToLoginActivty();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void CreateNewAccount() {
        String name = edittext_name.getText().toString().trim();
        String password = edittext_password.getText().toString().trim();
        String email = edittext_email.getText().toString().trim();
        edittext_name.clearFocus();
        edittext_email.clearFocus();
        edittext_password.clearFocus();
        edittext_password.setError(null);
        edittext_email.setError(null);
        edittext_name.setError(null);
        if (TextUtils.isEmpty(name)) {
            edittext_name.setError(getResources().getString(R.string.filed_required));
            edittext_name.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            edittext_password.setError(getResources().getString(R.string.filed_required));
            edittext_password.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            edittext_email.setError(getResources().getString(R.string.filed_required));
            edittext_email.requestFocus();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edittext_email.setError(getResources().getString(R.string.invalid_Email_address));
            edittext_email.requestFocus();
        } else {
            User app_user = new User(name, email, password);
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseAuth.getInstance().signOut();
                            AddFireBaseNewUser(app_user);
//                            updateUI(user);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        }
    }

    private void GoToLoginActivty() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}