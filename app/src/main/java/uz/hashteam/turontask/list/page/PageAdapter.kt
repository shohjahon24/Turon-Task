package uz.hashteam.turontask.list.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.hashteam.turontask.data.page.Page

class PageAdapter constructor(
    fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(
        fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private var data: ArrayList<Page> = ArrayList()

    fun setData(data: ArrayList<Page>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].title
    }

    override fun getItem(position: Int): Fragment {
        return data[position].fragment
    }

}