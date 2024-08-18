package com.example.rickandmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.repository.CharacterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val characterRepo: CharacterRepo):ViewModel(){

    private val _character = MutableStateFlow<Character?>(null)

    val character: StateFlow<Character?> = _character


    fun getCharacterById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _character.value = characterRepo.getCharacterById(id)
        }
    }


}