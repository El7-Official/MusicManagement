package com.factory.appsfactory.challenge.presentation.utils

interface UiModel

open class UiAwareModel : UiModel {
    var isRedelivered: Boolean = false
}
