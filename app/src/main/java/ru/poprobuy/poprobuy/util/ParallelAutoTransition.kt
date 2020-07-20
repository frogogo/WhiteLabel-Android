package ru.poprobuy.poprobuy.util

import android.content.Context
import android.util.AttributeSet
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionSet

/**
 * Utility class for creating a default transition that automatically fades,
 * moves, and resizes views during a scene change.
 *
 *
 * An AutoTransition can be described in a resource file by using the
 * tag `autoTransition`, along with the other standard
 * attributes of [Transition].
 */
class ParallelAutoTransition : TransitionSet {

  /**
   * Constructs an AutoTransition object, which is a TransitionSet which
   * fades out disappearing targets, moves and resizes existing
   * targets, and fades in appearing targets.
   */
  constructor() {
    init()
  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    init()
  }

  private fun init() {
    ordering = ORDERING_TOGETHER

    addTransition(Fade(Fade.OUT))
    addTransition(ChangeBounds())
    addTransition(Fade(Fade.IN))
  }
}
