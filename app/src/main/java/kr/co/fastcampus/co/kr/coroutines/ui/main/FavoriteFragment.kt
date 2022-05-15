package kr.co.fastcampus.co.kr.coroutines.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.co.fastcampus.co.kr.coroutines.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

	private lateinit var imageSearchViewModel: ImageSearchViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		imageSearchViewModel = ViewModelProvider(this)[ImageSearchViewModel::class.java]
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
		val root = binding.root

		return root
	}

	companion object {

	}
}