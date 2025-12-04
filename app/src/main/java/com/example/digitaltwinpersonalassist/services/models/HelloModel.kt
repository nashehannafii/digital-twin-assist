package com.example.digitaltwinpersonalassist.services.models

import java.io.Serializable

/**
 * Simple data model for the hello response from the API.
 *
 * @property message The message string returned by the server.
 */
data class HelloModel (
    var message: String?
) : Serializable
