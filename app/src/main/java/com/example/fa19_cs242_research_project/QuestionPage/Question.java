package com.example.fa19_cs242_research_project.QuestionPage;

public class Question {

    private String question;
    private int answer;

    private String[] options;

    public Question(String question,
                    int answer,
                    String optionOne,
                    String optionTwo,
                    String optionThree,
                    String optionFour) {
        this.question = question;
        this.answer = answer;
        this.options = new String[4];
        this.options[0] = optionOne;
        this.options[1] = optionTwo;
        this.options[2] = optionThree;
        this.options[3] = optionFour;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public String getCorrectAnswerText() {
        return this.options[answer];
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getOptionOne() {
        return options[0];
    }

    public void setOptionOne(String optionOne) {
        this.options[0] = optionOne;
    }

    public String getOptionTwo() {
        return options[1];
    }

    public void setOptionTwo(String optionTwo) {
        this.options[1] = optionTwo;
    }

    public String getOptionThree() {
        return options[2];
    }

    public void setOptionThree(String optionThree) {
        this.options[2] = optionThree;
    }

    public String getOptionFour() {
        return options[3];
    }

    public void setOptionFour(String optionFour) {
        this.options[3] = optionFour;
    }
}
