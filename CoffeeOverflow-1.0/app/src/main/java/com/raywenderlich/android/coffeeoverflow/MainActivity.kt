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
 */

package com.raywenderlich.android.coffeeoverflow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val coffeeRepo = CoffeeRepository()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    showCount()

    coffeeLimitValue.addTextChangedListener(object: TextWatcher {
      override fun afterTextChanged(p0: Editable?) { }

      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = showCount()
    })

    addCoffee.setOnClickListener {
      coffeeRepo.increment()
      showCount()
    }
  }

  private fun showCount() {
    amountConsumed.text = consumedString()
    coffeeProgress.max = limit().toInt()
    coffeeProgress.progress = coffeeRepo.count
  }

  private fun consumedString() =
      getString(R.string.consumed_format, coffeeRepo.count, limit())

  private fun limit(): String {
    val limitString = coffeeLimitValue.text.toString()
    return if (limitString.isNotEmpty()) limitString else "0"
  }
}

