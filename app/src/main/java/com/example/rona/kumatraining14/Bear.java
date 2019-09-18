package com.example.rona.kumatraining14;

public class Bear {
    private String id;
    private String name;
    private String level;

    public Bear(){

    }

    public Bear(String id,String name,String lvl){
        this.id = id;
        this.name = name;
        this.level = lvl;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }


}
