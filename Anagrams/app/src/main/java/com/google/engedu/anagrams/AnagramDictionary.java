/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private final ArrayList<String> wordList = new ArrayList<>();
    private final HashSet<String> wordSet = new HashSet<>();
    private final HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            String sortedWord = sortLetters(word);
            if(lettersToWord.containsKey(sortedWord))
                lettersToWord.get(sortedWord).add(word);
            else{
                lettersToWord.put(sortedWord, new ArrayList<String>());
                lettersToWord.get(sortedWord).add(word);
            }
            wordSet.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        // word does not contain base word as substring
        if (word.contains(base)) return false;
        else return wordSet.contains(word);
    }


    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();


        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String tempWord;
        for (char c = 'a'; c <='z'; c++) {
            tempWord = word + c;
            String tempWordSorted = sortLetters(tempWord);
            if (lettersToWord.containsKey(tempWordSorted)) {
                for(String anagram : lettersToWord.get(tempWordSorted)){
                    if(!anagram.contains(word)) result.add(anagram);
                }
            }
        }
        //find all anagrams add one letter

        return result;
    }

    public String sortLetters(String inputString){
        char[] sortedLetters = new char[inputString.length()];
        inputString.getChars(0, inputString.length(), sortedLetters, 0);
        Arrays.sort(sortedLetters);
        System.out.println(Arrays.toString(sortedLetters));
        return String.valueOf(sortedLetters);
    }

    public String pickGoodStarterWord() {
        //iterate over wordlist
        random.nextInt(wordList.size());
        int randIndex;
        while (true){
            randIndex = random.nextInt(wordList.size());
            String tempWordSorted = sortLetters(wordList.get(randIndex));
            if(lettersToWord.containsKey(tempWordSorted) && lettersToWord.get(tempWordSorted).size() >= MIN_NUM_ANAGRAMS){
                break;
            }
        }
        return wordList.get(randIndex);
    }
}
