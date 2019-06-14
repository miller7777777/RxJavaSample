package com.example.myapplication.activity;

import io.reactivex.Single;

/**
 * User: dcherepanov
 * Date: 16.05.2019
 * Time: 18:10
 */
class SomeRepository {

    private SomeDataSource dataSource = new SomeDataSource();

    Single<String> getNews(final String urlNews) {
        return dataSource.getNewsFromApi(urlNews);
    }
}
