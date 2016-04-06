package net.ericbram.pattracker.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import net.ericbram.pattracker.APICaller;
import net.ericbram.pattracker.Objects.Prediction;
import net.ericbram.pattracker.Objects.Route;
import net.ericbram.pattracker.Objects.Stop;
import net.ericbram.pattracker.OnDataSendToActivity;
import net.ericbram.pattracker.R;

import java.util.ArrayList;
import java.util.List;

public class WizardActivity extends AppCompatActivity implements OnDataSendToActivity {
    APICaller api;
    List<Route> _routes;
    List<Stop> _stops;
    String _currentRoute;
    boolean _inbound;
    int _currentStop;
    Activity _thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        _thisActivity = this;
        api = new APICaller();
        api.ExecuteTask("getroutes", _thisActivity);

        _inbound = true;
    }

    @Override
    public void SendPredictions(List<Prediction> predictions) {
        System.out.println("made it here");
    }

    @Override
    public void SendRoutes(List<Route> routes) {
        _routes = routes;
        Spinner mySpinner = (Spinner) findViewById(R.id.routeInbound);
        ArrayList<String> routenames = new ArrayList<>();
        routenames.add("Please Select Route");
        if (routes != null) {
            for (int i = 0; i < routes.size(); i++) {
                routenames.add(routes.get(i).getRouteRouteDisplayName());
            }
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routenames); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerArrayAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner mySpinner = (Spinner) findViewById(R.id.stopInbound);
                if (position != 0) {
                    mySpinner.setVisibility(View.VISIBLE);
                    _currentRoute = _routes.get(position - 1).getRoute();
                    api.ExecuteTask("getstops", _thisActivity, _currentRoute, _inbound ? "INBOUND" : "OUTBOUND");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    @Override
    public void SendStops(List<Stop> stops) {
        _stops = stops;
        Spinner mySpinner = (Spinner) findViewById(R.id.stopInbound);
        ArrayList<String> stopnames = new ArrayList<>();
        stopnames.add("Please Select Stop");
        if (stops != null) {
            for (int i = 0; i < stops.size(); i++) {
                stopnames.add(stops.get(i).getStopName());
            }
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stopnames); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerArrayAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != 0) {
                    Button btn = (Button)findViewById(R.id.btnInboundNext);
                    btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}