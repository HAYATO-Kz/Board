package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		CompositeGObject com = new CompositeGObject();
		for(GObject g : gObjects){
			if(g.isSelected())com.add(g);
		}
		System.out.println("hi");
		com.recalculateRegion();
		gObjects.add(com);
		repaint();
	}

	public void deleteSelected() {
		Iterator<GObject> i = gObjects.iterator();
		while (i.hasNext()) {
			GObject o = i.next();
			if(o.isSelected())i.remove();
		}
		repaint();
	}
	
	public void clear() {
		gObjects.clear();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		int x, y,finalX,finalY;

		
		private void deselectAll() {
			for(GObject g : gObjects){
				g.deselected();
				repaint();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			boolean s = false;
			for(GObject g : gObjects) {
				if (g.pointerHit(e.getX(), e.getY()) == true) {
					s = true;
					g.selected();
					target = g ;
					repaint();
				}
			}
			if(!s)deselectAll();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (target.pointerHit(e.getX(), e.getY()) == true) {
				finalX = e.getX();
				finalY = e.getY();
				target.move(finalX - x, finalY - y);
				x = finalX;
				y = finalY;
				repaint();
			}
		}
	}
	
}