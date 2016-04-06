package net.ericbram.pattracker;

import android.app.Activity;
import android.os.AsyncTask;

import net.ericbram.pattracker.Objects.Prediction;
import net.ericbram.pattracker.Objects.Route;
import net.ericbram.pattracker.Objects.Stop;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebram on 4/5/2016.
 */
public class APICaller {
    String apiKey = "zHyVkFb23XAugWavsJuujNyNL";
    String baseURL = "http://truetime.portauthority.org/bustime/api/v1/";
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd kk:mm");
    private String MakeQuery(String item, List<String> params)
    {
        String query = baseURL + item + "?key=" + apiKey;
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                query += "&" + params.get(i);
            }
        }
        return query;
    }

    public void ExecuteTask(String name, Activity a, String... params ) {
        new APIAsyncCall(a, name).execute(params);
    }

    public List<Prediction> ParsePrediction(String input) throws XmlPullParserException, IOException {
        List<Prediction> preds = new ArrayList<Prediction>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(input));
            int eventType = xpp.getEventType();
            Prediction p = new Prediction();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                } else if(eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().toLowerCase().equals("prd")) {
                        p = new Prediction();
                    } else if (xpp.getName().toLowerCase().equals("tmstmp")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setTimeStamp(fmt.parse(val));
                    }else if (xpp.getName().toLowerCase().equals("typ")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setType(val);
                    }else if (xpp.getName().toLowerCase().equals("stpnm")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setStopName(val);
                    }else if (xpp.getName().toLowerCase().equals("stpid")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setStopId(Integer.parseInt(val));
                    }else if (xpp.getName().toLowerCase().equals("vid")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setVehicleId(Integer.parseInt(val));
                    }else if (xpp.getName().toLowerCase().equals("dstp")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setDistanceToStop(Integer.parseInt(val));
                    }else if (xpp.getName().toLowerCase().equals("rt")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setRoute(val);
                    }else if (xpp.getName().toLowerCase().equals("rtdd")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setRouteDisplayName(val);
                    }else if (xpp.getName().toLowerCase().equals("rtdir")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setRoutDirection(val);
                    }else if (xpp.getName().toLowerCase().equals("prdtm")) {
                        xpp.next();
                        String val = xpp.getText();
                        p.setPredictedTime(fmt.parse(val));
                    }
                } else if(eventType == XmlPullParser.TEXT) {
                }else if(eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().toLowerCase().equals("prd")) {
                        preds.add(p);
                    }
                }
                eventType = xpp.next();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
        }
        return preds;
    }

    public List<Route> ParseRoutes(String input) throws XmlPullParserException, IOException {
        List<Route> routes = new ArrayList<Route>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            String val3 = input.toString();
            xpp.setInput(new StringReader(input));
            int eventType = xpp.getEventType();
            Route r = new Route();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                } else if(eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().toLowerCase().equals("route")) {
                        r = new Route();
                    }
                    else if (xpp.getName().toLowerCase().equals("rt")) {
                        xpp.next();
                        String val = xpp.getText();
                        r.setRoute(val);
                    }else if (xpp.getName().toLowerCase().equals("rtdd")) {
                        xpp.next();
                        String val = xpp.getText();
                        r.setRouteRouteDisplayName(val);
                    }else if (xpp.getName().toLowerCase().equals("rtnm")) {
                        xpp.next();
                        String val = xpp.getText();
                        r.setRouteName(val);
                    }
                } else if(eventType == XmlPullParser.TEXT) {
                }else if(eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().toLowerCase().equals("route")) {
                        routes.add(r);
                    }
                }
                eventType = xpp.next();
            }
        } finally {
        }
        return routes;
    }

    public List<Stop> ParseStops(String input) throws XmlPullParserException, IOException {
        List<Stop> stops = new ArrayList<Stop>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(input));
            int eventType = xpp.getEventType();
            Stop s = new Stop();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                } else if(eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().toLowerCase().equals("stop")) {
                        s = new Stop();
                    }
                    else if (xpp.getName().toLowerCase().equals("stpid")) {
                        xpp.next();
                        String val = xpp.getText();
                        s.setStopId(Integer.parseInt(val));
                    }else if (xpp.getName().toLowerCase().equals("stpnm")) {
                        xpp.next();
                        String val = xpp.getText();
                        s.setStopName(val);
                    }
                } else if(eventType == XmlPullParser.TEXT) {
                }else if(eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().toLowerCase().equals("stop")) {
                        stops.add(s);
                    }
                }
                eventType = xpp.next();
            }
        } finally {
        }
        return stops;
    }


    private String downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();

        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(isr);

        String inputLine;
        String lines = "";
        while ((inputLine = in.readLine()) != null)
        {
            lines += inputLine;
            System.out.println(inputLine);
        }
        in.close();

        return lines;
    }

    private List<Prediction>  GetPredictions() throws IOException, XmlPullParserException {
        String stream = null;
        List<String> parameters = new ArrayList<String>();
        parameters.add("stpid=7116");
        stream = downloadUrl(MakeQuery("getpredictions", parameters));
        return ParsePrediction(stream);
    }

    private List<Route>  GetRoutes() throws IOException, XmlPullParserException {
        String stream = null;
        stream = downloadUrl(MakeQuery("getroutes", null));
        return ParseRoutes(stream);

    }


    private List<Stop>  GetStops(String route, String dir) throws IOException, XmlPullParserException {
        String stream = null;
        String rt = "rt=" + route;
        String d = "dir=" + dir;
        List<String> pa = new ArrayList<>();
        pa.add(rt);
        pa.add(d);
        stream = downloadUrl(MakeQuery("getstops", pa));
        return ParseStops(stream);
    }

    public class APIAsyncCall extends AsyncTask<String, Void, Void>
    {
        OnDataSendToActivity dataSendToActivity;
        List<Prediction> predictionList;
        List<Route> routeList;
        List<Stop> stopList;
        String apiName;
        public APIAsyncCall(Activity activity, String api){
            dataSendToActivity = (OnDataSendToActivity)activity;
            apiName = api;
        }

        protected Void doInBackground(String... params) {
            try {
                switch (apiName) {
                    case "getpredictions":
                        predictionList = GetPredictions();
                        break;
                    case "getroutes":
                        routeList = GetRoutes();
                        break;
                    case "getstops":
                        stopList = GetStops(params[0], params[1]);
                        break;
                }
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPreExecute() {
            //display progress dialog.
        }

        protected void onPostExecute(Void result) {
            // dismiss progress dialog and update ui
            switch (apiName) {
                case "getpredictions":
                    dataSendToActivity.SendPredictions(predictionList);
                    break;
                case "getroutes":
                    dataSendToActivity.SendRoutes(routeList);
                    break;
                case "getstops":
                    dataSendToActivity.SendStops(stopList);
                    break;
            }
        }
    }
}
