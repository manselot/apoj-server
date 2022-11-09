package com.example.demo.model;

import java.util.ArrayList;

public class Member {

    private String name;
    private int currentid;
    private boolean leader;

    public int getScore() {
        return score;
    }

    private int score;
    private ArrayList<String> answers = new ArrayList<>();

    public Member(String name, int currentid, boolean leader) {
        this.name = name;
        this.currentid = currentid;
        this.leader = leader;
    }

    public Member(String name, int currentid) {
        this.name = name;
        this.currentid = currentid;
        this.leader = false;
    }

    public String getName() {
        return name;
    }

    public void addAnswer(String answer) {

        answers.add(answer);
    }

    public int getCurrentid() {
        return currentid;
    }

    public String getAnswer(){
        if (answers.size() == 0){
            return "1";
        }
        return  answers.get(answers.size() - 1);
    }

    public void setScore(int score) {
        this.score = score;
    }
}
