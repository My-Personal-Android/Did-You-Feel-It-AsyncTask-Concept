
package com.example.android.didyoufeelit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-01-01&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadDataFromServer downloadDataFromServer = new DownloadDataFromServer();
        downloadDataFromServer.execute(USGS_REQUEST_URL);

    }

    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }

    private class DownloadDataFromServer extends AsyncTask<String, Void, Event> {

        protected Event doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            Event earthquake = Utils.fetchEarthquakeData(urls[0]);
            return earthquake;
        }

        protected void onPostExecute(Event event) {
            if (event == null) {
                return;
            }
            updateUi(event);
        }
    }
}
