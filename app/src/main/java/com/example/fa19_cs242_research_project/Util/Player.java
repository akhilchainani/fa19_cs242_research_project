package com.example.fa19_cs242_research_project.Util;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static String playerName;
    private static ArrayList<String> friends;

    private static ArrayList<String> pastScores;
    private static int highScore;
    private static String email;
    private static String phoneNumber;
    private static String playerId;
    private static Constants.loginType login;
    private static String profilePicUri;

    public Player(String playerId,
                  String playerName,
                  String email,
                  String phoneNumber,
                  int highScore,
                  Constants.loginType login,
                  String profilePicUri) {
        this.playerName = playerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.highScore = highScore;
        this.playerId = playerId;
        this.login = login;
        this.profilePicUri = profilePicUri;
        this.friends = new ArrayList<>();
        this.pastScores = new ArrayList<>();
    }

    public static ArrayList<String> getPastScores() {
        return pastScores;
    }

    public static void setPastScores(ArrayList<String> pastScores) {
        Player.pastScores = pastScores;
    }

    public static void addNewScore(int score) {
        Player.pastScores.add(Integer.toString(score));
    }

    public static ArrayList<String> getFriends() {
        return friends;
    }

    public static void setFriends(ArrayList<String> friends) {
        Player.friends = friends;
    }

    public static Constants.loginType getLogin() {
        return login;
    }

    public static void setLogin(Constants.loginType login) {
        Player.login = login;
    }

    public static String getPlayerId() {
        return playerId;
    }

    public static void setPlayerId(String playerId) {
        Player.playerId = playerId;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        Player.phoneNumber = phoneNumber;
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static String getProfilePicUri() {
        return profilePicUri;
    }

    public static void setProfilePicUri(String profilePicUri) {
        Player.profilePicUri = profilePicUri;
    }

    public static void setPlayerName(String playerName) {
        Player.playerName = playerName;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static void setHighScore(int highScore) {
        Player.highScore = highScore;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Player.email = email;
    }

    public static void addFriend(String friendName) {
        friends.add(friendName);
    }
}
