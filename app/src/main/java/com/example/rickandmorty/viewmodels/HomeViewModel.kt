package com.example.rickandmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.rickandmorty.model.HomeCharacter
import com.example.rickandmorty.repository.CharacterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val characterRepo: CharacterRepo):ViewModel(){

    val pagingData: Flow<PagingData<HomeCharacter>> = characterRepo.getCharacterStream()


}