package kr.co.fastcampus.co.kr.coroutines.ui.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import kr.co.fastcampus.co.kr.coroutines.model.Item

class ImageSearchAdapter(
    private val like: (Item) -> Unit
) : PagingDataAdapter<Item, ImageSearchViewHolder>(comparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSearchViewHolder {
        return ImageSearchViewHolder.create(like, parent)
    }

    override fun onBindViewHolder(holder: ImageSearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        /**
         * areItemsTheSame, areContentsTheSame 을 객체 비교만을해서 판단하면 안됩니다.
         *
         * 동작 :
         * 1. `areItemsTheSame` 이 ture 면 `areContentsTheSame` 을 실행
         * 2. `areContentsTheSame` 은 Item 안의 값이 모두 같아야 true 를 반환
         */
        val comparator = object : DiffUtil.ItemCallback<Item>() {
            // 원래 같은 id 를 가지고 있는지? 특징적이거나 유니크한 값을 주로 비교
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.thumbnail == newItem.thumbnail
            }

            // oldItem 과 newItem 의 값들이 다 같은지?
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}