package com.luk.vibetest

import android.os.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    internal enum class Effect {
        /**
         * A single click effect.
         *
         * This effect should produce a sharp, crisp click sensation.
         */
        CLICK,

        /**
         * A double click effect.
         *
         * This effect should produce two sequential sharp, crisp click sensations with a minimal
         * amount of time between them.
         */
        DOUBLE_CLICK,

        /**
         * A tick effect.
         *
         * This effect should produce a soft, short sensation, like the tick of a clock.
         */
        TICK,

        /**
         * A thud effect.
         *
         * This effect should solid feeling bump, like the depression of a heavy mechanical button.
         */
        THUD,

        /**
         * A pop effect.
         *
         * A short, quick burst effect.
         */
        POP,

        /**
         * A heavy click effect.
         *
         * This should produce a sharp striking sensation, like a click but stronger.
         */
        HEAVY_CLICK,

        /**
         * Ringtone patterns. They may correspond with the device's ringtone audio, or may just be a
         * pattern that can be played as a ringtone with any audio, depending on the device.
         */
        RINGTONE_1, RINGTONE_2, RINGTONE_3, RINGTONE_4, RINGTONE_5, RINGTONE_6, RINGTONE_7, RINGTONE_8, RINGTONE_9, RINGTONE_10, RINGTONE_11, RINGTONE_12, RINGTONE_13, RINGTONE_14, RINGTONE_15,

        /**
         * A soft tick effect meant to be played as a texture.
         *
         * A soft, short sensation like the tick of a clock. Unlike regular effects, texture effects
         * are expected to be played multiple times in quick succession, replicating a specific
         * texture to the user as a form of haptic feedback.
         */
        TEXTURE_TICK
    }

    internal enum class PrimitiveEffect {
        /**
         * No haptic effect. Used to generate extended delays between primitives.
         *
         * Support is required.
         */
        NOOP,
        /**
         * This effect should produce a sharp, crisp click sensation.
         *
         * Support is required.
         */
        CLICK,
        /**
         * A haptic effect that simulates downwards movement with gravity. Often
         * followed by extra energy of hitting and reverberation to augment
         * physicality.
         *
         * Support is optional.
         */
        THUD,
        /**
         * A haptic effect that simulates spinning momentum.
         *
         * Support is optional.
         */
        SPIN,
        /**
         * A haptic effect that simulates quick upward movement against gravity.
         *
         * Support is required.
         */
        QUICK_RISE,
        /**
         * A haptic effect that simulates slow upward movement against gravity.
         *
         * Support is required.
         */
        SLOW_RISE,
        /**
         * A haptic effect that simulates quick downwards movement with gravity.
         *
         * Support is required.
         */
        QUICK_FALL,
        /**
         * This very short effect should produce a light crisp sensation intended
         * to be used repetitively for dynamic feedback.
         *
         * Support is required.
         */
        LIGHT_TICK,
        /**
         * This very short low frequency effect should produce a light crisp sensation intended
         * to be used repetitively for dynamic feedback.
         *
         * Support is required.
         */
        LOW_TICK,
    }
    internal enum class WaveformEffects(val durations: LongArray, val amplitudes: IntArray) {
        DOUBLE_PULSE(
            longArrayOf(50, 50, 50, 50, 50, 100, 350, 25, 25, 25, 25, 200),
            intArrayOf(33, 51, 75, 113, 170, 255, 0, 38, 62, 100, 160, 255)
        ),
        TRIPLE_PULSE(
            longArrayOf(0, 100, 200, 100, 300, 100),
            intArrayOf(255, 0, 128, 0, 255, 0)
        ),
        RAMP_UP(
            longArrayOf(0, 50, 50, 50, 50, 50),
            intArrayOf(0, 50, 100, 150, 200, 255)
        ),
        RAMP_DOWN(
            longArrayOf(0, 50, 50, 50, 50, 50),
            intArrayOf(255, 200, 150, 100, 50, 0)
        ),
        HEARTBEAT(
            longArrayOf(0, 50, 100, 50, 300),
            intArrayOf(255, 0, 255, 0, 0)
        ),
        BUZZ(
            longArrayOf(0, 200, 100, 200),
            intArrayOf(255, 0, 255, 0)
        ),
        LONG_BUZZ(
            longArrayOf(0, 500),
            intArrayOf(255, 0)
        ),
        SHORT_PULSE(
            longArrayOf(0, 50, 100, 50, 100, 50),
            intArrayOf(255, 0, 255, 0, 255, 0)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 10L..150L step 10L) {
            val button = Button(this)
            button.text = "${i}ms"
            button.setOnClickListener { _ ->
                val vib = getSystemService(Vibrator::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    vib.vibrate(
                        VibrationEffect.createOneShot(i, VibrationEffect.DEFAULT_AMPLITUDE),
                        VibrationAttributes.createForUsage(VibrationAttributes.USAGE_MEDIA)
                    )
                } else {
                    vib.vibrate(VibrationEffect.createOneShot(i, VibrationEffect.DEFAULT_AMPLITUDE))
                }
            }

            val layout = findViewById<View>(R.id.layout) as LinearLayout
            layout.addView(
                button,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }

        enumValues<Effect>().forEach {
            val button = Button(this)
            button.text = it.name
            button.setOnClickListener { _ ->
                val vib = getSystemService(Vibrator::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    vib.vibrate(
                        VibrationEffect.createPredefined(it.ordinal),
                        VibrationAttributes.createForUsage(VibrationAttributes.USAGE_MEDIA)
                    )
                } else {
                    vib.vibrate(VibrationEffect.createPredefined(it.ordinal))
                }
            }

            val layout = findViewById<View>(R.id.layout) as LinearLayout
            layout.addView(
                button,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
        enumValues<PrimitiveEffect>().forEach {
            val button = Button(this)
            button.text = it.name
            button.setOnClickListener { _ ->
                val vib = getSystemService(Vibrator::class.java)
                vib.vibrate(
                    VibrationEffect.startComposition().addPrimitive(
                        it.ordinal).compose()
                )
            }

            val layout = findViewById<View>(R.id.layout) as LinearLayout
            layout.addView(
                button,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
        enumValues<WaveformEffects>().forEach {
            val button = Button(this)
            button.text = it.name
            button.setOnClickListener { _ ->
                val vib = getSystemService(Vibrator::class.java)
                vib.vibrate(
                    VibrationEffect.createWaveform(it.durations, it.amplitudes, -1)
                )
            }

            val layout = findViewById<View>(R.id.layout) as LinearLayout
            layout.addView(
                button,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_keyboard -> {
                @Suppress("DEPRECATION")
                getSystemService(InputMethodManager::class.java)?.toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    0
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}