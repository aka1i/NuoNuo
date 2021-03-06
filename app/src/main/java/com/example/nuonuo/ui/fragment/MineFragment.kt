package com.example.nuonuo.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jpush.im.android.api.JMessageClient
import com.bumptech.glide.Glide
import com.example.mykotlin.base.Preference

import com.example.nuonuo.R
import com.example.nuonuo.marco.Constant
import com.example.nuonuo.ui.activity.CarOwnerActivity
import com.example.nuonuo.ui.activity.FirstActivity
import com.example.nuonuo.ui.activity.SettingActivity
import com.example.nuonuo.utils.HeadImgUtil
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : Fragment(), View.OnClickListener {

    private val name: String by Preference(Constant.USER_NAME_KEY,"")

    private var uid: Int by Preference(Constant.UID_KEY,0)

    private var sexual: String by Preference(Constant.SEXUAL_KEY,"")

    private var headPicUrl: String by Preference(Constant.HEAD_PIC_URL_KEY,"")

    private var phone: String by Preference(Constant.PHONE_KEY,"")

    private var score: Int by Preference(Constant.SCORE_KEY,0)


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
        val options = HeadImgUtil.getHeadImgOptions(sexual)
        Glide.with(this).load(headPicUrl).apply(options).into(head_img)

        userNameText.text = name
        head_img.setOnClickListener(this)
        mine_out_rl.setOnClickListener(this)
        modify_info_rl.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mine_out_rl ->{
                Preference.clear()
                JMessageClient.logout()
                startActivity(Intent(activity,FirstActivity::class.java))
                activity?.finish()
            }
            R.id.head_img ->{
                activity?.run {
                    startActivity(CarOwnerActivity.newIntent(this,uid,name,headPicUrl,phone))
                }
            }
            R.id.modify_info_rl -> {
                activity?.startActivityForResult(Intent(activity,SettingActivity::class.java),Constant.START_SETTING)
            }
        }
    }

    fun refreshSelfData(){
        val options = HeadImgUtil.getHeadImgOptions(sexual)
        Glide.with(this).load(headPicUrl).apply(options).into(head_img)
        userNameText.text = name
        scoreText.text = "积分 $score"
    }

}
