package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data

sealed class UIState<out T> where T : Any? {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T? = null) : UIState<T>()
    data class Failure(val message: String) : UIState<Nothing>()

    companion object {
        fun <T> success(data: T? = null) = Success(data)
        fun failure(message: String) = Failure(message)
    }
}

infix fun <T> UIState<T>.onLoading(onLoading: UIState.Loading.() -> Unit): UIState<T> {
    return when (this) {
        is UIState.Loading -> {
            onLoading(this)
            this
        }
        else -> this
    }
}

infix fun <T> UIState<T>.onSuccess(onSuccess: UIState.Success<T>.() -> Unit): UIState<T> {
    return when (this) {
        is UIState.Success -> {
            onSuccess(this)
            this
        }
        else -> this
    }
}

infix fun <T> UIState<T>.onFailure(onFailure: UIState.Failure.() -> Unit): UIState<T> {
    return when (this) {
        is UIState.Failure -> {
            onFailure(this)
            this
        }
        else -> this
    }
}
