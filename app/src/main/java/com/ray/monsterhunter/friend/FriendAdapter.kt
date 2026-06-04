package com.ray.monsterhunter.friend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ray.monsterhunter.friend.item.FriendItemFragment


class FriendAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return FriendItemFragment(FriendTypeFilter.entries[position])
    }

    override fun getCount() = FriendTypeFilter.entries.size

    override fun getPageTitle(position: Int): CharSequence? {
        return FriendTypeFilter.entries[position].value
    }
}
