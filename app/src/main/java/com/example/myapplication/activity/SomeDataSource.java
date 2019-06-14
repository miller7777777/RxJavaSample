package com.example.myapplication.activity;

import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


class SomeDataSource {

    Single<String> getNewsFromApi(final String urlNews) {
        return Single
                .fromCallable(() -> getNews(urlNews))
                .subscribeOn(Schedulers.io());
    }

    private String getNews(final String urlNews) {
        HttpURLConnection connection = null;
        String response = null;

        try {
            URL url = new URL(urlNews);

            connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = SomeConverter.convertInputStreamToString(connection.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response;
    }
}
