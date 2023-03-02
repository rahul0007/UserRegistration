package com.assignment.userregistration.lifecycle

import java.io.Serializable

open class ErrorModelView(
    var messageTitle: String? = "",
    var messageSubtitle: String? = "",
    var tagException: String? = ""
) : Serializable