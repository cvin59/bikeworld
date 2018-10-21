package com.team17.bikeworld.crawl.dict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CateObj {
    private String meaning;
    private List<String> acceptWords;
    private List<String> nameContain;

    public CateObj(String meaning, String[] acceptWord, String[] nameContain) {
        this.meaning = meaning;
        this.acceptWords = new ArrayList<String>();
        this.acceptWords.add(meaning);
        this.acceptWords.addAll(Arrays.asList(acceptWord));
        this.nameContain = new ArrayList<String>();
        this.nameContain.add(meaning);
        this.nameContain.addAll(Arrays.asList(nameContain));
    }

    public String getMeaning() {
        return meaning;
    }

    public List<String> getAcceptWords() {
        return acceptWords;
    }

    public List<String> getNameContain() {
        return nameContain;
    }
}
