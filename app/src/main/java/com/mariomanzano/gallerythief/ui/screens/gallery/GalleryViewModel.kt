package com.mariomanzano.gallerythief.ui.screens.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.ImageItem
import com.mariomanzano.gallerythief.network.toError
import com.mariomanzano.gallerythief.ui.navigation.NavArg
import com.mariomanzano.gallerythief.usecases.GetImageListUseCase
import com.mariomanzano.gallerythief.usecases.StealWebImagesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getImageListUseCase: GetImageListUseCase,
    private val stealWebImagesListUseCase: StealWebImagesListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { _state.value.copy(loading = true) }
            getImageListUseCase()
                .catch { cause ->
                    _state.update { it.copy(error = cause.toError()) }
                }
                .collect { items ->
                    if (items.isNotEmpty()) {
                        _state.update {
                            _state.value.copy(loading = false,
                                pictures = items)
                        }
                    } else {
                        _state.update {
                            _state.value.copy(loading = false,
                                pictures = emptyList(),
                            error = Error.NoData)
                        }
                    }
                }
        }
    }

    fun launchTheRobbery() {
        val url = savedStateHandle.get<String>(NavArg.ItemType.key) ?: ""
        if (url.replace(" ".toRegex(),"").isNotEmpty()){
            viewModelScope.launch {
                _state.update { _state.value.copy(loading = true) }
                _state.update { _state.value.copy(loading = false, error = stealWebImagesListUseCase(url)) }
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val pictures: List<ImageItem>? = null,
        val error: Error? = null
    )
}