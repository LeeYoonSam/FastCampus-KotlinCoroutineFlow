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
import kotlinx.coroutines.launch
import kr.co.fastcampus.co.kr.coroutines.databinding.FragmentMainBinding

class ImageSearchFragment : Fragment() {

    private lateinit var imageSearchViewModel: ImageSearchViewModel
    private val adapter: ImageSearchAdapter = ImageSearchAdapter {
        imageSearchViewModel.toggle(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSearchViewModel = ViewModelProvider(requireActivity())[ImageSearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        /**
         * Fragment 에서는 viewLifecycleOwner 에서 lifecycleScope 를 가져와야 합니다.
         *
         * lifecycleScope VS viewLifecycleOwner.lifecycleScope
         *
         *  - lifecycleScope
         *      - 프래그먼트의 라이프사이클에 의존 합니다
         *      - 프래그먼트가 화면에 보이지 않을때도 lifecycleScope 가 남아있을수 있습니다.
         *
         *  - viewLifecycleOwner.lifecycleScope
         *      - 뷰의 라이프사이클에 의존 합니다.
         *      - 프래그먼트에서 보여지는것이 더이상 의미가 없을때 viewLifecycleOwner.lifecycleScope 이 취소가 될것으로 프래그먼트에서 사용하기에 더 적절합니다.
         */
        viewLifecycleOwner.lifecycleScope.launch {
            imageSearchViewModel.pagingDataFlow
                // 질의어, 새로 더 많은 데이터를 가져올 경우 조금씩 데이터가 변경되기 때문에 마지막 데이터만 사용하기 위해 collectLatest 를 사용
                .collectLatest { items ->
                    adapter.submitData(items)
                }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.search.setOnClickListener {
            val query = binding.editText.text.trim().toString()
            imageSearchViewModel.handleQuery(query)
        }

        return root
    }
}