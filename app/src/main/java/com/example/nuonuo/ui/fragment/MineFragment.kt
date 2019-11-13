package com.example.nuonuo.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mykotlin.base.Preference

import com.example.nuonuo.R
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.ui.activity.CarOwnerActivity
import com.example.nuonuo.ui.activity.FirstActivity
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : Fragment(), View.OnClickListener {

    private val name: String by Preference(Constant.USER_NAME_KEY,"")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        userNameText.text = name
        head_img.setOnClickListener(this)
        mine_out_rl.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mine_out_rl ->{
                Preference.clear()
                startActivity(Intent(activity,FirstActivity::class.java))
                activity?.finish()
            }
            R.id.head_img ->{
                startActivity(Intent(activity,CarOwnerActivity::class.java))
            }
        }
    }
}
