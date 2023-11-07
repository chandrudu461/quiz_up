
package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TeacherActivity extends AppCompatActivity {

    Button uploadQuiz;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    StorageReference storageRef;
    String userId;
    int PICK_FILE_REQUEST_CODE = 100;
    // Create a reference to the file you want to upload
    StorageReference fileRef;
    // Get the file URI or path
    Uri textFireUri; // File URI or path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        uploadQuiz = findViewById(R.id.upload_quiz);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();


        storageRef = FirebaseStorage.getInstance().getReference();
        fileRef = storageRef.child("quizes").child(userId).child("test_quiz.txt");

        uploadQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTextFile();
            }
        });
    }

    private void selectTextFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("text/plain"); // Only show .txt files
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                textFireUri = data.getData();
                // Proceed with uploading the file
                uploadFileToFirebase(textFireUri);
            }
        }
    }
    private void uploadFileToFirebase(Uri fileUri) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Create a reference to the file you want to upload
//        StorageReference fileRef = storageRef.child("textFiles").child(userId).child("textFile.txt");
        StorageReference fileRef = storageRef.child("textFiles").child("textFile.txt");
//        StorageReference fileRef = storageRef.child("textFile.txt");

        // Upload the file
        fileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // File uploaded successfully
                        // Handle the success scenario here
                        Toast.makeText(TeacherActivity.this,"The file is uploaded..",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // File upload failed
                        // Handle the failure scenario here
                        Toast.makeText(TeacherActivity.this,"Failed to upload",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}