package com.discount.app.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.views.ui.activities.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by Avinash Kumar on 22/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class DiscountMessagingService: FirebaseMessagingService() {
    private val TAG: String = DiscountMessagingService::class.java.simpleName

    override fun onMessageReceived(mRemoteMessage: RemoteMessage?) {
        MyLog.i(TAG,  "Remote message received")
        pushNotification(mRemoteMessage?.data?.get("title"),mRemoteMessage?.data?.get("body"),mRemoteMessage?.data?.get("reference_id"))
    }

    private fun pushNotification(mTitle: String?, msg: String?,refId: String?) {
        val iUniqueId = (System.currentTimeMillis() and 0xfffffff).toInt()
        val mBundle = Bundle()
        mBundle.putString(Constants.KEY_COUPON_ID_EXTRA,refId)
        val pendingIntent = PendingIntent.getActivity(this, iUniqueId,
            Intent(this, HomeActivity::class.java)
                .putExtra(Constants.KEY_BUNDLE_PARAM,mBundle)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mChannelId = "store_alert_01"
        val name = "Store Alert"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationBuilder = NotificationCompat.Builder(this, mChannelId)
            .setSmallIcon(R.drawable.ic_stat_discount)
            .setContentTitle(mTitle)
            .setContentText(msg)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        try {
            var mChannel: NotificationChannel? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel = NotificationChannel(mChannelId, name, importance)
                mChannel.setShowBadge(true)
                mChannel.enableLights(true)
                notificationManager.createNotificationChannel(mChannel)
            }
        } catch (e: NoClassDefFoundError) {
            MyLog.e(TAG,"Error",e)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}