package net.ericbram.pattracker.Objects;

import java.util.Date;

/**
 * Created by ebram on 4/5/2016.
 */
public class Prediction {
    private Date TimeStamp;
    private String Type;
    private String StopName;
    private int StopId;
    private int VehicleId;
    private int DistanceToStop;
    private String Route;
    private String RouteDisplayName;
    private String RoutDirection;
    private String Destination;
    private Date PredictedTime;

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStopName() {
        return StopName;
    }

    public void setStopName(String stopName) {
        StopName = stopName;
    }

    public int getStopId() {
        return StopId;
    }

    public void setStopId(int stopId) {
        StopId = stopId;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public String getRouteDisplayName() {
        return RouteDisplayName;
    }

    public void setRouteDisplayName(String routeDisplayName) {
        RouteDisplayName = routeDisplayName;
    }

    public String getRoutDirection() {
        return RoutDirection;
    }

    public void setRoutDirection(String routDirection) {
        RoutDirection = routDirection;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public Date getPredictedTime() {
        return PredictedTime;
    }

    public void setPredictedTime(Date predictedTime) {
        PredictedTime = predictedTime;
    }

    public int getDistanceToStop() {
        return DistanceToStop;
    }

    public void setDistanceToStop(int distanceToStop) {
        DistanceToStop = distanceToStop;
    }
}
