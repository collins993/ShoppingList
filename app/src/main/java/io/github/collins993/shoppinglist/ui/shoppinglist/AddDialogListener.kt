package io.github.collins993.shoppinglist.ui.shoppinglist

import io.github.collins993.shoppinglist.data.db.entities.ShoppingItem

interface AddDialogListener {

    fun onAddButttonClicked(item: ShoppingItem)
}