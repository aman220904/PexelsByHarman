package com.example.pexels;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private List<Photo> photoList;
    private static final String API_KEY = "HylTNwr7lJv53y3ocDv9c4CWSZKxROwR7opQJJwM0tKxlsF0kjDpFei4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize photo list
        photoList = new ArrayList<>();
        photoAdapter = new PhotoAdapter(photoList, this);
        recyclerView.setAdapter(photoAdapter);

        // Fetch photos from Pexels API
        fetchPhotos();
    }

    // Method to inflate the menu in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);  // Inflate the menu
        return true;
    }

    // Handle item clicks in the toolbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:  // Use the correct ID here
                showAboutDialog();  // Show the About dialog
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Method to show the "About" dialog
    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About Pexels App")
                .setMessage("This app allows you to browse curated photos from Pexels. You can search for photos and explore various images.\n\nApp version: 1.0")
                .setPositiveButton("OK", null)
                .show();
    }

    private void fetchPhotos() {
        String url = "https://api.pexels.com/v1/curated?page=1&per_page=20";

        // Volley request to fetch photos
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    // Parse the response and extract photo data
                    try {
                        JSONArray photos = response.getJSONArray("photos");
                        for (int i = 0; i < photos.length(); i++) {
                            JSONObject photoObject = photos.getJSONObject(i);
                            String title = photoObject.getString("alt");
                            String imageUrl = photoObject.getJSONObject("src").getString("medium");

                            // Add the photo to the list
                            photoList.add(new Photo(title, imageUrl));
                        }

                        // Notify adapter that data has changed
                        photoAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            // Handle error (e.g., no internet connection)
            Snackbar.make(recyclerView, "Error fetching data", Snackbar.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "HylTNwr7lJv53y3ocDv9c4CWSZKxROwR7opQJJwM0tKxlsF0kjDpFei4");
                return headers;
            }
        };

        // Add the request to the request queue
        requestQueue.add(jsonObjectRequest);
    }
}
