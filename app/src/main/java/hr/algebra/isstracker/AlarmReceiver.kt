package hr.algebra.isstracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val plantEmoji = String.format("\uD83C\uDF31")
        Toast.makeText(context, "Touch Grass $plantEmoji", Toast.LENGTH_LONG).show()
    }
}