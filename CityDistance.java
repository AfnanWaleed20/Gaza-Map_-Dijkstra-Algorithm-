package application;

class CityDistance implements Comparable<CityDistance> {
    private String city;
    private double distance;

    public CityDistance(String city, double distance) {
        this.city = city;
        this.distance = distance;
    }

    public String getCity() {
        return city;
    }

    @Override
    public int compareTo(CityDistance other) {
        return Double.compare(this.distance, other.distance);
    }


}
