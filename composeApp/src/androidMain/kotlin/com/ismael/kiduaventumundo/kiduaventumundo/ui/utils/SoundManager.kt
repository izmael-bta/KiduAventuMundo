package com.ismael.kiduaventumundo.kiduaventumundo.ui.utils

import android.content.Context
import android.media.SoundPool
import com.ismael.kiduaventumundo.kiduaventumundo.R

class SoundManager(context: Context) {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(4)
        .build()

    private val catSound = soundPool.load(context, R.raw.cat, 1)
    private val dogSound = soundPool.load(context, R.raw.dog, 1)
    //private val bearSound = soundPool.load(context, R.raw.bear, 1)
    private val lionSound = soundPool.load(context, R.raw.lion, 1)

    fun playSound(avatarId: Int) {
        when (avatarId) {
            1 -> soundPool.play(catSound, 1f, 1f, 1, 0, 1f)
            2 -> soundPool.play(dogSound, 1f, 1f, 1, 0, 1f)
           // 3 -> soundPool.play(bearSound, 1f, 1f, 1, 0, 1f)
            4 -> soundPool.play(lionSound, 1f, 1f, 1, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}