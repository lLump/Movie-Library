package com.example.mymovielibrary.presentation.ui.util.uiText

import com.example.mymovielibrary.R
import com.example.mymovielibrary.domain.model.handlers.DataError
import com.example.mymovielibrary.domain.model.handlers.Result

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}

//TODO (text for every error)
fun DataError.asUiText(): UiText {
    return when (this) {
        is DataError.Message -> UiText.DynamicString(this.error)
        DataError.Network.BAD_REQUEST -> UiText.StringResource(R.string.bad_request) // 400
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.bad_request) // 401
        DataError.Network.FORBIDDEN -> UiText.StringResource(R.string.bad_request) // 403
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.bad_request)
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.bad_request)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.bad_request) // 500
        DataError.Network.SERVICE_UNAVAILABLE -> UiText.StringResource(R.string.bad_request) // 503
        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.bad_request)
    }
}

