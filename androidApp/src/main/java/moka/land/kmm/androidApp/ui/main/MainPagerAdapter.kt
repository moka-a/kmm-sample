package moka.land.kmm.androidApp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import moka.land.androidApp.R
import moka.land.kmm.androidApp.ui.home.HomeLayout
import moka.land.kmm.androidApp.ui.profile.ProfileLayout


class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    var items: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    fun addItems(vararg fragments: Fragment) {
        fragments.forEach {
            if (!items.contains(it)) {
                items.add(it)
            }
        }
        notifyDataSetChanged()
    }

    fun getItemInt(menuId: Int): Int {
        val className = when (menuId) {
            R.id.homeLayout -> HomeLayout::class.java.simpleName
            R.id.profileLayout -> ProfileLayout::class.java.simpleName
            else -> ""
        }
        return items.indexOf(items.filter { it.javaClass.simpleName == className }[0])
    }

}
