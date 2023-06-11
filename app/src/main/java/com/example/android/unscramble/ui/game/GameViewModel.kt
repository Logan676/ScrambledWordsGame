package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord: String

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    val score: Int
        get() = _score

    val currentWordCount: Int
        get() = _currentWordCount

    val currentScrambledWord: String
        get() = _currentScrambledWord

    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false

    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tmpWord = currentWord.toCharArray()
        tmpWord.shuffle()
        while (String(tmpWord).equals(currentWord, false)) {
            tmpWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tmpWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        return if (playerWord.equals(currentWord, true)) {
            increaseScore()
            true
        } else {
            false
        }
    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }
}