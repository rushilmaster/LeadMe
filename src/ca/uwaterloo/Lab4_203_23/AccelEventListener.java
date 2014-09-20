package ca.uwaterloo.Lab4_203_23;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import mapper.*;
import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class AccelEventListener implements SensorEventListener {
	MapView mv;
	Context cont;
	Toast reach;
	NavigationalMap map;
	PointF first = new PointF(3,9);
	PointF second = new PointF(7,9);
	PointF third = new PointF(11,9);
	PointF fourth = new PointF(16, 9);

	static int stepcount = 0;
	static boolean rising, top, falling, step, show = false;
	float smoothedAccelX = 0;
	float smoothedAccelY = 0;
	float smoothedAccelZ = 0;
	float accel[] = new float[3];
	static float stepsNorth, stepsEast = 0;

	public AccelEventListener(MapView mapv, Context c, NavigationalMap m) {
		mv = mapv;
		cont = c;
		map = m;
	}

	public void onAccuracyChanged(Sensor s, int i) {
	}

	public void onSensorChanged(SensorEvent se) {
		if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			smoothedAccelX += (se.values[0] - smoothedAccelX) / 2.5;
			smoothedAccelY += (se.values[1] - smoothedAccelY) / 2.5;
			smoothedAccelZ += (se.values[2] - smoothedAccelZ) / 2.5;
			accel[0] = smoothedAccelX;
			accel[1] = smoothedAccelY;
			accel[2] = smoothedAccelZ;

			if (accel[1] >= 0.1 && accel[1] <= 0.2) {
				rising = true;
			}
			if (rising == true && accel[1] >= 0.6 && accel[1] <= 1.5) {
				top = true;
			}
			if (rising == true && top == true && accel[1] >= 0.6
					&& accel[1] <= 1.2) {
				falling = true;
				step = true;
			}
			if (step == true) {
				step = false;
				rising = false;
				top = false;
				falling = false;
				
				float x = (float)(0.6*Math.sin(Math.toRadians(Rotation.degree + 20)));
				float y = (float)(0.6*Math.cos(Math.toRadians(Rotation.degree + 20)));
				
				mv.setUserPoint(mv.getUserPoint().x + x , mv.getUserPoint().y - y);
				checkifreached(mv.getUserPoint().x, mv.getUserPoint().y, 
						mv.getDestinationPoint().x, mv.getDestinationPoint().y );
				getPath(mv.getUserPoint(),mv.getDestinationPoint());
			}
			if (show == true){
				getPath(mv.getUserPoint(),mv.getDestinationPoint());
				show = false;
			}

		}
	}
	
	void getPath(PointF start, PointF end){
		List<PointF> list = new ArrayList<PointF>();	
		boolean fpoint = map.calculateIntersections(first, mv.getDestinationPoint()).isEmpty();
		boolean spoint = map.calculateIntersections(second, mv.getDestinationPoint()).isEmpty();
		boolean tpoint = map.calculateIntersections(third, mv.getDestinationPoint()).isEmpty();
		
		if(map.calculateIntersections(mv.getUserPoint(), mv.getDestinationPoint()).isEmpty()){
			list.add(mv.getUserPoint());
			list.add(mv.getDestinationPoint());
			mv.setUserPath(list);
		}
		else {
			if (fpoint){
				if (map.calculateIntersections(mv.getUserPoint(), first).isEmpty()){
					list.add(mv.getUserPoint());
					list.add(first);
					list.add(mv.getDestinationPoint());
					mv.setUserPath(list);
				}
				else{
					if (map.calculateIntersections(mv.getUserPoint(), second).isEmpty()){
						list.add(mv.getUserPoint());
						list.add(second);
						list.add(first);
						list.add(mv.getDestinationPoint());
						mv.setUserPath(list);
					}
					else{
						if(map.calculateIntersections(mv.getUserPoint(), third).isEmpty()){
							list.add(mv.getUserPoint());
							list.add(third);
							list.add(second);
							list.add(first);
							list.add(mv.getDestinationPoint());
							mv.setUserPath(list);
						}
						else{
							if(map.calculateIntersections(mv.getUserPoint(), fourth).isEmpty()){
								list.add(mv.getUserPoint());
								list.add(fourth);
								list.add(third);
								list.add(second);
								list.add(first);
								list.add(mv.getDestinationPoint());
								mv.setUserPath(list);
							}
							else{
								
							}
						}
					}
					
				}
			}
			else if(spoint){
				if (map.calculateIntersections(mv.getUserPoint(), second).isEmpty()){
					list.add(mv.getUserPoint());
					list.add(second);
					list.add(mv.getDestinationPoint());
					mv.setUserPath(list);
				}
				else{
					if (map.calculateIntersections(mv.getUserPoint(), first).isEmpty()){
						list.add(mv.getUserPoint());
						list.add(first);
						list.add(second);
						list.add(mv.getDestinationPoint());
						mv.setUserPath(list);
					}
					else{
						if(map.calculateIntersections(mv.getUserPoint(), third).isEmpty()){
							list.add(mv.getUserPoint());
							list.add(third);
							list.add(second);
							list.add(mv.getDestinationPoint());
							mv.setUserPath(list);
						}
						else{
							if(map.calculateIntersections(mv.getUserPoint(), fourth).isEmpty()){
								list.add(mv.getUserPoint());
								list.add(fourth);
								list.add(third);
								list.add(second);
								list.add(mv.getDestinationPoint());
								mv.setUserPath(list);
							}
							else{
								
							}
						}
					}
					
				}
			}
			else if(tpoint){
				if (map.calculateIntersections(mv.getUserPoint(), third).isEmpty()){
					list.add(mv.getUserPoint());
					list.add(third);
					list.add(mv.getDestinationPoint());
					mv.setUserPath(list);
				}
				else{
					if (map.calculateIntersections(mv.getUserPoint(), first).isEmpty()){
						list.add(mv.getUserPoint());
						list.add(first);
						list.add(second);
						list.add(third);
						list.add(mv.getDestinationPoint());
						mv.setUserPath(list);
					}
					else{
						if(map.calculateIntersections(mv.getUserPoint(), second).isEmpty()){
							list.add(mv.getUserPoint());
							list.add(second);
							list.add(third);
							list.add(mv.getDestinationPoint());
							mv.setUserPath(list);
						}
						else{
							if(map.calculateIntersections(mv.getUserPoint(), fourth).isEmpty()){
								list.add(mv.getUserPoint());
								list.add(fourth);
								list.add(third);
								list.add(mv.getDestinationPoint());
								mv.setUserPath(list);
							}
							else{
								
							}
						}
					}
					
				}
			}
			else{
				if (map.calculateIntersections(mv.getUserPoint(), fourth).isEmpty()){
					list.add(mv.getUserPoint());
					list.add(fourth);
					list.add(mv.getDestinationPoint());
					mv.setUserPath(list);
				}
				else{
					if (map.calculateIntersections(mv.getUserPoint(), first).isEmpty()){
						list.add(mv.getUserPoint());
						list.add(first);
						list.add(second);
						list.add(third);
						list.add(fourth);
						list.add(mv.getDestinationPoint());
						mv.setUserPath(list);
					}
					else{
						if(map.calculateIntersections(mv.getUserPoint(), third).isEmpty()){
							list.add(mv.getUserPoint());
							list.add(third);
							list.add(fourth);
							list.add(mv.getDestinationPoint());
							mv.setUserPath(list);
						}
						else{
							if(map.calculateIntersections(mv.getUserPoint(), second).isEmpty()){
								list.add(mv.getUserPoint());
								list.add(second);
								list.add(third);
								list.add(fourth);
								list.add(mv.getDestinationPoint());
								mv.setUserPath(list);
							}
							else{
								
							}
						}
					}
					
				}
			}
		}
		
	}

	void checkifreached (float currX, float currY, float desX, float desY){
		
		if (Math.abs(currX - desX) <= 0.5 && Math.abs(currY - desY) <= 0.5){
			reach = Toast.makeText(cont, "You have reached! Now give us 100%", Toast.LENGTH_LONG);
			reach.show();
		}
	}
	
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
