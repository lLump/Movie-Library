package com.example.mymovielibrary.core.presentation.ui.uiText

import com.example.mymovielibrary.core.domain.model.DataError
import com.example.mymovielibrary.core.domain.model.Result

//fun DataError.asUiText(): UiText {
//    return when (this) {
//        //Network
//        DataError.Network.BAD_REQUEST -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.FORBIDDEN -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.SERVICE_UNAVAILABLE -> UiText.StringResource(R.string.bad_request)
//        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.bad_request)
//        //Local
//        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.bad_request)
//    }
//}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> TODO()
        is DataError.Network -> UiText.DynamicString(this.error)
    }
}

