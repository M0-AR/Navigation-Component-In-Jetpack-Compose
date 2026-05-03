package com.example.navigationcomponentinjetpackcompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _name = mutableStateOf("")
    val name: State<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}
