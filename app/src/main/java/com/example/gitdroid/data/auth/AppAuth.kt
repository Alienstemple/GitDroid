package com.example.gitdroid.data.auth

import net.openid.appauth.ResponseTypeValues

object AppAuth {


    private object AuthConfig {
        const val AUTH_URI = "https://github.com/login/oauth/authorize"
        const val TOKEN_URI = "https://github.com/login/oauth/access_token"
        const val END_SESSION_URI = "https://github.com/logout"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "user,repo"

        const val CLIENT_ID = "6705c8c0e05b7f836e8c"
        const val CLIENT_SECRET = "4e610008ce99bb6469b96b1580e546f201fb79af"
        const val CALLBACK_URL = "com.example.gitdroid.oauth://github.com/callback"
        const val LOGOUT_CALLBACK_URL = "ru.kts.oauth://github.com/logout_callback"
    }
}