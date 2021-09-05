package ru.frogogo.whitelabel.util

import android.net.UrlQuerySanitizer

object QRCodeUtils {

  private const val STUB_URL = "http://example.com/?"

  fun getQueryParametersMap(parameters: String): Map<String, String> {
    val sanitizer = UrlQuerySanitizer("$STUB_URL$parameters").apply {
      allowUnregisteredParamaters = true
    }

    sanitizer.parameterList ?: return mapOf()

    return sanitizer.parameterList
      .filter { parameterValuePair -> parameterValuePair.mValue.isNotBlank() }
      .associate { parameterValuePair ->
        parameterValuePair.mParameter to parameterValuePair.mValue
      }
  }

  fun isQueryString(string: String): Boolean {
    val parameters = getQueryParametersMap(string)
    return parameters.isNotEmpty()
  }
}
