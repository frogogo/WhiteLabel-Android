package ru.poprobuy.poprobuy.data

class SessionStorage {

  private var sessionId: String? = null

  fun getSessionId(): String? = sessionId

  fun saveSessionId(sessionId: String) {
    this.sessionId = sessionId
  }
}
