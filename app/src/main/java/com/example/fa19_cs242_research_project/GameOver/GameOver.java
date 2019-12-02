package com.example.fa19_cs242_research_project.GameOver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fa19_cs242_research_project.LoginPage.Login;
import com.example.fa19_cs242_research_project.MainMenu.MainMenu;
import com.example.fa19_cs242_research_project.ProfilePage.ProfilePage;
import com.example.fa19_cs242_research_project.R;
import com.example.fa19_cs242_research_project.Util.Constants;
import com.example.fa19_cs242_research_project.Util.DatabaseHandler;
import com.example.fa19_cs242_research_project.Util.Player;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GameOver extends AppCompatActivity {

    private ImageButton profileButton;
    private Button signOutButton;
    private Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //retrieve highscore value and update text with that info
        updateCurrentPlayer();

        setUpProfileButton();
        setUpSignOutButton();
        setUpMainMenuButton();

    }

    private void updateCurrentPlayer() {
        String personName = "", personGivenName = "", personFamilyName = "", personEmail = "", personId = "";
        Constants.loginType personLogin = Constants.loginType.valueOf("NATIVE");
        Uri personPhoto = new Uri.Builder().build();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Profile profile = Profile.getCurrentProfile();
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
            personLogin = Constants.loginType.valueOf("GOOGLE");
        }
        else if(profile != null){
            personName = profile.getName();
            personGivenName = profile.getFirstName();
            personFamilyName = profile.getLastName();
            personId = profile.getId();
            personPhoto = profile.getProfilePictureUri(100, 100);
            personEmail = "";
            personLogin = Constants.loginType.valueOf("FACEBOOK");
        }

        //retrieve highscore value and update text with that info
        int highScoreVal = getIntent().getIntExtra("highscore", 0);
        System.out.println(highScoreVal);

        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        Player currentPlayer = dbHandler.getPlayer(personId);
        if(currentPlayer == null) {
            System.out.println("player does not exist");
            currentPlayer = new Player(personId,
                    personName,
                    personEmail,
                    "",
                    highScoreVal,
                    personLogin,
                    personPhoto.toString());
            currentPlayer.addNewScore(1);
            currentPlayer.addNewScore(2);
            currentPlayer.addNewScore(5);
            currentPlayer.addNewScore(3);
            currentPlayer.addNewScore(4);
            currentPlayer.addNewScore(8);
            currentPlayer.addNewScore(9);
            currentPlayer.addNewScore(7);
            currentPlayer.addNewScore(11);
            currentPlayer.addNewScore(highScoreVal);
            dbHandler.addPlayer(currentPlayer);
        }
        else {
            System.out.println("player does exist");
            currentPlayer.addNewScore(highScoreVal);
            dbHandler.updatePlayer(currentPlayer);
        }
    }

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    private void setUpMainMenuButton() {
        mainMenuButton = findViewById(R.id.main_menu_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();
            }
        });
    }

    /**
     * Initialize functionality for sign out button
     */
    private void setUpSignOutButton() {
        signOutButton = (Button) findViewById(R.id.sign_out_button_profile);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    /**
     * sign out of google/facebook
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
}
