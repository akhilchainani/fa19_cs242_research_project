package com.example.fa19_cs242_research_project.Util;

public class Constants {
    public static enum loginType {
        FACEBOOK("FACEBOOK"),
        GOOGLE("GOOGLE"),
        NATIVE("NATIVE");

        private String name;

        loginType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static enum questionType {

    }
}
