package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = MutableLiveData(0)
    private var _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    val score: LiveData<Int>
        get() = _score

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false

    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
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
            _currentScrambledWord.value = String(tmpWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
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