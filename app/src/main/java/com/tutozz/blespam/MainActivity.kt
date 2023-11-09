package com.tutozz.blespam

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tutozz.blespam.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun onClickSpamButton(spammer: Spammer, button: Button, circle: ImageView){
        button.setOnClickListener {
            if(!spammer.isSpamming){
                spammer.start()
                // blink animation
                circle.setImageResource(R.drawable.active_circle)
                if(spammer.blinkRunnable == null){ spammer.blinkRunnable = startBlinking(circle, spammer); }
                // button style
                button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.orange)
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
            }else{
                spammer.stop()
                // blink animation
                circle.setImageResource(R.drawable.grey_circle)
                // button style restore
                button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.empty)
                button.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // setup global blink animation

        // Ask missing permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((this as Activity?)!!, arrayOf(Manifest.permission.BLUETOOTH), 1)
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((this as Activity?)!!, arrayOf(Manifest.permission.BLUETOOTH_ADMIN), 1)
        }else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { ActivityCompat.requestPermissions((this as Activity?)!!, arrayOf(Manifest.permission.BLUETOOTH_ADVERTISE),  1) }
        }

        if(Helper.isPermissionGranted(this)){
            // Setup click listener
            // iOS 17 crash
            onClickSpamButton(ContinuitySpam(ContinuityDevice.type.ACTION, true), binding.ios17CrashButton, binding.ios17CrashCircle)
            // Apple Action Modal
            onClickSpamButton(ContinuitySpam(ContinuityDevice.type.ACTION, false), binding.appleActionModalButton, binding.appleActionModalCircle)
            // Apple Device Modal
            onClickSpamButton(ContinuitySpam(ContinuityDevice.type.DEVICE, false), binding.appleDevicePopupButton, binding.appleDevicePopupCircle)
            // Android FastPair
            onClickSpamButton(FastPairSpam(), binding.androidFastPairButton, binding.androidFastPairCircle)
            // Samsung EasyPair Buds
            onClickSpamButton(EasySetupSpam(EasySetupDevice.type.BUDS), binding.samsungEasyPairBudsButton, binding.samsungEasyPairBudsCircle)
            // Samsung EasyPair Watch
            onClickSpamButton(EasySetupSpam(EasySetupDevice.type.WATCH), binding.samsungEasyPairWatchButton, binding.samsungEasyPairWatchCircle)
            // Windows SwiftPair
            binding.windowsSwiftPairButton.setOnClickListener {
                Toast.makeText(this, "Not Implemented yet", Toast.LENGTH_SHORT).show()
            }

            // Delay Buttons onClick
            binding.minusDelayButton.setOnClickListener {
                val i = Helper.delays.indexOf(Helper.delay)
                if(i > 0){
                    Helper.delay = Helper.delays[i-1]
                    binding.delayText.text = Helper.delay.toString() + "ms"
                }
            }
            binding.plusDelayButton.setOnClickListener {
                val i = Helper.delays.indexOf(Helper.delay)
                if(i < Helper.delays.size - 1){
                    Helper.delay = Helper.delays[i+1]
                    binding.delayText.text = Helper.delay.toString() + "ms"
                }
            }
        }
    }

    // blink animation
    private fun startBlinking(imageView: ImageView, spammer: Spammer): Runnable {
        val handler = Handler(Looper.getMainLooper())
        val blinkRunnable: Runnable = object : Runnable {
            override fun run() {
                if(spammer.isSpamming) {
                    if (imageView.visibility == View.VISIBLE) {
                        imageView.visibility = View.INVISIBLE
                        handler.postDelayed(this, Helper.delay.toLong()) // Blink for delay
                    } else {
                        imageView.visibility = View.VISIBLE
                        handler.postDelayed(this, (Helper.delay / 10).coerceAtLeast(20).toLong())
                    }
                }else{
                    imageView.visibility = View.VISIBLE
                    handler.postDelayed(this, 200) // check if need to blink every 200ms
                }
            }
        }
        handler.postDelayed(blinkRunnable, 100) // ext
        return blinkRunnable
    }
}

