package com.example.insights.data.repository

import com.example.insights.data.RoundResponse

sealed class MainRepositoryState {
    data class Success(val list: List<RoundResponse>) : MainRepositoryState()
    object EmptyList : MainRepositoryState()
    data class Error(val errorMessage: String?) : MainRepositoryState()
}
