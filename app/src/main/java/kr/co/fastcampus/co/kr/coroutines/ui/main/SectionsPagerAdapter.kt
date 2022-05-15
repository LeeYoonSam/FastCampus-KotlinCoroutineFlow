package kr.co.fastcampus.co.kr.coroutines.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    /**
     * createFragment 는 상황에 따라 필요한것을 만들어내고 캐싱을 하게 되어 있습니다.
     *
     * ```
     *  val imageSearchFragment = ImageSearchFragment()
     * ```
     * 이런식으로 변수를 만들어서 할당하게 되면 항상 만들어져 있기 때문에 곤란한 상황이 생깁니다.
     * 생성과 소멸은 페이저 어댑터에게 맡깁니다.
     */
    override fun createFragment(position: Int): Fragment {
        return ImageSearchFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}