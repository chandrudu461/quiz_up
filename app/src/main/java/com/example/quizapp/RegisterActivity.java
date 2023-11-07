package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEditText,passwordEditText,confirmPasswordEditText,nameEditText;
    Button submit;
    String role = "",userId = "";
    FirebaseDatabase database;
    DatabaseReference rootReference,teacherReference,studentReference;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
//        nameEditText = findViewById(R.id.name);

        String[] values = {"Teacher", "Student"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                role = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        submit = findViewById(R.id.signup);
        submit.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        rootReference = database.getReference().child("Users");
        teacherReference = database.getReference().child("Teachers");
        studentReference = database.getReference().child("Students");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null) {
            userId = firebaseUser.getUid();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signup:
            register();
            break;
        }
    }

    void register(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
//        String name = nameEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (email.isEmpty()) {
            emailEditText.setError("email is required");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter valid email");
            emailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("password is required");
            passwordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Minimum password length is 6 characters!");
            passwordEditText.requestFocus();
            return;
        }

        if(!confirmPassword.equals(password)){
            passwordEditText.setError("Password is not matching!!!");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this,"Sign UP completed...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Sign up failed. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
//        user.setName(name);

        if(user.getRole() == "Teacher"){
            teacherReference.child(userId).setValue(user);
        }
        else{
            studentReference.child(userId).setValue(user);
        }
        rootReference.child(userId).setValue(user);
    }
}