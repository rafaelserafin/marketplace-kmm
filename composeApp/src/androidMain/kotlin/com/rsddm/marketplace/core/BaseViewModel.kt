package com.rsddm.marketplace.core

import androidx.lifecycle.ViewModel
import com.rsddm.marketplace.navigation.Navigator

abstract class BaseViewModel(val navigator: Navigator) : ViewModel() {
    abstract fun setupTopBar()
}