package com.SQLsupport.DBClass;

import java.io.Serializable;

public class Faq implements Serializable {
    private int id;
    private String question, answer, user_login;

    public Faq(String user_login, String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.user_login=user_login;
        this.id=0;
    }

    public String getUser_login() {
        return user_login;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
