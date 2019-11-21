package com.example.fa19_cs242_research_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fa19_cs242_research_project.LoginPage.Login;
import com.example.fa19_cs242_research_project.ProfilePage.ProfilePage;
import com.example.fa19_cs242_research_project.QuestionPage.QuestionActivity;
import com.example.fa19_cs242_research_project.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GameSettingsPage extends AppCompatActivity {

    private static final String DIFFICULTY_CHOICE_TEXT = "Choose a Difficulty Level!";

    private static final String GENERAL_CATEGORY = "General Knowledge";
    private static final String ENTERTAINMENT_CATEGORY = "Entertainment";
    private static final String VIDEO_GAMES_CATEGORY = "Video Games";

    private static final String EASY_DIFFICULTY = "Easy";
    private static final String MEDIUM_DIFFICULTY = "Medium";
    private static final String HARD_DIFFICULTY = "Hard";

    private static boolean isDifficulty = false;
    private static String selectedCategory;

    private TextView choiceText;

    private Button categoryOneText;
    private Button categoryTwoText;
    private Button categoryThreeText;

    private ImageButton profileButton;
    private Button signOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings_page);

        choiceText = (TextView) findViewById(R.id.myQuestion);
        categoryOneText = (Button) findViewById(R.id.option_one_settings_button);
        categoryTwoText = (Button) findViewById(R.id.option_two_settings_button);
        categoryThreeText = (Button) findViewById(R.id.option_three_settings_button);

        selectedCategory = getIntent().getStringExtra("category");

        if(selectedCategory != null) {
            choiceText.setText(DIFFICULTY_CHOICE_TEXT);
            categoryOneText.setText(EASY_DIFFICULTY);
            categoryTwoText.setText(MEDIUM_DIFFICULTY);
            categoryThreeText.setText(HARD_DIFFICULTY);
            isDifficulty = true;
        }
        else {
            categoryOneText.setText(GENERAL_CATEGORY);
            categoryTwoText.setText(ENTERTAINMENT_CATEGORY);
            categoryThreeText.setText(VIDEO_GAMES_CATEGORY);
            isDifficulty = false;
        }

        setUpOptionButtons();
    }

    /**
     * Set up attributes for each button in Activity
     */
    private void setUpOptionButtons() {
        setUpOptionOneButton();
        setUpOptionTwoButton();
        setUpOptionThreeButton();
        setUpProfileButton();
        setUpSignOutButton();
    }

    private void setUpOptionOneButton() {
        final String text = (String) categoryOneText.getText();
        categoryOneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity(text);
            }
        });
    }

    private void setUpOptionTwoButton() {
        final String text = (String) categoryTwoText.getText();
        categoryTwoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity(text);
            }
        });
    }

    private void setUpOptionThreeButton() {
        final String text = (String) categoryThreeText.getText();
        categoryThreeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity(text);
            }
        });
    }

    /**
     * sign out of both google and facebook if user clicks sign out button
     */
    private void setUpSignOutButton() {
        signOutButton = (Button) findViewById(R.id.sign_out_button_question);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    /**
     * sign out of both google and facebook if user clicks sign out button
     */
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

    /**
     * If user clicks profile button, navigate to Profile page information
     */
    private void startProfilePageActivity() {
        Intent profileIntent = new Intent(this, ProfilePage.class);
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
     * click
     */
    private void startNextActivity(String input) {
        if(isDifficulty) {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("category", selectedCategory);
            intent.putExtra("difficulty", input);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, GameSettingsPage.class);
            intent.putExtra("category", input);
            startActivity(intent);
        }
    }
}
