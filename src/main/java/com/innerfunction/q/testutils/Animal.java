package com.innerfunction.q.testutils;

public class Animal extends Thing {

    Fruit likes;
    
    public void setLikes(Fruit likes) {
        this.likes = likes;
    }

    public Fruit getLikes() {
        return likes;
    }

}
