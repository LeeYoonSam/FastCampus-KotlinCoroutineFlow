package kr.co.fastcampus.co.kr.coroutines.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kr.co.fastcampus.co.kr.coroutines.data.NaverImageSearchRepository
import kr.co.fastcampus.co.kr.coroutines.model.Item

/**
 * 참고
 *
 * SharedFlow -> Flow 콜드 collect 값을. SharedFlow 핫 플로우.
 *
 * 액티비티와 뷰모델끼리 데이터를 공유하기 위해 SharedFlow 를 사용
 * queryFlow - 사용자가 입력한 검색어를 이용해서 검색을 하기위한 용도
 */
class ImageSearchViewModel : ViewModel() {
    private val repository = NaverImageSearchRepository()
    private val queryFlow = MutableSharedFlow<String>()
    private val favorites = mutableSetOf<Item>()
    // 핫 스트림, 구독하지 않고 있어도 값을 계속 내보내고 있습니다.
    // replay - 새 구독자에게 재생되는 값의 수입니다(음수일 수 없으며 기본값은 0).
    private val _favoritesFlow = MutableSharedFlow<List<Item>>(replay = 1)

    /**
     * 1. 사용자가 키워드를 입력하면 queryFlow 키워드가 흘러 들어옵니다
     * 2. flow 가 변경될때마다 이미지 검색결과를 가져오고 이전 결과를 날립니다
     * 3. 결과가 PagingDataFlow 라는 하나의 Flow
     * 4. 이 flow 로 어댑터를 업데이트를 해야 합니다
     */
    val pagingDataFlow = queryFlow
        // 검색어가 변경되면 다시 검색을위해 flatMap 사용
        .flatMapLatest {
            searchImages(it)
        }
        // 처리 결과를 캐시를 통해서 뷰모델 스코프에 저장
        .cachedIn(viewModelScope)

    // 값을 변경할수 없는 SharedFlow 로 변경
    val favoritesFlow = _favoritesFlow.asSharedFlow()

    private fun searchImages(query: String): Flow<PagingData<Item>> =
        repository.getImageSearch(query)

    fun handleQuery(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }

    fun toggle(item: Item) {
        if (favorites.contains(item)) {
            favorites.remove(item)
        } else {
            favorites.add(item)
        }
        viewModelScope.launch {
            _favoritesFlow.emit(favorites.toList())
        }
    }
}