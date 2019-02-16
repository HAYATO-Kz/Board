package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		 gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		for (GObject g : gObjects){
			g.move(dX,dY);
		}
		this.x += dX;
		this.y += dY;
	}
	
	public void recalculateRegion() {
		GObject maxX = gObjects.get(0), maxY = gObjects.get(0), minX = gObjects.get(0), minY = gObjects.get(0);
		for(GObject g : gObjects){
			if(g==gObjects.get(0))continue;
			if(g.x < minX.x)minX= g;
			else maxX = g;
			if(g.y < minY.y)minY=g;
			else maxY = g ;
		}
		width = ((maxX.x+maxX.width)-(minX.x-minX.width));
		System.out.printf("maxX = %d width = %d, minX = %d width = %d\n",maxX.x,maxX.width,minX.x,minX.width);
		height = ((maxY.y+maxY.height)-(minY.y-minY.height));
		System.out.printf("maxY = %d height = %d, minY = %d height = %d\n",maxY.y,maxY.height,minY.y,minY.height);
		y = (maxY.y+minY.y)/2;
		x = (maxX.x+minX.x)/2;
		System.out.printf("width = %d height = %d x = %d y = %d",width,height,x,y);
	}

	@Override
	public void paintObject(Graphics g) {
		g.drawRect(x,y,width,height);
	}

	@Override
	public void paintLabel(Graphics g) {
		g.drawString("group",width,height);
	}
	
}
