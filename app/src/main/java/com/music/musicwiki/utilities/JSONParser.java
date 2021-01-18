package com.music.musicwiki.utilities;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONParser {


    JSONTask jsonTask = new JSONTask();

    //function called to get the response from URL and parse
    public void parseJSON(String url, String activityIndex) {

        jsonTask.cancel(true);
        jsonTask = new JSONTask();
        jsonTask.execute(url, activityIndex);
    }

    private class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                    if (isCancelled()) {
                        break;
                    }
                }

                return buffer.toString() + "*" + params[1];


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                String activityIndex = result.substring(result.indexOf("*") + 1);
                result = result.substring(0, result.indexOf("*"));

                switch (activityIndex) {
                    case "1":
                        if (StaticClass.getGenresActivity() != null) {
                            StaticClass.getGenresActivity().genreResponse(result);
                        }
                        break;

                    case "2":
                        if (StaticClass.getGenreAlbumsFragment() != null) {
                            StaticClass.getGenreAlbumsFragment().albumsResponse(result);
                        }
                        break;

                    case "3":
                        if (StaticClass.getGenreArtistsFragment() != null) {
                            StaticClass.getGenreArtistsFragment().albumsResponse(result);
                        }
                        break;

                    case "4":
                        if (StaticClass.getGenreTracksFragment() != null) {
                            StaticClass.getGenreTracksFragment().albumsResponse(result);
                        }
                        break;

                    case "5":
                        if (StaticClass.getAlbumDetailActivity() != null) {
                            StaticClass.getAlbumDetailActivity().albumResponse(result);
                        }
                        break;

                    case "6":
                        if (StaticClass.getArtistDetailActivity() != null) {
                            StaticClass.getArtistDetailActivity().albumResponse(result);
                        }
                        break;

                    case "7":
                        if (StaticClass.getArtistDetailActivity() != null) {
                            StaticClass.getArtistDetailActivity().topAlbumsResponse(result);
                        }
                        break;

                    case "8":
                        if (StaticClass.getArtistDetailActivity() != null) {
                            StaticClass.getArtistDetailActivity().topTracksResponse(result);
                        }
                        break;
                }

            }
        }
    }
}