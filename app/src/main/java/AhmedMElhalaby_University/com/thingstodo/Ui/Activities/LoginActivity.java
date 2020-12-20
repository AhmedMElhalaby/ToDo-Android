package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import AhmedMElhalaby_University.com.thingstodo.Medules.User;
import AhmedMElhalaby_University.com.thingstodo.R;
import AhmedMElhalaby_University.com.thingstodo.Unit.AppUserPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_register;
    private EditText edittext_email, edittext_password;
    private Button btn_action;

    FirebaseFirestore db;
    CollectionReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initSetUp();
    }

    private void initSetUp() {
        text_register = findViewById(R.id.text_register);
        edittext_email = findViewById(R.id.edittext_email);
        edittext_password = findViewById(R.id.edittext_password);
        btn_action = findViewById(R.id.btn_action);
        db = FirebaseFirestore.getInstance();
        ref = db.collection("Users::").document("List").collection("AllUsers");

        btn_action.setOnClickListener(this::onClick);
        text_register.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action:
                Login();
                break;

            case R.id.text_register:
                GoToRegisterActivty();
                break;
        }
    }

    ListenerRegistration eventListener;

    private void Login() {


        String password = edittext_password.getText().toString().trim();
        String email = edittext_email.getText().toString().trim();
        edittext_email.clearFocus();
        edittext_password.clearFocus();
        edittext_password.setError(null);
        edittext_email.setError(null);
        if (TextUtils.isEmpty(password)) {
            edittext_password.setError(getResources().getString(R.string.filed_required));
            edittext_password.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            edittext_email.setError(getResources().getString(R.string.filed_required));
            edittext_email.requestFocus();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edittext_email.setError(getResources().getString(R.string.invalid_Email_address));
            edittext_email.requestFocus();
        } else {

            if (eventListener != null) {
                eventListener.remove();
            }
            User user = new User(email, password);

            eventListener = ref.whereEqualTo("email", user.getEmail()).whereEqualTo("password", user.getPassword())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            if (documentSnapshots.size() > 0) {
                                Toast.makeText(LoginActivity.this, getResources().getText(R.string.mail_exists), Toast.LENGTH_SHORT).show();

                                List<User> data = documentSnapshots.toObjects(User.class);
                                if (data.size() > 0) {
                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                        String id = documentChange.getDocument().getString("id");
                                        Toast.makeText(LoginActivity.this, " Welcome " + id, Toast.LENGTH_SHORT).show();

                                    }
                                    AppUserPreferences.SetUser(LoginActivity.this, data.get(0));
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, getResources().getText(R.string.mail_not_exists), Toast.LENGTH_SHORT).show();

                            }

                            eventListener.remove();
                        }
                    });

        }


    }


    private void GoToRegisterActivty() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (eventListener != null) {
            eventListener.remove();
        }
    }
}