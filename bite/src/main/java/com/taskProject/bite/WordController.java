package com.taskProject.bite;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Controller responsible for logic affecting word entities.
 */
@RestController
@RequestMapping("/BiteApi")
public class WordController {

    /**
     * replacement for database.
     */
    private List<Word> dataBase = new ArrayList<>();

    /**
     * replacement for id increment.
     */
    private AtomicLong idCounter = new AtomicLong();

    /**
     * boolean to ensure that entities' word exists in database.
     */
    private boolean wordExist = false;

    /**
     * Method that selects id to interact with from list.
     * @param word
     * @return
     */
    private int selectedWord(String word) {
        Integer idToSelect = 0;
        wordExist = false;
        for (int i = 0; i < dataBase.size(); i++) {
            if (dataBase.get(i).getWord().equals(word)) {
                wordExist = true;
                idToSelect = i;
                break;
            }
        }
        return idToSelect;
    }

    /**
     * API extension that creates new word entities.
     * @param word
     * @return
     */
    @GetMapping("/createWord")
    public ResponseEntity<Word> createWord(@RequestParam(value = "word", defaultValue = "createTest") String word) {
        if (word.matches("[a-zA-Z]+")) {
            dataBase.add(new Word(idCounter.getAndIncrement(), word));
            Word addedWord = dataBase.get(dataBase.size() - 1);
            return new ResponseEntity<Word>(addedWord, HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * API extension that gets word.
     * @param word value by which db is being searched
     * @return
     */
    @GetMapping("/getWord")
    public ResponseEntity<Word> getWord(@RequestParam(value = "word", defaultValue = "getTest") String word) {
        int idOfSelectedWord = selectedWord(word);
        if (wordExist) {
            return new ResponseEntity<Word>(dataBase.get(idOfSelectedWord), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * API extension that updates word.
     * @param oldWord value by which db is being searched
     * @param newWord update value
     * @return
     */
    @GetMapping("/updateWord")
    public ResponseEntity<Word> updateWord(@RequestParam(value = "oldWord", defaultValue = "oldWord") String oldWord,
                           @RequestParam(value = "newWord", defaultValue = "newWord") String newWord) {
        int idOfSelectedWord = selectedWord(oldWord);
        if (wordExist) {
            Word word = dataBase.get(idOfSelectedWord);
            word.setWord(newWord);
            word.updatePalindromeStatus(word);
            return new ResponseEntity<Word>(word, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * API extension that deletes word.
     * @param word value by which db is being searched
     * @return
     */
    @GetMapping("/deleteWord")
    public ResponseEntity<Word> deleteWord(@RequestParam(value = "word", defaultValue = "getTest") String word) {
        int idOfSelectedWord = selectedWord(word);
        if (wordExist) {
            Word deletedWord = dataBase.get(idOfSelectedWord);
            dataBase.remove(dataBase.get(idOfSelectedWord));
            return new ResponseEntity<Word>(deletedWord, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * API extension that gets all words.
     * @return
     */
    @GetMapping("/getAllWords")
    public ResponseEntity<List<Word>> getAllWords() {
        return new ResponseEntity<List<Word>>(dataBase, HttpStatus.OK);
    }

    /**
     * API extension that gets all palindrome words.
     * @return
     */
    @GetMapping("/getPalindromes")
    public ResponseEntity<List<Word>> getAllPalindromeWords() {
        List<Word> palindromeList = new ArrayList<>();
        dataBase.forEach(db -> {
            if (db.isPalindromeStatus()) palindromeList.add(db);
        });
        return new ResponseEntity<List<Word>>(palindromeList, HttpStatus.OK);
    }

    /**
     * getter for database.
     * @return
     */
    public List<Word> getDataBase() {
        return dataBase;
    }

}
