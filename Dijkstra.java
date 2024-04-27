package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Dijkstra {

private Map<String, City> cities;

public Dijkstra() {
    this.cities = new HashMap<>();
}

public void addCity(String cityName, double latitude, double longitude) {
    City city = new City(cityName, latitude, longitude);
    cities.put(cityName, city);
}

public void addEdge(String sourceCityName, String destinationCityName, double distance) {
    if (!cities.containsKey(sourceCityName) || !cities.containsKey(destinationCityName)) {
        throw new IllegalArgumentException("Invalid source or destination city");
    }

    City sourceCity = cities.get(sourceCityName);
    City destinationCity = cities.get(destinationCityName);
    sourceCity.addEdge(destinationCity, distance);
    destinationCity.addEdge(sourceCity, distance); // Assuming it's an undirected graph
}

public List<String> findShortestPath(String startCity, String endCity) {
    if (!cities.containsKey(startCity) || !cities.containsKey(endCity)) {
        throw new IllegalArgumentException("Invalid start or end city");
    }

    PriorityQueue<CityDistance> priorityQueue = new PriorityQueue<>();

    initializeDistances(startCity);

    priorityQueue.add(new CityDistance(startCity, 0.0));

    while (!priorityQueue.isEmpty()) {
        String currentCity = priorityQueue.poll().getCity();
        
        if (currentCity.equals(endCity)) {
            break;
        }

        updateDides(currentCity, priorityQueue);
    }

    return Path(startCity, endCity);
}

private void initializeDistances(String startCity) {
    for (City city : cities.values()) {
        city.setDistance(Double.MAX_VALUE);
        city.setPredecessor(null);
    }

    City start = cities.get(startCity);
    if (start != null) {
        start.setDistance(0.0);
    }
}

private void updateDides(String currentCity, PriorityQueue<CityDistance> priorityQueue) {
    City current = cities.get(currentCity);

    for (Edge edge : current.getEdges()) {
        City neighbor = edge.getDest();
        
        double newDistance = current.getDistance() + edge.getDis();

        if (newDistance < neighbor.getDistance()) {
            neighbor.setDistance(newDistance);
            neighbor.setPredecessor(current);//the previous adjecent
            
            priorityQueue.add(new CityDistance(neighbor.getName(), newDistance));
        }
    }
}

private List<String> Path(String startCity, String endCity) {
    List<String> shortestPath = new ArrayList<>();
    City currentCity = cities.get(endCity);

    while (currentCity != null) {
        shortestPath.add(currentCity.getName());
      currentCity = currentCity.getPredecessor();
    }

    Collections.reverse(shortestPath);

    return shortestPath;
}

public double getShortestDistance(String startCity, String endCity) {
    List<String> shortestPath = findShortestPath(startCity, endCity);

    if (shortestPath.size() < 2) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Warning");
    	alert.setContentText("Same location so  No Path Found");

    	alert.showAndWait();

        return -1.0;
    }

    double distance = 0.0;
    for (int i = 0; i < shortestPath.size() - 1; i++) {
        distance += getDistance(shortestPath.get(i), shortestPath.get(i + 1));
    }

    return distance;
}

public Set<String> getVertices() {
    return cities.keySet();
}

public List<String> getNeighbors(String cityName) {
    if (!cities.containsKey(cityName)) {
        throw new IllegalArgumentException("Invalid city name");
    }

    City city = cities.get(cityName);
    List<String> neighbors = new ArrayList<>();

    for (Edge edge : city.getEdges()) {
        neighbors.add(edge.getDest().getName());
    }

    return neighbors;
}

public double getDistance(String sourceCityName, String destinationCityName) {
    if (!cities.containsKey(sourceCityName) || !cities.containsKey(destinationCityName)) {
        throw new IllegalArgumentException("Invalid source or destination city");
    }

    City sourceCity = cities.get(sourceCityName);
    City destinationCity = cities.get(destinationCityName);

    for (Edge edge : sourceCity.getEdges()) {
        if (edge.getDest().equals(destinationCity)) {
            return edge.getDis();
        }
    }


    return 0;
}
}
