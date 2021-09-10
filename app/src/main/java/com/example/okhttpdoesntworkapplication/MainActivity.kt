package com.example.okhttpdoesntworkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.response)
        var count = 0
        val button = findViewById<Button>(R.id.button)
        lifecycleScope.launch {
            val token = AuthApiClient().getAuthToken(GetAuthTokenRequest("goberdovskii.v@info-city.ru", "QQQqqq@2")).responseData?.accessToken

            button.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    token?.let {
                        val user = UserApiClient().getUser(GetUserRequest(token)).responseData
                        val s = "${user?.get(0)?.fullName} $count"
                        textView.text = s
                        count += 1
                    }
                }
            }
        }

    }

}