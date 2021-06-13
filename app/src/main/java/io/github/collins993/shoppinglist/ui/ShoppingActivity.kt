package io.github.collins993.shoppinglist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.collins993.shoppinglist.R
import io.github.collins993.shoppinglist.data.db.ShoppingDatabase
import io.github.collins993.shoppinglist.data.db.entities.ShoppingItem
import io.github.collins993.shoppinglist.data.repositorys.ShoppingRepository
import io.github.collins993.shoppinglist.other.ShoppingItemAdapter
import io.github.collins993.shoppinglist.ui.shoppinglist.AddDialogListener
import io.github.collins993.shoppinglist.ui.shoppinglist.AddShoppingItemDialog
import io.github.collins993.shoppinglist.ui.shoppinglist.ShoppingViewModel
import io.github.collins993.shoppinglist.ui.shoppinglist.ShoppingViewModelFactory
import kotlinx.android.synthetic.main.activity_shopping.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity(){

//    override val kodein by kodein()
//
//    private val factory: ShoppingViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val database = ShoppingDatabase(this)
        val repository = ShoppingRepository(database)
        val factory = ShoppingViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(), viewModel)

        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        fab.setOnClickListener {
            AddShoppingItemDialog(this, object : AddDialogListener{
                override fun onAddButttonClicked(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            }).show()
        }
    }
}