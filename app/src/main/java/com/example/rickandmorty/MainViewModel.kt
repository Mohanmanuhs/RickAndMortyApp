package com.example.rickandmorty

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.repository.CharacterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepo: CharacterRepo):ViewModel(){


    val pagingData = characterRepo.getCharacterStream()



}