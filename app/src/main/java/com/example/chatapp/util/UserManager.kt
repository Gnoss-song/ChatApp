package com.example.chatapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.fragment.app.FragmentActivity
import com.example.chatapp.api.AuthInterceptor
import com.example.chatapp.model.entity.Member
import com.example.chatapp.view.login.LoginDialog

class UserManager(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val ISLOGIN = "islogin"
        const val KEY_MEMBER_ID = "memberId"
        const val KEY_PROFILE_URL = "profileUrl"
        const val KEY_USER_NAME = "userName"
        const val DEFAULT_MEMBER_ID = 0L
        const val GUEST = "Guest"
    }


    var isLogin = sharedPreferences.getBoolean(ISLOGIN, false)
    var userName: String? = sharedPreferences.getString(KEY_USER_NAME, "")
        set(value) {
            sharedPreferences.edit {
                putString(KEY_USER_NAME, value)
            }
            field = value
        }

    var memberId: Long = DEFAULT_MEMBER_ID
        set(value) {
            sharedPreferences.edit {
                putLong(KEY_MEMBER_ID, value)
            }
            field = value
        }
    var profileUrl: String? = null
        set(value) {
            sharedPreferences.edit {
                putString(KEY_PROFILE_URL, value)
            }
            field = value
        }

    fun login() {
        val isLogin = true
        login(isLogin)
    }


    private fun login(
        isLogin: Boolean
    ) {
        this.isLogin = isLogin
        sharedPreferences.edit {
            putBoolean(ISLOGIN, isLogin)
        }
    }

    fun setMemberInfo(member: Member) {
        memberId = member.id
        userName = member.username
        profileUrl = member.avatarUrl
    }

    private fun initMemberInfo() {
        memberId = DEFAULT_MEMBER_ID
        userName = GUEST
        profileUrl = ""
    }

    fun logout() {
        initMemberInfo()
        login(false)
    }

    fun checkLogin(activity: FragmentActivity): Boolean {
        return if (!isLogin) {
            val frag = LoginDialog()
            frag.show(activity.supportFragmentManager, frag.tag)
            false
        } else {
            true
        }
    }
}
