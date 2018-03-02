/*
 * Copyright (c) 2016 Razeware LLC
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.reposearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Util {

  public static ArrayList<Repository> retrieveRepositoriesFromResponse(String response) throws
      JSONException {
    if (null == response) {
      return new ArrayList<>();
    }
    JSONArray jsonArray = new JSONArray(response);
    ArrayList<Repository> repositories = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      if (null != jsonObject) {
        Repository repository = new Repository();
        if (jsonObject.has("owner")) {
          JSONObject owner = jsonObject.getJSONObject("owner");
          if (owner.has("login")) {
            String ownerName = owner.getString("login");
            repository.setOwner(repository.new Owner(ownerName));
          }
        }
        if (jsonObject.has("name")) {
          repository.setName(jsonObject.getString("name"));
        }
        repositories.add(repository);
      }
    }
    return repositories;
  }
}
