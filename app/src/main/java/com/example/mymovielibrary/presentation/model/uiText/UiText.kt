package com.example.mymovielibrary.presentation.model.uiText

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.mymovielibrary.domain.model.DataError
import com.example.mymovielibrary.domain.model.Result

sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(id, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}

//fun Result.Error<*>.asErrorUiText(): UiText {
//    return error.asUiText()
//}

fun DataError.asUiText(): UiText {
    return when (this) {
        // code 401 - 100%
//        DataError.Local.DISK_FULL -> TODO()
        is DataError.Network -> UiText.DynamicString(this.error)
    }
}
