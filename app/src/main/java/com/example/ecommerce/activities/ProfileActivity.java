package com.example.ecommerce.activities;

// ProfileActivity.java

// ProfileActivity.java

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerce.R;
import com.example.ecommerce.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Thêm nút Back trên Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);

        if (currentUser != null) {
            DocumentReference userRef = db.collection("users").document(currentUser.getUid());

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = document.toObject(User.class);
                        updateUI(user);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            });
        }
    }

    private void updateUI(User user) {
        if (user != null) {
            nameTextView.setText("Name: " + user.getName());
            emailTextView.setText("Email: " + user.getEmail());
//            phoneTextView.setText("Phone: " + user.getPhoneNumber());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}