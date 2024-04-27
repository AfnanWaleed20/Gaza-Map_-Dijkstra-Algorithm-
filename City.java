package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Circle;

public class City {
String name;
double x;
double y;
double longitud;
double latitude;
private List<Edge> edges;
Circle circel=new Circle();
City predecessor;
private double distance;
public City(String name, double longitud, double latitude) {
	super();
	this.name = name;
	this.longitud = longitud;
	this.latitude = latitude;
	this.edges=new ArrayList();
	this.distance = Double.MAX_VALUE;
}
public City(String name) {
	super();
	this.name = name;
}
public City(String name, double x, double y, double longitud, double latitude) {
	super();
	this.name = name;
	this.x = x;
	this.y = y;
	this.longitud = longitud;
	this.latitude = latitude;
	
}
public String getName() {
	return name;
}
public City getPredecessor() {
    return predecessor;
}

public void setPredecessor(City predecessor) {
    this.predecessor = predecessor;
}

public List<Edge> getEdges() {
    return edges;
}

public double getDistance() {
	return distance;
}
public void setDistance(double distance) {
	this.distance = distance;
}
public void addEdge(City destination, double distance) {
    edges.add(new Edge(this, destination, distance));
}
public void setName(String name) {
	this.name = name;
}
public double getX() {
	return x;
}
public void setX(double x) {
	this.x = x;
}
public double getY() {
	return y;
}
public void setY(double y) {
	this.y = y;
}
public double getLongitud() {
	return longitud;
}
public void setLongitud(double longitud) {
	this.longitud = longitud;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public Circle getCircle() {
	return circel;
}

public void setCircle(Circle circle) {
	this.circel = circle;
}

@Override
public String toString() {
	return "City [name=" + name + ", x=" + x + ", y=" + y + ", longitud=" + longitud + ", latitude=" + latitude + "]";
}



}
