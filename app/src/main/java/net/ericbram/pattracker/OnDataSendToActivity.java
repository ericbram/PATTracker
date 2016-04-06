package net.ericbram.pattracker;

import net.ericbram.pattracker.Objects.Prediction;
import net.ericbram.pattracker.Objects.Route;
import net.ericbram.pattracker.Objects.Stop;

import java.util.List;

/**
 * Created by ebram on 4/5/2016.
 */
public interface OnDataSendToActivity {
    void SendPredictions(List<Prediction> predictions);
    void SendRoutes(List<Route> routes);
    void SendStops(List<Stop> stops);
}
