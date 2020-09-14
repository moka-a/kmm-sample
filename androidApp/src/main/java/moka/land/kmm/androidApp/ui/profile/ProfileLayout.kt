package moka.land.kmm.androidApp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import moka.land.androidApp.R
import moka.land.androidApp.databinding.LayoutProfileBinding
import moka.land.kmm.androidApp.ui.main._BlankFragmentDirections
import moka.land.kmm.androidApp.ui.profile.adapter.OverviewAdapter
import moka.land.ui.profile.adapter.RepositoryAdapter


data class Profile(var name: String)
data class Pinned(var name: String, var description: String)
data class Organizer(var name: String, var description: String, var avatarUrl: String)
data class Repository(var name: String, var description: String)

enum class Tab {
    Overview, Repositories;

    companion object {
        fun get(index: Int): Tab = values().filter { it.ordinal == index }[0]
    }
}

class ProfileLayout : Fragment() {

    private val _view by lazy { LayoutProfileBinding.inflate(layoutInflater) }

    private val overviewAdapter by lazy { OverviewAdapter() }
    private val repositoryAdapter by lazy { RepositoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        init()
        bindEvent()
        bindViewModel()

        lifecycleScope.launch {
            // loadData
        }
        return _view.root
    }

    private fun init() {
//        viewModel.selectedTab.value = Tab.Overview

        _view.recyclerViewOverview.showPlaceHolder(R.layout.view_overview_placeholder)
        _view.recyclerViewOverview.adapter = overviewAdapter
        _view.recyclerViewRepositories.adapter = repositoryAdapter
    }

    private fun bindEvent() {
        _view.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (Tab.get(tab?.position ?: 0)) {
                    Tab.Overview -> {
//                        viewModel.selectedTab.value = Tab.Overview
                    }
                    Tab.Repositories -> {
                        lifecycleScope.launch {
                            _view.recyclerViewRepositories.scrollToPosition(0)

//                            viewModel.selectedTab.value = Tab.Repositories
//                            viewModel.myRepositoryList.value = arrayListOf()
//                            viewModel.reloadRepositories()
                        }
                    }
                }
            }
        })

        _view.recyclerViewRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

//                if (!viewModel.loading.value) {
//                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() >= repositoryAdapter.itemCount - 2) {
//                        lifecycleScope.launch {
//                            viewModel.loadRepositories()
//                        }
//                    }
//                }
            }
        })

        overviewAdapter.onClickItem = {
            if (it.type == OverviewAdapter.Type.PINNED && null != it.repository) {
                val direction = _BlankFragmentDirections.goRepository(it.repository!!.name)
                findNavController().navigate(direction)
            }
        }

        repositoryAdapter.onClickItem = {
            val direction = _BlankFragmentDirections.goRepository(it.repository.name)
            findNavController().navigate(direction)
        }
    }

    private fun bindViewModel() {
//        viewModel.selectedTab.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                Tab.Overview -> {
//                    _view.recyclerViewOverview.visibleFadeIn(150)
//                    _view.recyclerViewRepositories.goneFadeOut(150)
//                }
//                Tab.Repositories -> {
//                    _view.recyclerViewOverview.goneFadeOut(150)
//                    _view.recyclerViewRepositories.visibleFadeIn(150)
//                }
//            }
//        })
//
//        viewModel.profile.observe(viewLifecycleOwner, Observer {
//            _view.textViewName.text = it.name()
//            _view.textViewBio.text = it.bio()
//            _view.textViewStatus.text = "\"${it.status()?.message()}\""
//            _view.imageViewProfileImage.load(activity!!, "${it.avatarUrl()}")
//
//            _view.run {
//                listOf(view01, view02, view03).forEach { it.gone() }
//            }
//        })
//
//        combineWith(viewModel.pinnedList, viewModel.organizerList) { pinnedList, organizerList ->
//            if (null == pinnedList || null == organizerList) {
//                return@combineWith
//            }
//            _view.recyclerViewOverview.hidePlaceHolder(200)
//
//            val items = pinnedList
//                .asSequence()
//                .map { OverviewAdapter.Data(type = OverviewAdapter.Type.PINNED, repository = it) }
//                .plus(
//                    listOf(OverviewAdapter.Data(type = OverviewAdapter.Type.ORGANIZER_SECTION))
//                )
//                .plus(
//                    organizerList
//                        .map { OverviewAdapter.Data(type = OverviewAdapter.Type.ORGANIZER, organizer = it) }
//                        .toList()
//                )
//                .toList()
//            overviewAdapter.setItems(items)
//        }.observe(viewLifecycleOwner, Observer {})
//
//        viewModel.myRepositoryList.observe(viewLifecycleOwner, Observer { repoList ->
//            if (repoList.isEmpty()) {
//                repositoryAdapter.items.clear()
//                repositoryAdapter.notifyDataSetChanged()
//                return@Observer
//            }
//            repositoryAdapter.replaceItems(repoList.map { RepositoryAdapter.Data(it) })
//        })
//
//        viewModel.loading.observe(viewLifecycleOwner, Observer {
//            when (viewModel.selectedTab.value) {
//                Tab.Overview -> {
//                }
//                Tab.Repositories -> {
//                    if (it) {
//                        repositoryAdapter.showFooterLoading()
//                    }
//                    else {
//                        repositoryAdapter.hideFooterLoading()
//                    }
//                }
//            }
//        })
//
//        viewModel.error.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                Error.CONNECTION -> {
//                    _view.recyclerViewOverview.hidePlaceHolder(200)
//
//                    _view.textViewError.visible()
//                    _view.textViewError.text = "인터넷 연결을 확인해주세요 :D"
//                }
//                Error.SERVER -> {
//                    _view.recyclerViewOverview.hidePlaceHolder(200)
//
//                    _view.textViewError.visible()
//                    _view.textViewError.text = "예상치 못한 에러입니다 :(\n\napikey.properties 파일의 GitHub api key 를 확인해주세요\nread:org 권한을 포함 하여야 합니다."
//                }
//                Error.NOPE -> {
//                    _view.textViewError.gone()
//                }
//            }
//        })
    }

}
