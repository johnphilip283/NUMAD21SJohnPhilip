package edu.neu.madcourse.numad21s_johnphilip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {

    public TextView titleInput;
    public Button searchButton;

    public TextView movieInfoType;
    public TextView movieInfoTitle;
    public TextView movieInfoYear;

    private final String MOVIE_ACTIVITY = "Movie Activity";
    private final String API_ENDPOINT = "https://www.omdbapi.com/?apikey=76ca14dd&s=";

    private final String TYPE_KEY = "TYPE";
    private final String TITLE_KEY = "TITLE";
    private final String YEAR_KEY = "YEAR";

    public String type = "";
    public String title = "";
    public String year = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        titleInput = findViewById(R.id.movie_name_input);
        searchButton = findViewById(R.id.search_movies_button);

        movieInfoYear = findViewById(R.id.movie_info_year);
        movieInfoType = findViewById(R.id.movie_info_type);
        movieInfoTitle = findViewById(R.id.movie_info_title);

        initialSearchState(savedInstanceState);
    }

    public void initialSearchState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            type = savedInstanceState.getString(TYPE_KEY);
            year = savedInstanceState.getString(YEAR_KEY);
            title = savedInstanceState.getString(TITLE_KEY);

            movieInfoType.setText(String.format("%s %s", getString(R.string.type), type));
            movieInfoTitle.setText(String.format("%s %s", getString(R.string.title), title));
            movieInfoYear.setText(String.format("%s %s", getString(R.string.year), year));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString(TYPE_KEY, type);
        outState.putString(YEAR_KEY, year);
        outState.putString(TITLE_KEY, title);

        super.onSaveInstanceState(outState);
    }

    public void onSearchMovies(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(titleInput.getWindowToken(), 0);

        Toast.makeText(getApplication(),"Fetching movie information...",Toast.LENGTH_SHORT).show();
        MovieService api = new MovieService();

        String query = API_ENDPOINT + titleInput.getText();
        Log.i("Movie url with query: ", query);

        try {
            api.execute(query);
        } catch (Exception e) {
            Log.e("Movie API call error: ", e.toString());
            Toast.makeText(getApplication(),"Error fetching movie information.", Toast.LENGTH_SHORT).show();
        }
    }

    private class MovieService extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(MOVIE_ACTIVITY, "Making progress...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jObject = new JSONObject();
            try {
                URL url = new URL(params[0]);
                // Get String response from the url address
                String resp = NetworkUtil.httpGETResponse(url);
                jObject = new JSONObject(resp);
                return jObject;

            } catch (MalformedURLException e) {
                Log.e(MOVIE_ACTIVITY,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(MOVIE_ACTIVITY,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(MOVIE_ACTIVITY,"IOException: " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(MOVIE_ACTIVITY,"JSONException");
                e.printStackTrace();
            }

            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);

            try {
                if (jObject.getString("Response").equals("True")) {

                    JSONObject closestMatch = jObject.getJSONArray("Search").getJSONObject(0);

                    type = closestMatch.getString("Type");
                    title = closestMatch.getString("Title");
                    year = closestMatch.getString("Year");

                    movieInfoType.setText(String.format("%s %s", getString(R.string.type), type));
                    movieInfoTitle.setText(String.format("%s %s", getString(R.string.title), title));
                    movieInfoYear.setText(String.format("%s %s", getString(R.string.year), year));

                } else {
                    if (jObject.getString("Error").equals("Too many results.")) {
                        Toast.makeText(getApplication(),"Too many results for given query.",Toast.LENGTH_SHORT).show();
                    } else if (jObject.getString("Error").equals("Movie not found!")) {
                        Toast.makeText(getApplication(),"Movie not found!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(),"Error fetching movie information.",Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(getApplication(),"Error fetching movie information.",Toast.LENGTH_SHORT).show();
            }
        }

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

    }

}
