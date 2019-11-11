package com.example.fa19_cs242_research_project.QuestionPage;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.fa19_cs242_research_project.QuestionPage.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

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

    /**
     * Populate array with question objects
     * @param response
     */
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

    /**
     * retrieve random trivia question to display
     * @return
     */
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
