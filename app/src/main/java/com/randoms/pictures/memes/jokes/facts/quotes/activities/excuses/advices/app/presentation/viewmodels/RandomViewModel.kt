package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.UIState
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.RandomModel
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.domain.RandomUseCases
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.navigation.NavArguments
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val randomUseCases: RandomUseCases
) : ViewModel() {

    private val randomData = MutableLiveData<UIState<RandomModel>>()
    val random: LiveData<UIState<RandomModel>> = randomData

    init {
        getRandom()
    }

    fun getRandom() {
        randomData.value = UIState.Loading

        val model = NavArguments.model ?: return

        viewModelScope.launch {
            if (model.isImageUrl) {
                randomData.value = UIState.success(RandomModel(imageUrl = model.baseUrl))
            } else if (model.isListUrl) {
                randomUseCases.getRandomList(model.baseUrl.orEmpty())
            } else if (model.isStringListUrl) {
                randomUseCases.getRandomStringList(model.baseUrl.orEmpty())
            } else {
                randomUseCases.getRandom(model.baseUrl.orEmpty())
            }.collectLatest {
                randomData.value = it
            }
        }
    }
}