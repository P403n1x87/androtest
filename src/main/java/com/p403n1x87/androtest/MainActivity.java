package com.p403n1x87.androtest;

import android.app.Activity;
import android.os.Bundle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.view.ViewGroup.LayoutParams;

import android.text.Html;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.List;

import static java.lang.Math.sqrt;


public class MainActivity extends Activity
{
  private SensorManager mSensorManager;
  private Sensor        mSensor;

  private TextView      text       = null;
  private LinearLayout  layoutMain = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // UI
    setContentView(R.layout.main_layout);
    if (text == null)       text       = (TextView)     findViewById(R.id.text);
    if (layoutMain == null) layoutMain = (LinearLayout) findViewById(R.id.layout_main);

    // Sensors
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
      List<Sensor> gravSensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
      int nSensors = gravSensors.size();
      text.setText(getString(R.string.sensors_no, nSensors));

      for (int i = 0; i < nSensors; i++) {
        final TextView tvSensor = new TextView(this);
        final int      j        = i + 1;

        tvSensor.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        mSensor = gravSensors.get(i);
        mSensorManager.registerListener(new SensorEventListener() {
          @Override
          public void onSensorChanged(final SensorEvent event) {
    		    float x = event.values[0];
    		    float y = event.values[1];
    		    float z = event.values[2];
    		    float g = (float) sqrt(x*x + y*y + z*z);

            tvSensor.setText(Html.fromHtml(getString(R.string.sensor_val, j, g)));
          }

          @Override
          public void onAccuracyChanged(Sensor sensor, int a) {}
        }, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        layoutMain.addView(tvSensor);
      }
    }
    else {
      text.setText(R.string.no_sensors);
    }
  }

}
