/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.emotionalface

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View


class EmotionalFaceView(context: Context, attrs: AttributeSet) : View(context, attrs) {

  companion object {
    private const val DEFAULT_FACE_COLOR = Color.YELLOW
    private const val DEFAULT_EYES_COLOR = Color.BLACK
    private const val DEFAULT_MOUTH_COLOR = Color.BLACK
    private const val DEFAULT_BORDER_COLOR = Color.BLACK
    private const val DEFAULT_BORDER_WIDTH = 4.0f

    const val HAPPY = 0L
    const val SAD = 1L
  }

  private var faceColor = DEFAULT_FACE_COLOR
  private var eyesColor = DEFAULT_EYES_COLOR
  private var mouthColor = DEFAULT_MOUTH_COLOR
  private var borderColor = DEFAULT_BORDER_COLOR
  private var borderWidth = DEFAULT_BORDER_WIDTH

  private val paint = Paint()
  private val mouthPath = Path()
  private var size = 0

  var happinessState = HAPPY
    set(state) {
      field = state
      invalidate()
    }

  init {
    paint.isAntiAlias = true
    setupAttributes(attrs)
  }

  private fun setupAttributes(attrs: AttributeSet?) {
    // Obtain a typed array of attributes
    val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.EmotionalFaceView,
        0, 0)

    // Extract custom attributes into member variables
    happinessState = typedArray.getInt(R.styleable.EmotionalFaceView_state, HAPPY.toInt()).toLong()
    faceColor = typedArray.getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_COLOR)
    eyesColor = typedArray.getColor(R.styleable.EmotionalFaceView_eyesColor, DEFAULT_EYES_COLOR)
    mouthColor = typedArray.getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_COLOR)
    borderColor = typedArray.getColor(R.styleable.EmotionalFaceView_borderColor,
        DEFAULT_BORDER_COLOR)
    borderWidth = typedArray.getDimension(R.styleable.EmotionalFaceView_borderWidth,
        DEFAULT_BORDER_WIDTH)

    // TypedArray objects are shared and must be recycled.
    typedArray.recycle()
  }

  override fun onDraw(canvas: Canvas) {
    // call the super method to keep any drawing from the parent side.
    super.onDraw(canvas)

    drawFaceBackground(canvas)
    drawEyes(canvas)
    drawMouth(canvas)
  }

  private fun drawFaceBackground(canvas: Canvas) {
    paint.color = faceColor
    paint.style = Paint.Style.FILL

    val radius = size / 2f

    canvas.drawCircle(size / 2f, size / 2f, radius, paint)

    paint.color = borderColor
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = borderWidth

    canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
  }

  private fun drawEyes(canvas: Canvas) {
    paint.color = eyesColor
    paint.style = Paint.Style.FILL

    val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
    canvas.drawOval(leftEyeRect, paint)

    val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)

    canvas.drawOval(rightEyeRect, paint)
  }

  private fun drawMouth(canvas: Canvas) {
    // Clear
    mouthPath.reset()

    mouthPath.moveTo(size * 0.22f, size * 0.7f)

    if (happinessState == HAPPY) {
      // Happy mouth path
      mouthPath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f)
      mouthPath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f)
    } else {
      // Sad mouth path
      mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
      mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)
    }

    paint.color = mouthColor
    paint.style = Paint.Style.FILL

    // Draw mouth path
    canvas.drawPath(mouthPath, paint)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    size = Math.min(measuredWidth, measuredHeight)

    setMeasuredDimension(size, size)
  }

  override fun onSaveInstanceState(): Parcelable {
    val bundle = Bundle()

    bundle.putLong("happinessState", happinessState)
    bundle.putParcelable("superState", super.onSaveInstanceState())

    return bundle
  }

  override fun onRestoreInstanceState(state: Parcelable) {
    var viewState = state

    if (viewState is Bundle) {
      happinessState = viewState.getLong("happinessState", HAPPY)
      viewState = viewState.getParcelable("superState")
    }

    super.onRestoreInstanceState(viewState)
  }
}