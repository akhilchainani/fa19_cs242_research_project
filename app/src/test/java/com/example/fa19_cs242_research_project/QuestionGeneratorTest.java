package com.example.fa19_cs242_research_project;

import com.example.fa19_cs242_research_project.QuestionPage.Question;
import com.example.fa19_cs242_research_project.QuestionPage.QuestionActivity;
import com.example.fa19_cs242_research_project.QuestionPage.QuestionGenerator;

import org.junit.Before;
import org.junit.Test;

import static com.facebook.FacebookSdk.getApplicationContext;
import static org.junit.Assert.*;

public class QuestionGeneratorTest {

    private QuestionGenerator questionGeneratorTest;

    @Before
    public void setUp() throws Exception {
        questionGeneratorTest = new QuestionGenerator(getApplicationContext(),
                                                                        "General Knowledge",
                                                                            "Hard");
    }

    @Test
    public void testOne() {
        assertNotNull(questionGeneratorTest);
        assertTrue(questionGeneratorTest.hasNextQuestion());
        Question question = questionGeneratorTest.getNextQuestion();
        assertNotNull(question);
    }
}