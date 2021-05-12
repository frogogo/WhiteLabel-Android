package ru.frogogo.whitelabel.util

object NetworkConstants {

  const val TIMEOUT_CONNECT = 60L
  const val TIMEOUT_READ = 60L
  const val TIMEOUT_WRITE = 60L

  const val CACHE_SIZE_BYTES: Long = 1024 * 1024 * 10 // 10 MB
  const val CACHE_DIRECTORY = "network"

  const val HEADER_SESSION_ID = "Session-Id"
}
