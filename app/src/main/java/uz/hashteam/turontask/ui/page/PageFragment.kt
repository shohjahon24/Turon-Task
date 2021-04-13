package uz.hashteam.turontask.ui.page

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.hashteam.turontask.R
import uz.hashteam.turontask.data.page.Page
import uz.hashteam.turontask.databinding.FragmentPageBinding
import uz.hashteam.turontask.list.page.PageAdapter
import uz.hashteam.turontask.ui.main.DownloadedFragment
import uz.hashteam.turontask.ui.main.DownloadingFragment
import uz.hashteam.turontask.ui.main.MainFragment

class PageFragment : Fragment(R.layout.fragment_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPageBinding.bind(view)
        val adapter = PageAdapter(childFragmentManager)
        val mainFragment = MainFragment()
        val downloadedFragment = DownloadedFragment()
        val downloadingFragment = DownloadingFragment()
        val pages = arrayListOf(
            Page(downloadedFragment, getString(R.string.downloaded)),
            Page(mainFragment, getString(R.string.all)),
            Page(downloadingFragment, getString(R.string.downloading))
        )
        binding.tabLog.setupWithViewPager(binding.pager)
        binding.pager.adapter = adapter
        adapter.setData(pages)
        binding.pager.setCurrentItem(1, false)
    }
}
