package ca.uwaterloo.Lab4_203_23;

import android.graphics.PointF;
import mapper.MapView;
import mapper.PositionListener;

public class Position implements PositionListener {
	public Position(){
		
	}
	
	@Override
	public void originChanged(MapView source, PointF loc) {
		// TODO Auto-generated method stub
		source.setUserPoint(source.getOriginPoint());
	}

	@Override
	public void destinationChanged(MapView source, PointF dest) {
		// TODO Auto-generated method stub
		source.setDestinationPoint(source.getDestinationPoint());
	}

}
