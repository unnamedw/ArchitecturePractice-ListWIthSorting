package com.doachgosum.marketsampleapp.presentation

sealed class CommonEvent {
    data class ShowToast(val msg: String): CommonEvent()
}
