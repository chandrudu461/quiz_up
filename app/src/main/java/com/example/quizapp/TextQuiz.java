package com.example.quizapp;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TextQuiz {
    String quizName;
    ArrayList<QuestionsList> quizList;
    private Context mContext;
    File file;

    public TextQuiz(){}

    public TextQuiz(String quizName, File file,Context mContext) {
        this.quizName = quizName;
        this.file = file;
        this.mContext = mContext;
//        this.quizList = readLine(file.getPath());
        this.quizList = readQuiz(file.getPath());
    }

    public ArrayList<QuestionsList> readQuiz(String path){
        ArrayList<QuestionsList> questionsList = new ArrayList<>();
        File file = new File(mContext.getFilesDir(), "textFile.txt");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                if(fields.length == 6){
                    QuestionsList quiz = new QuestionsList();
                    quiz.setQuestion(fields[0]);
                    quiz.setOption1(fields[1]);
                    quiz.setOption2(fields[2]);
                    quiz.setOption3(fields[3]);
                    quiz.setOption4(fields[4]);
                    quiz.setAnswer(fields[5]);
                    questionsList.add(quiz);
                }
            }

            bufferedReader.close();

            String fileContents = stringBuilder.toString();

            // Parse or deserialize the file contents to create an object
            // Example: If the file contains JSON data
            // JSONObject jsonObject = new JSONObject(fileContents);
            // YourObject yourObject = new YourObject(jsonObject);

            // Use the created object as needed

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the failure scenario here
        }

        return questionsList;
    }

    public ArrayList<QuestionsList> readLine(String path) {
        ArrayList<QuestionsList> questionsList = new ArrayList<>();
        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while((line = reader.readLine()) != null){
                String[] fields = line.split(",");
                if(fields.length == 6){
                    QuestionsList quiz = new QuestionsList();
                    quiz.setQuestion(fields[0]);
                    quiz.setOption1(fields[1]);
                    quiz.setOption2(fields[2]);
                    quiz.setOption3(fields[3]);
                    quiz.setOption4(fields[4]);
                    quiz.setAnswer(fields[5]);
                    questionsList.add(quiz);
                }
            }
//                    mLines.add(line);

            } catch (IOException e) {
                e.printStackTrace();
            }
        return questionsList;
    }

    @NonNull
    public ArrayList<QuestionsList> readQuizFile() {
        ArrayList<QuestionsList> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            list = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("");

                if (data.length == 6) {
                    String question = data[0];
                    String option1 = data[1];
                    String option2 = data[2];
                    String option3 = data[3];
                    String option4 = data[4];
                    String answer = data[5];

                    QuestionsList quiz = new QuestionsList();
                    quiz.setQuestion(question);
                    quiz.setOption1(option1);
                    quiz.setOption2(option2);
                    quiz.setOption3(option3);
                    quiz.setOption4(option4);
                    quiz.setAnswer(answer);
                    list.add(quiz);
                }
            }
            bufferedReader.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public ArrayList<QuestionsList> getQuizList() {
        return quizList;
    }

    public void setQuizList(ArrayList<QuestionsList> quizList) {
        this.quizList = quizList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
