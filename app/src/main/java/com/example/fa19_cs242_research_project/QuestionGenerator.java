package com.example.fa19_cs242_research_project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class QuestionGenerator {
    private static final String apiUrl = "https://opentdb.com/api.php?amount=10&category=9&type=multiple";
    private RequestQueue requestQueue;
    private ArrayList<Question> questionQueue;

    public QuestionGenerator(Context context) {
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(context);
        questionQueue = new ArrayList<>();

        try {
            InputStream stream = context.getAssets().open("question_bank.json");
            int size = stream.available();

            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();

            populateJson(new String(bytes));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void populateJson(String response) {
        try {
            System.out.println(response.toString());
            JSONArray jsonArray = new JSONObject(response).getJSONArray("results");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                String question = json.getString("question");
                String correctAnswer = json.getString("correct_answer");

                JSONArray incorrectAnswers = json.getJSONArray("incorrect_answers");
                String incorrectAnswerOne = incorrectAnswers.getString(0);
                String incorrectAnswerTwo = incorrectAnswers.getString(1);
                String incorrectAnswerThree = incorrectAnswers.getString(2);

                Question questionObject = new Question(question, 0,
                        correctAnswer,
                        incorrectAnswerOne,
                        incorrectAnswerTwo,
                        incorrectAnswerThree);

                System.out.println(questionObject.getQuestion());
                questionQueue.add(questionObject);
                System.out.println(i + " Added");
            }
        }
        catch (JSONException e) {
            // catch for the JSON parsing error
            e.printStackTrace();
        }
    }

    public synchronized Question getNextQuestion() {
        //this.fetchNewQuestion();
        Random rand = new Random();
        int questionNum = rand.nextInt(50);
        return questionQueue.get(questionNum);
    }

    public boolean hasNextQuestion() {
        return !questionQueue.isEmpty();
    }
}
