package com.quantumman.whooshservice.util

import com.quantumman.whooshservice.R

sealed class MessageError(error: Int) {
    object NoConnection : MessageError(error = R.string.connection_error)
    object Unknown : MessageError(error = R.string.unknown_error)
}