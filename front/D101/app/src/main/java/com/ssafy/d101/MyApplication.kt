package com.ssafy.d101

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        var keyHash = Utility.getKeyHash(this)
        print(keyHash)

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}