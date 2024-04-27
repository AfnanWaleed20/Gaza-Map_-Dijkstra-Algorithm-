package application;

public class Edge implements Comparable<Edge> {
City src;
City dest;
double dis;
public Edge(City src, City dest, double dis) {
	super();
	this.src = src;
	this.dest = dest;
	this.dis = dis;
}
@Override
public String toString() {
	return "Edge [src=" + src + ", dest=" + dest + ", dis=" + dis + "]";
}
public City getNeighbor(String cityName) {
    if (src.getName().equals(cityName)) {
        return dest;
    } else if (dest.getName().equals(cityName)) {
        return src;
    } else {
        throw new IllegalArgumentException("City " + cityName + " is not part of this edge");
    }
}
public boolean isConnectedTo(String city1Name, String city2Name) {
    return (src.getName().equals(city1Name) && dest.getName().equals(city2Name)) ||
           (src.getName().equals(city2Name) && dest.getName().equals(city1Name));
}

public Edge() {
	super();
	// TODO Auto-generated constructor stub
}
public City getSrc() {
	return src;
}
public void setSrc(City src) {
	this.src = src;
}
public City getDest() {
	return dest;
}
public void setDest(City dest) {
	this.dest = dest;
}
public double getDis() {
	return dis;
}
public void setDis(double dis) {
	this.dis = dis;
}
@Override
public int compareTo(Edge other) {
    // Compare based on distance
    return Double.compare(this.dis, other.dis);
}


}
