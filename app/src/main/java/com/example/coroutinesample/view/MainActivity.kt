package com.example.coroutinesample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.coroutinesample.R
import com.example.coroutinesample.databinding.ActivityMainBinding
import com.example.coroutinesample.vm.UniversitiesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private var universitiseAdapter = UniversitiesAdapter(arrayListOf())
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(UniversitiesViewModel::class.java)
    }
    private lateinit var myLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvList.apply {
            myLayoutManager = LinearLayoutManager(context)
            layoutManager = myLayoutManager
            adapter = universitiseAdapter
        }
        binding.btnStop.setOnClickListener {
            viewModel.job?.cancel()
        }

        observeData()
        viewModel.getUniversities()
    }

    private fun observeData() {
        //подписка на получение элементов из реста
        lifecycleScope.launchWhenStarted {
            viewModel.university.collect {
                showView(binding.rvList, true)
                if (!it.name.isNullOrBlank()) {
                    Log.e("TEST", it.name ?: "null")
                    universitiseAdapter.updateDataSet(it)
                    myLayoutManager.scrollToPosition(universitiseAdapter.itemCount)
                }
            }
        }

        //подписка на отображение лоадера
        lifecycleScope.launchWhenStarted {
            viewModel.loading.collect {
                showView(binding.pbProgress, it)
            }
        }

        //подписка на отображение снека с ошибкой
        lifecycleScope.launchWhenStarted {
            viewModel.loadError.collect {
                if (it.isNotBlank())
                    Snackbar.make(binding.root, it, 2000).show()
            }
        }
    }

    private fun showView(view: View, show: Boolean) {
        if (show)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

}