package net.ericbram.pattracker.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

        Intent intent = getIntent();
        String inboundStr = intent.getExtras().getString("inbound");
        _inbound = Boolean.parseBoolean(inboundStr);
        TextView introText = (TextView)findViewById(R.id.introText2);
        if (_inbound) {
            introText.setText("First, what bus do you ride inbound and where do you get picked up?");
        } else {
            introText.setText("Great!  Now, what bus do you ride outbound and where do you get picked up?");
        }

        Button clickButton = (Button) findViewById(R.id.btnInboundNext);
        clickButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if (_inbound){
                    editor.putString("inboundroute", _currentRoute);
                    editor.putInt("inboundstop", _currentStop);
                } else {
                    editor.putString("outboundroute", _currentRoute);
                    editor.putInt("outboundstop", _currentStop);
                }
                editor.apply();

                if (_inbound) {
                    Intent i = new Intent(v.getContext(), WizardActivity.class);
                    i.putExtra("inbound", "false");
                    startActivity(i);
                } else {
                    Intent i = new Intent(v.getContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });
        api.ExecuteTask("getroutes", _thisActivity);
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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, routenames); //selected item will look like a spinner set from XML
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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stopnames); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerArrayAdapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != 0) {
                    Button btn = (Button)findViewById(R.id.btnInboundNext);
                    _currentStop = _stops.get(position - 1).getStopId();
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