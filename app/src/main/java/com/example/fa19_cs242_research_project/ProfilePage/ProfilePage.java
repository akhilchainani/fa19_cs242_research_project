package com.example.fa19_cs242_research_project.ProfilePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fa19_cs242_research_project.LoginPage.Login;
import com.example.fa19_cs242_research_project.LoginPage.DownloadImageTask;
import com.example.fa19_cs242_research_project.R;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfilePage extends AppCompatActivity {

    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;
    private String personId;
    private Uri personPhoto;

    private ImageView profilePic;

    private TextView profileName;
    private TextView highScore;
    private TextView highScore2;

    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeProfileAttributes();
        setUpSignOutButton();

        String[] urlList = {personPhoto.toString()};
        profilePic = (ImageView) findViewById(R.id.profile_pic);
        DownloadImageTask getProfilePic = (DownloadImageTask) new DownloadImageTask(profilePic).execute(urlList);

        profileName = (TextView) findViewById(R.id.profile_name);
        profileName.setText(personName);

        //retrieve highscore value and update text with that info
        int highScoreVal = getIntent().getIntExtra("highscore", 0);
        System.out.println(highScoreVal);
        highScore = (TextView) findViewById(R.id.high_score_val);
        highScore.setText(Integer.toString(highScoreVal));
    }

    /**
     * Initialize profile information from either Facebook or Google
     * to load profile page
     */
    private void initializeProfileAttributes() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Profile profile = Profile.getCurrentProfile();
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
        }
        else if(profile != null){
            personName = profile.getName();
            personGivenName = profile.getFirstName();
            personFamilyName = profile.getLastName();
            personId = profile.getId();
            personPhoto = profile.getProfilePictureUri(100, 100);
        }
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
}
