package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import observer.Observable;
import observer.Observer;
import java.util.Iterator;

public abstract class Shape implements Moveable, Comparable, Observable, Serializable {

	private boolean selected;
	private Color color;
	
	public abstract void draw(Graphics g);
	public abstract boolean contains(int x, int y);
	public abstract boolean equals(Object o);
	
	private List<Observer> observers = new ArrayList<Observer>();

	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		this.notifyObservers();
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		while(it.hasNext()) {
			it.next().update();
		}
	}
}
