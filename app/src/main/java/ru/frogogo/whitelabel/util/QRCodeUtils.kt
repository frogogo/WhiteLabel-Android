package ru.frogogo.whitelabel.util

import android.net.UrlQuerySanitizer
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

object QRCodeUtils {

  private const val STUB_URL = "http://example.com/?"
  private const val VENDING_MACHINE_HOST = "poprobuy.ru"
  private const val VENDING_MACHINE_QUERY_PARAM = "machine"

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

  /**
   * Extracts returns vending machine number from provided url
   *
   * @return valid machine number of null
   */
  fun getVendingMachineNumber(string: String): String? {
    val url = string.toHttpUrlOrNull() ?: return null

    // Check if host is appropriate
    if (url.host != VENDING_MACHINE_HOST) {
      return null
    }

    return url.queryParameter(VENDING_MACHINE_QUERY_PARAM)
  }

  fun isQueryString(string: String): Boolean {
    val parameters = getQueryParametersMap(string)
    return parameters.isNotEmpty()
  }
}
