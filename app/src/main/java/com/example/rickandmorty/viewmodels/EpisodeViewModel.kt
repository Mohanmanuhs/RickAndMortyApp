package com.example.rickandmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.model.EpisodesUiModel
import com.example.rickandmorty.repository.CharacterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val characterRepo: CharacterRepo):ViewModel(){

    val pagingData: Flow<PagingData<EpisodesUiModel>> = characterRepo.getEpisodesStream()

    private val _imgList = MutableStateFlow<List<String>>(emptyList())
    val imgList:StateFlow<List<String>> = _imgList

    fun getCharacterImgList(list:List<Int>){
        viewModelScope.launch {
             _imgList.value = characterRepo.getCharactersImageList(list)
        }
    }


}