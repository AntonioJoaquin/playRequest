package com.arjhox.develop.domain.common

@Suppress("DataClassPrivateConstructor")
data class LoadingState private constructor(
    val status: Status,
    val message: String? = null
) {

    companion object {
        val LOADING =
            LoadingState(Status.RUNNING)
        val LOADED =
            LoadingState(Status.SUCCESS)

        fun error(message: String?) =
            LoadingState(
                Status.FAILED,
                message
            )
    }


    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

}