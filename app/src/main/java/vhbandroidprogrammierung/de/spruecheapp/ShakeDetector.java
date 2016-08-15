package vhbandroidprogrammierung.de.spruecheapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Patrick PC on 15.08.2016.
 */
public class ShakeDetector implements SensorEventListener {

    /*
     * The gForce that is necessary to register as shake.
     * Must be greater than 1G (one earth gravity unit).
     * You can install "G-Force", by Blake La Pierre
     * from the Google Play Store and run it to see how
     *  many G's it takes to register a shake
     */
    private static final String TAG = "ShakeDetector";
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 350;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 1800;
    private static final int ENOUGH_SHAKES = 2;

    private OnShakeListener onShakeListener;
    private long shakeTimestamp;
    private int shakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.onShakeListener = listener;
    }

    public interface OnShakeListener {
        public void onShake();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nichts tun
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (onShakeListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // gForce an Erdgravitation anpassen
            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                // Schüttler, die zu nah aneinander liegen, ignorieren
                if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // Zähler zurücksetzen, wenn zu viel Zeit dazwischen liegt
                if (shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    shakeCount = 0;
                }

                shakeTimestamp = now;
                shakeCount++;
                Log.i(TAG, "onSensorChanged: shake event");

                // Bei genug events den listener aufrufen
                if (shakeCount > ENOUGH_SHAKES) {
                    onShakeListener.onShake();
                    shakeCount = 0;
                }
            }
        }
    }
}