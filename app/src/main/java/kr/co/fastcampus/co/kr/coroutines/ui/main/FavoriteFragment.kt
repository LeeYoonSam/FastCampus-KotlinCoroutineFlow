package kr.co.fastcampus.co.kr.coroutines.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kr.co.fastcampus.co.kr.coroutines.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

	private lateinit var imageSearchViewModel: ImageSearchViewModel

	private val adapter = FavoriteAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		imageSearchViewModel = ViewModelProvider(requireActivity())[ImageSearchViewModel::class.java]
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
		val root = binding.root

		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = GridLayoutManager(context, 4)

		viewLifecycleOwner.lifecycleScope.launchWhenResumed {
			imageSearchViewModel.favoritesFlow.collectLatest {
				adapter.setItems(it)
			}
		}

		return root
	}

	companion object {

	}
}