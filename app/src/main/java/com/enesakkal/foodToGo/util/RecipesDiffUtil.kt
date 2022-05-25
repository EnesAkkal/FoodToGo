package com.enesakkal.foodToGo.util

import androidx.recyclerview.widget.DiffUtil

class RecipesDiffUtil<T>(
    private val oldList: List<T>,
    private val newList : List<T>

    ) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
      return oldList.size                                      //gets the size of old news
    }

    override fun getNewListSize(): Int {
       return newList.size                                      //gets the size of new news
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            //Decides  if two objects represent the same Item in the old and new list.
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //checks if the two items have the same data.
        return oldList[oldItemPosition] == newList[newItemPosition]

    }
}