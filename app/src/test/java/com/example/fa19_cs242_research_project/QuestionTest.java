package com.example.fa19_cs242_research_project;

import com.example.fa19_cs242_research_project.QuestionPage.Question;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionTest {

    private Question questionTest;

    @Before
    public void setUp() throws Exception {
        questionTest = new Question("What is this question?",
                                                2, "option 1",
                                                "option 2", "option 3",
                                                "option 4");

    }

    @Test
    public void testOne() {
        assertEquals(questionTest.getAnswer(), 2);
        assertEquals(questionTest.getCorrectAnswerText(), "option 3");
    }

    @Test
    public void testTwo() {
        assertEquals(questionTest.getOptionOne(), "option 1");
        assertEquals(questionTest.getOptionTwo(), "option 2");
        assertEquals(questionTest.getOptionThree(), "option 3");
        assertEquals(questionTest.getOptionFour(), "option 4");
    }

    @Test
    public void testThree() {
        assertEquals(questionTest.getQuestion(), "What is this question?");
    }
}