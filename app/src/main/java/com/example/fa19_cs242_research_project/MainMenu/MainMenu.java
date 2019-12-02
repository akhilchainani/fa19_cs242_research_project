package com.example.fa19_cs242_research_project.MainMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fa19_cs242_research_project.GameSettingsPage;
import com.example.fa19_cs242_research_project.LoginPage.Login;
import com.example.fa19_cs242_research_project.ProfilePage.ProfilePage;
import com.example.fa19_cs242_research_project.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainMenu extends AppCompatActivity {

    private Button signOutButton;
    private Button newGameButton;
    private ImageButton profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpProfileButton();
        setUpSignOutButton();
        setUpNewGameButton();
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
        profileIntent.putExtra("highscore", 0);
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
     * set up new game button
     */
    private void setUpNewGameButton() {
        newGameButton = (Button) findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameSettingsActivity();
            }
        });
    }

    /**
     * once login is successful, start game settings activity
     */
    private void startGameSettingsActivity() {
        Intent questionIntent = new Intent(this, GameSettingsPage.class);
        startActivity(questionIntent);
    }
}
