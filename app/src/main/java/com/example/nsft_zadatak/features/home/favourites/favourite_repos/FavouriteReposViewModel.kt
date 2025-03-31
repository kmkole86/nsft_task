package com.example.nsft_zadatak.features.home.favourites.favourite_repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository_impl.GitRepositoryImpl
import com.example.nsft_zadatak.common.mapToPresentationRepositories
import com.example.nsft_zadatak.model.GitRepositoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavouriteReposViewModel @Inject constructor(
    private val gitRepository: GitRepositoryImpl,
) : ViewModel() {

    //TODO: add loading and error state
    val state: StateFlow<List<GitRepositoryUiModel>> =
        gitRepository.observeFavouriteRepositories().map { it.mapToPresentationRepositories() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = persistentListOf()
            )


    fun onChangeRepoFavouriteStatus(repoId: Int) {
        gitRepository.changeRepoFavouriteStatus(
            repoId
        ).onEach {
            //handle error here, emit toast...
            //if there is a real api call
            //for local impl we are using there is no error possible
        }.launchIn(viewModelScope)
    }
}