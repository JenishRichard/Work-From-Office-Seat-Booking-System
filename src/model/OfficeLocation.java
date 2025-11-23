package model;

//Immutable type representing an office location
public final class OfficeLocation {

    private final String city;
    private final String building;

    public OfficeLocation(String city, String building) {
        this.city = city;
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public String getBuilding() {
        return building;
    }

    @Override
    public String toString() {
        return city + " - " + building;
    }
}
