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


import android.os.Parcel;
import android.os.Parcelable;

public class Repository implements Parcelable {

  private String name;
  private Owner owner;

  public Repository() {

  }

  public Repository(String name, Owner owner) {
    setName(name);
    setOwner(owner);
  }

  public Repository(Parcel in) {
    String[] data = new String[2];
    in.readStringArray(data);
    this.name = data[0];
    this.owner = new Owner(data[1]);

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeStringArray(new String[]{this.name, this.owner.getLogin()});
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public Repository createFromParcel(Parcel in) {
      return new Repository(in);
    }

    public Repository[] newArray(int size) {
      return new Repository[size];
    }
  };

  public class Owner {

    public Owner() {

    }

    public Owner(String login) {
      setLogin(login);
    }

    private String login;

    public String getLogin() {
      return login;
    }

    public void setLogin(String login) {
      this.login = login;
    }

  }
}

