package ca.uwaterloo.Lab4_203_23;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import mapper.*;

public class MainActivity extends Activity {
	TextView rotationvalue;
	Button simulate;
	Button path;
	MapView mv;
	NavigationalMap map;
	PositionListener pl = null;
	Context cont;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		mv.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return super.onContextItemSelected(item)
				|| mv.onContextItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = MapLoader.loadMap(Environment.getExternalStorageDirectory(),"Lab-room-peninsula.svg");

		mv = new MapView(getApplicationContext(), 1000, 800, 57, 60);
		registerForContextMenu(mv);
		mv.setMap(map);
		
		pl= new Position();
		mv.addListener(pl);
		
		rotationvalue = new TextView(getApplicationContext());
		cont = getApplicationContext();

		simulate = new Button(this);
		simulate.setText("Simulate Step");
		simulate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AccelEventListener.step = true;
			}
		});
		
		path = new Button(this);
		path.setText("Find Path!");
		path.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AccelEventListener.show = true;
			}
		});
		

		LinearLayout LL = (LinearLayout) findViewById(R.id.layout);
		LL.setOrientation(LinearLayout.VERTICAL);

		LL.addView(mv);
		LL.addView(rotationvalue);
		LL.addView(path);
		LL.addView(simulate);

		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

		Sensor accel = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		Sensor rotation = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		SensorEventListener accelListener = new AccelEventListener(mv, cont, map);
		SensorEventListener rotationListener = new Rotation(rotationvalue);
		
		sm.registerListener(accelListener, accel,
				SensorManager.SENSOR_DELAY_FASTEST);
		sm.registerListener(rotationListener, rotation,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}