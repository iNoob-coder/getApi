package com.example.assment.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assment.ViewModelClass
import com.example.assment.adapter.Adapter
import com.example.assment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Adapter.ItemClicked {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewModelClass::class.java]


        viewModel.isInternetAvailable.observe(this@MainActivity) {isInternetAvailable ->
            if (!isInternetAvailable) {
                viewModel.showToastIfNoInternet()
            } else {
                viewModel.getPostData().observe(this) {
                    if (it != null) {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.recyclerView.apply {
                            adapter = Adapter(it, this@MainActivity)
                            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                        }
                    }
                }
            }
        }


    }

    override fun onItemClicked(id: Int) {
        if (id == 1) {
            Toast.makeText(this, "${id}st item clicked", Toast.LENGTH_SHORT).show()
        } else if (id == 2) {
            Toast.makeText(this, "${id}nd item clicked", Toast.LENGTH_SHORT).show()
        } else if (id == 3) {
            Toast.makeText(this, "${id}rd item clicked", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "${id}th item clicked", Toast.LENGTH_SHORT).show()
        }

    }
}