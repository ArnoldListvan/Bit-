package com.taskProject.bite;

/**
 * Word entitie.
 */
public class Word {

    /**
     * Identification.
     */
    private long id;

    /**
     * Word content.
     */
    private String word;

    /**
     * Status that states if word is palindrome or not.
     */
    private boolean palindromeStatus;

    /**
     * Constructor.
     * @param word
     */
   public Word(long id, String word) {
       super();
       this.id = id;
       this.word = word;
       this.palindromeStatus = identifyPalindromeStatus(word);
   }

    /**
     * Method that identifies palindrome status of word.
     * @param word
     * @return
     */
   private boolean identifyPalindromeStatus(String word) {
       StringBuffer reverse = new StringBuffer(word);
       if (word.equals(reverse.reverse().toString())) return true;
       return false;
   };

    /**
     * Method that is responsible for updating palindrome status.
     * @param wordToUpdate
     */
   public void updatePalindromeStatus(Word wordToUpdate) {
       wordToUpdate.setPalindromeStatus(identifyPalindromeStatus(wordToUpdate.getWord()));
   }

    /**
     * Getter for id.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for Word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Setter for word.
     * @param word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Getter for palindrome.
     */
    public boolean isPalindromeStatus() {
        return palindromeStatus;
    }

    /**
     * Setter for palindromeStatus.
     * @param palindromeStatus
     */
    public void setPalindromeStatus(boolean palindromeStatus) {
        this.palindromeStatus = palindromeStatus;
    }
}
