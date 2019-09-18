package com.example.rona.kumatraining14;

public class Skills {
    private String id,name,power;

    public Skills(){

    }
    public Skills(String id,String name,String power){
        this.id = id;
        this.name = name;
        this.power = power;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }
}
