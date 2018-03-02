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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {

  private final List<Repository> mValues;

  public ListRecyclerViewAdapter(List<Repository> repos) {
    mValues = repos;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mRepo = mValues.get(position);
    holder.mRepoName.setText(mValues.get(position).getName());
    holder.mRepoOwner.setText(mValues.get(position).getOwner().getLogin());
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public Repository mRepo;
    public final TextView mRepoName;
    public final TextView mRepoOwner;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mRepoName = (TextView) view.findViewById(R.id.repo_name);
      mRepoOwner = (TextView) view.findViewById(R.id.repo_owner);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + mRepoName.getText() + "'";
    }
  }
}
