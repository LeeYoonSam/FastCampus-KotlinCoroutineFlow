package kr.co.fastcampus.co.kr.coroutines.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kr.co.fastcampus.co.kr.coroutines.api.NaverImageSearchService
import kr.co.fastcampus.co.kr.coroutines.model.Item
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NaverImageSearchRepository {
    private val service: NaverImageSearchService

    init {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    // 실제 구현시에는 소스코드에는 키를 넣지 않고 외부에 id, secret 을 두고 가져와서 사용하는 방식으로 상요하는것이 좋음
                    .addHeader("X-Naver-Client-Id", "yEzEjl1UXYL_P560z2KW")
                    .addHeader("X-Naver-Client-Secret", "MLNGONj0h9")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logger)
            .build()

        service = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverImageSearchService::class.java)
    }

    fun getImageSearch(query: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NaverImageSearchDataSource.defaultDisplay,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NaverImageSearchDataSource(query, service)
            }
        ).flow
    }
}