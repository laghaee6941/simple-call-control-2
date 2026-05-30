package com.simple.callcontrol

import android.content.Context
import android.telecom.Call
import android.telecom.CallScreeningService

class MyCallService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {

        val number =
            callDetails.handle?.schemeSpecificPart ?: return

        val prefs =
            getSharedPreferences("call_prefs", Context.MODE_PRIVATE)

        val allowed =
            prefs.getStringSet("numbers", emptySet()) ?: emptySet()

        val response = CallResponse.Builder()

        if (allowed.contains(number)) {

            // اجازه تماس
            response.setDisallowCall(false)

        } else {

            // بلاک تماس
            response.setDisallowCall(true)
            response.setRejectCall(true)
        }

        respondToCall(callDetails, response.build())
    }
}
