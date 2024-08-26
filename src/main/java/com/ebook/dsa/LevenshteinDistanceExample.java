package com.ebook.dsa;

import org.apache.commons.text.similarity.EditDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class LevenshteinDistanceExample {
    public static void main(String[] args) {
        String str1 = "apple";
        String str2 = "apples";
        
        int distance = calculateLevenshteinDistance(str1, str2);
        System.out.println("Levenshtein distance between \"" + str1 + "\" and \"" + str2 + "\": " + distance);
    }

    public static int calculateLevenshteinDistance(String str1, String str2) {
    	EditDistance<Integer> levenshteinDistance = new LevenshteinDistance();
        return levenshteinDistance.apply(str1, str2);
    }
}
