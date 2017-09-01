package com.p403n1x87.androtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.widget.TextView;

import java.util.List;

import static java.lang.Math.sqrt;


public class MainActivity extends Activity implements SensorEventListener
{

  private SensorManager mSensorManager;
  private Sensor        mSensor;

  private TextView      text = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_layout);
    if (text == null) text = (TextView) findViewById(R.id.text);

    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
      List<Sensor> gravSensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
      Log.i("AndroTest", "There are " + Integer.toString(gravSensors.size()) + " grav sensors");
      mSensor = gravSensors.get(0);
      text.setText("We have gravity! :D :D :D");
      mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    else {
      text.setText("We DO NOT have gravity! :(");
    }
	}


  /************************************************************************************************
   * Gravity Sensor Callbacks
   ************************************************************************************************/

  @Override
  public void onSensorChanged(final SensorEvent event) {
		runOnUiThread(new Runnable() {
      @Override
      public void run() {
		    float x = event.values[0];
		    float y = event.values[1];
		    float z = event.values[2];
		    float g = (float) sqrt(x*x + y*y + z*z);

        text.setText("Gravity: " + Float.toString(g) + " meters per second per second");
      }
    });
  }

  @Override
  public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    // Do something here if sensor accuracy changes.
  }


}
