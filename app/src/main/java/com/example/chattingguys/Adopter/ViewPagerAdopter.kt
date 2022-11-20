package com.example.chattingguys.Adopter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdopter (private val context : Context, manager: FragmentManager?, val list : ArrayList<Fragment>) : FragmentPagerAdapter(manager!!){

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
           return TABTITAL[position]
    }

    companion object{

        val TABTITAL = arrayOf("Chat","Stutes","Call")

    }

}