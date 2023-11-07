package com.example.quizapp;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class QuestionsBank {
    Context mContext;

    public QuestionsBank(Context mContext) {
        this.mContext = mContext;
    }

    private static List<QuestionsList> javaQuestions(){
        final List<QuestionsList> questionsLists=new ArrayList<>();
        final QuestionsList question1=new QuestionsList("what is the size of int variable?","16bit","8 bit","32bit","64bit","32bit","");
        final QuestionsList question2=new QuestionsList("what is default value of Boolean Variable?","true","false","null","not defined","false","");
        final QuestionsList question3=new QuestionsList("what is the following is the default value of an instance variable?","Depends on type of variable","null","0","not assigned","Depends on type of variable","");
        final QuestionsList question4=new QuestionsList("which of the following is reserved keyword in java?","method","native","reference","array","native","");
        final QuestionsList question5=new QuestionsList("which of the following is NOT a keyword in java?","if","then","goto","while","then","");
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        return questionsLists;
    }
    private static List<QuestionsList> phpQuestions(){
        final List<QuestionsList> questionsLists=new ArrayList<>();
        final QuestionsList question1=new QuestionsList("what does PHP stands for?","professional home page","hypertext preprocessor","pretext hypertext processor","preprocessor home page","hypertext preprocessor","");
        final QuestionsList question2=new QuestionsList("who is father of PHP?","Rasmus Lerdorf","Willam Makepiece","Dark Kolkevi","List Barely","Rasmus Lerdorf","");
        final QuestionsList question3=new QuestionsList("PHP files have a default file extension of?",".html",".php",".xml",".json",".php","");
        final QuestionsList question4=new QuestionsList("A PHP script should start with ____ and end with ____?","< php >","<php />","< ? ? >","< ?php ?>","< ?php ?>","");
        final QuestionsList question5=new QuestionsList("which version of PHP introduced try/catch Exception?","PHP 4","PHP 5","PHP 6","PHP 5.3","PHP 5","");
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        return questionsLists;
    }
    private static List<QuestionsList> htmlQuestions(){
        final List<QuestionsList> questionsLists=new ArrayList<>();
        final QuestionsList question1=new QuestionsList("HTML stands for?","Hyper Text Markup Language","High Text Markup Language","Hyper Tabular Markup Language","None of the above","Hyper Text Markup Language","");
        final QuestionsList question2=new QuestionsList("From which tag descriptive list starts?","<LL>","<DD>","<DL>","<DS>","<DL>","");
        final QuestionsList question3=new QuestionsList("Correct HTML tag for largest heading is?","<head>","<large>","<h1>","<heading>","<h1>","");
        final QuestionsList question4=new QuestionsList("The attribute of <form> tag?","Method","Action","Both (a)&(b)","None of these","Both (a)&(b)","");
        final QuestionsList question5=new QuestionsList("Markup tags tell the web browser?","How to organise the page","How to display the page","How to display message box on page","None of these","How to display the page","");
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        return questionsLists;
    }
    private static List<QuestionsList> androidQuestions(){
        final List<QuestionsList> questionsLists=new ArrayList<>();
        final QuestionsList question1=new QuestionsList("Select a component which is NOT part of Android architecture?","Android Framework","Libraries","Linux Kernel","Android Document","Linux Kernel","");
        final QuestionsList question2=new QuestionsList("A _______ makes a specific set of the application data available to other application?","content provider","broadcast receivers","Intent","None of these","content provider","");
        final QuestionsList question3=new QuestionsList("which among these are NOT a part of Android's native libraries?","Webkit","Dalvik","OpenGL","SQLite","Dalvik","");
        final QuestionsList question4=new QuestionsList("During an Activity life-cycle, what is the first callback method invoked by the system?","onStop()","onStart()","onCreate()","onRestore()","onCreate()","");
        final QuestionsList question5=new QuestionsList("The requests from content provider class is handled by method?","onCreate","onSelect","contentResolver","onClick","contentResolver","");
        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        return questionsLists;
    }
    public List<QuestionsList> getQuestions(String selectedTopicName){
        switch(selectedTopicName){
            case "java":
                return javaQuestions();
            case "php":
                return phpQuestions();
            case "android":
                return androidQuestions();
            case "html":
                return htmlQuestions();
            default:
//                File file = new File("test_quiz.txt");
                File file = new File(mContext.getFilesDir(),"textFile.txt");
                TextQuiz textQuiz = new TextQuiz("Online Quiz",file,mContext);
//                TextQuiz textQuiz = new TextQuiz("example",mContext.getFilesDir().listFiles()[0],mContext);
//                textQuiz.setQuizName("example");
//                textQuiz.setFile(new File("test_quiz.txt"));
                return textQuiz.getQuizList();
        }
    }
}