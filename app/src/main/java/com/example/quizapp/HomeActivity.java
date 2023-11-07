package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HomeActivity extends AppCompatActivity {

    private String selectedTopicName= "";
    Button onlineQuiz;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    StorageReference storageRef,fileRef;
    String userId;
    TextView txt_to_quiz;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        storageRef = FirebaseStorage.getInstance().getReference();
//        fileRef = storageRef.child("textFiles").child(userId).child("textFile.txt");
        fileRef = storageRef.child("textFiles").child("textFile.txt");
        onlineQuiz = findViewById(R.id.onlineQuiz);

        onlineQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,QuizActivity.class);
                intent.putExtra("selectedTopic","Online Quiz");
                startActivity(intent);
            }
        });

        final LinearLayout java=findViewById( R.id.javaLayout);
        final LinearLayout php=findViewById( R.id.phpLayout);
        final LinearLayout html=findViewById( R.id.htmlLayout);
        final LinearLayout android=findViewById( R.id.androidLayout);

//        txt_to_quiz = findViewById(R.id.chooseTopic);

        final Button startBtn=findViewById(R.id.startQuiz);
        final TextView download = findViewById(R.id.chooseTopic);
        final TextView modules = findViewById(R.id.modules);
        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTopicName="java";
                java.setBackgroundResource(R.drawable.round_back_white_stroke10);
                php.setBackgroundResource(R.drawable.round_back_white10);
                html.setBackgroundResource(R.drawable.round_back_white10);
                android.setBackgroundResource(R.drawable.round_back_white10);
            }
        });
        php.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTopicName="php";
                php.setBackgroundResource(R.drawable.round_back_white_stroke10);
                java.setBackgroundResource(R.drawable.round_back_white10);
                html.setBackgroundResource(R.drawable.round_back_white10);
                android.setBackgroundResource(R.drawable.round_back_white10);
            }
        });
        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTopicName="html";
                html.setBackgroundResource(R.drawable.round_back_white_stroke10);
                php.setBackgroundResource(R.drawable.round_back_white10);
                java.setBackgroundResource(R.drawable.round_back_white10);
                android.setBackgroundResource(R.drawable.round_back_white10);
            }
        });
        android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTopicName="android";
                android.setBackgroundResource(R.drawable.round_back_white_stroke10);
                php.setBackgroundResource(R.drawable.round_back_white10);
                html.setBackgroundResource(R.drawable.round_back_white10);
                java.setBackgroundResource(R.drawable.round_back_white10);
            }
        });

//        txt_to_quiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(HomeActivity.this,QuizActivity.class);
//                intent.putExtra("selectedTopic","other");
//                startActivity(intent);
//            }
//        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuizFile(fileRef);
            }
        });

        modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ModulesActivity.class));
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTopicName.isEmpty())
                {
                    Toast.makeText(HomeActivity.this,"Please select the topic",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(HomeActivity.this,QuizActivity.class);
                    intent.putExtra("selectedTopic",selectedTopicName);
                    startActivity(intent);
                }
            }
        });

    }

    private void getQuizFile(StorageReference fileRef) {

        File localFile = new File(getFilesDir().toString(), "textFile.txt");
//        File localFile = new File(Environment.getExternalStorageDirectory(), "textFile.txt");

        fileRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // File downloaded successfully
                        // Move the file to the assets folder

                        Log.d("TAG", "File contents: " + localFile.getAbsolutePath().toString());
                        Toast.makeText(HomeActivity.this,localFile.getAbsolutePath().toString(),Toast.LENGTH_SHORT).show();

//                        moveFileToAssets(localFile);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // File download failed
                        // Handle the failure scenario here
                        Toast.makeText(HomeActivity.this,"File is unable download from Firebase.",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void moveFileToAssets(File localFile) {
        try {
            InputStream in = new FileInputStream(localFile);
            OutputStream out = getAssets().openFd("test_quiz3.txt").createOutputStream();

            Toast.makeText(HomeActivity.this,"File is moved to assets.",Toast.LENGTH_SHORT).show();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            // File moved successfully to the assets folder
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(HomeActivity.this,"error generated to move to assets",Toast.LENGTH_SHORT).show();
            // Handle the failure scenario here
        }
//        try {
//            InputStream inputStream = getAssets().open("test_quiz2.txt");  // Replace "yourfile.txt" with the actual file name and extension
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            StringBuilder stringBuilder = new StringBuilder();
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//
//            String fileContents = stringBuilder.toString();
//
//            // Use the file contents as needed
//            Log.d("TAG", "File contents: " + fileContents);
//            Toast.makeText(HomeActivity.this,"See file contents in log",Toast.LENGTH_SHORT).show();
//
//            // Close the streams
//            reader.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle the failure scenario here
//        }

    }

}