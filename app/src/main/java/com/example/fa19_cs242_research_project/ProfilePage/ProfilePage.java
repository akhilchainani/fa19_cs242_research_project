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
import com.example.fa19_cs242_research_project.MainMenu.MainMenu;
import com.example.fa19_cs242_research_project.R;
import com.example.fa19_cs242_research_project.Util.Constants;
import com.example.fa19_cs242_research_project.Util.DatabaseHandler;
import com.example.fa19_cs242_research_project.Util.Player;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ProfilePage extends AppCompatActivity {

    private Player currentPlayer;

    private ImageView profilePic;

    private TextView profileName;
    private TextView highScore;
    private TextView highScore2;

    private Button signOutButton;
    private Button inviteButton;
    private Button mainMenuButton;

    private GameRequestDialog requestDialog;
    private CallbackManager callbackManager;

    private LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        currentPlayer = initializeProfileAttributes();
        setUpSignOutButton();
        setUpInviteButton();
        setUpMainMenuButton();

        String[] urlList = {currentPlayer.getProfilePicUri()};
        System.out.println(currentPlayer.getProfilePicUri());
        profilePic = (ImageView) findViewById(R.id.profile_pic);
        DownloadImageTask getProfilePic = (DownloadImageTask) new DownloadImageTask(profilePic).execute(urlList);

        profileName = (TextView) findViewById(R.id.profile_name);
        profileName.setText(currentPlayer.getPlayerName());

        highScore = (TextView) findViewById(R.id.high_score_val);
        highScore.setText(Integer.toString(currentPlayer.getHighScore()));

        lineChartView = findViewById(R.id.chart);
        setUpLineChart();
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

    private void setUpLineChart() {
        String[] axisData = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int[] yAxisData = new int[10];
        for(int i = currentPlayer.getPastScores().size() - 10; i < currentPlayer.getPastScores().size(); i++) {
            yAxisData[i - (currentPlayer.getPastScores().size() - 10)] = Integer.parseInt(currentPlayer.getPastScores().get(i));
        }

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues);

        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);
    }

    /**
     * Initialize profile information from either Facebook or Google
     * to load profile page
     */
    private Player initializeProfileAttributes() {
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

        DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
        Player currentPlayer = dbHandler.getPlayer(personId);
        if(currentPlayer == null) {
            System.out.println("player does not exist");
            currentPlayer = new Player(personId,
                    personName,
                    personEmail,
                    "",
                    0,
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
            dbHandler.addPlayer(currentPlayer);
        }
        else {
            System.out.println("player does exist");
            dbHandler.updatePlayer(currentPlayer);
        }

        return currentPlayer;
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
     * Initialize functionality for invite button
     */
    private void setUpInviteButton() {
        inviteButton = (Button) findViewById(R.id.invite_button_profile);
        callbackManager = CallbackManager.Factory.create();
        requestDialog = new GameRequestDialog(this);
        requestDialog.registerCallback(callbackManager,
                new FacebookCallback<GameRequestDialog.Result>() {
                    public void onSuccess(GameRequestDialog.Result result) {
                        String id = result.getRequestId();
                    }
                    public void onCancel() {}
                    public void onError(FacebookException error) {}
                }
        );
        System.out.println("request dialog created");

        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestButton();
            }
        });
    }

    private void onClickRequestButton() {
        GameRequestContent content = new GameRequestContent.Builder()
                .setMessage("Come play this new quiz game with me")
                .build();
        requestDialog.show(content);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
