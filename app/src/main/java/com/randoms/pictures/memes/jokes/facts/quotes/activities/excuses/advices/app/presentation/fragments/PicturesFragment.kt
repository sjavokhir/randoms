package com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.R
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions.grid
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions.repeatingJobOnStarted
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.core.extensions.start
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.data.model.ListModel
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.databinding.FragmentListBinding
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.activities.PictureActivity
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.adapters.ListItemListener
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.adapters.ListPhotoAdapter
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.navigation.NavArguments
import com.randoms.pictures.memes.jokes.facts.quotes.activities.excuses.advices.app.presentation.viewmodels.ListViewModel

@AndroidEntryPoint
class PicturesFragment : Fragment(R.layout.fragment_list), ListItemListener {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel by viewModels<ListViewModel>()

    private val picturesAdapter by lazy { ListPhotoAdapter(requireContext(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPictures()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvList.grid().adapter = picturesAdapter

        repeatingJobOnStarted { viewModel.list.collectLatest { picturesAdapter.submitList(it) } }
    }

    override fun onItemClick(model: ListModel) {
        NavArguments.model = model
        start(PictureActivity::class.java)
    }
}