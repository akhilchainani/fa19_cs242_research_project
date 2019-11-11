package com.example.fa19_cs242_research_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class QuestionActivity extends AppCompatActivity {

    private TextView questionText;
    private TextView highScoreText;

    private Button optionOneButton;
    private Button optionTwoButton;
    private Button optionThreeButton;
    private Button optionFourButton;
    private Button skipButton;
    private Button signOutButton;

    private ImageButton profileButton;

    private GoogleSignInClient mGoogleSignInClient;

    private static int currentAnswer = -1;
    private QuestionGenerator generator;

    private static int highScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionText = (TextView) findViewById(R.id.myQuestion);
        highScoreText = (TextView) findViewById(R.id.high_score);
        setUpOptionButtons();

        generator = new QuestionGenerator(this);
        updateWithNewQuestion();
    }

    /**
     * Set up attributes for each button in Activity
     */
    private void setUpOptionButtons() {
        setUpOptionOneButton();
        setUpOptionTwoButton();
        setUpOptionThreeButton();
        setUpOptionFourButton();
        setUpProfileButton();
        setUpSignOutButton();
    }

    private void setUpSignOutButton() {
        signOutButton = (Button) findViewById(R.id.sign_out_button_question);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        //logout of google
        GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        return;
                    }
                });

        //logout of facebook
        LoginManager.getInstance().logOut();

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void setUpOptionOneButton() {
        optionOneButton = (Button) findViewById(R.id.option_one_button);
        optionOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndUpdateData(0, getCurrentAnswer());
            }
        });
    }

    private void setUpOptionTwoButton() {
        optionTwoButton = (Button) findViewById(R.id.option_two_button);
        optionTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndUpdateData(1, getCurrentAnswer());
            }
        });
    }

    private void setUpOptionThreeButton() {
        optionThreeButton = (Button) findViewById(R.id.option_three_button);
        optionThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndUpdateData(2, getCurrentAnswer());
            }
        });
    }

    private void setUpOptionFourButton() {
        optionFourButton = (Button) findViewById(R.id.option_four_button);
        optionFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerAndUpdateData(3, getCurrentAnswer());
            }
        });
    }

    private void startProfilePageActivity() {
        Intent profileIntent = new Intent(this, ProfilePage.class);
        profileIntent.putExtra("highscore", highScore);
        startActivity(profileIntent);
    }

    /**
     * Initialize Profile button attributes and send to Profile
     * page once clicked
     */
    private void setUpProfileButton() {
        profileButton = (ImageButton) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfilePageActivity();
            }
        });
    }

    /**
     * retrieve new question from Question generator
     * and update the views with question info
     */
    private void updateWithNewQuestion() {
        final Question question = generator.getNextQuestion();
        System.out.println("After sleep");
        if(question != null) {
            System.out.println(question.getQuestion());

            //set the question text
            questionText.setText(question.getQuestion());

            //set options text
            optionOneButton.setText(question.getOptionOne());
            optionTwoButton.setText(question.getOptionTwo());
            optionThreeButton.setText(question.getOptionThree());
            optionFourButton.setText(question.getOptionFour());

            //set the current answer for checkAnswer() function
            setCurrentAnswer(question.getAnswer());
        }
    }

    /**
     * compares the input choice and answer to determine
     * if selected choice is correct, and update HighScore
     * @param choice
     * @param answer
     */
    private void checkAnswerAndUpdateData(int choice, int answer) {
        String popupText = "";
        if(choice == answer) {
            popupText = "Correct!";
            highScore++;
        }
        else {
            popupText = "Incorrect :(";
            highScore = 0;
        }
        Toast.makeText(this, popupText, Toast.LENGTH_SHORT).show();
        highScoreText.setText("High Score: " + highScore);
        updateWithNewQuestion();
    }

    public int getCurrentAnswer() {
        return this.currentAnswer;
    }

    public void setCurrentAnswer(int currentAnswer) {
        this.currentAnswer = currentAnswer;
    }
}
