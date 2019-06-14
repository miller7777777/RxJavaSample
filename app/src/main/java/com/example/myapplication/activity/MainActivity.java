package com.example.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    TextView someTextView;
    ProgressBar progressBar;
    Button startButton;

    Disposable disposable;
    CompositeDisposable instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        instance = new CompositeDisposable();

        initUI();
        initHandlers();
    }

    private void initUI() {
        someTextView = findViewById(R.id.some_text_view);
        startButton = findViewById(R.id.start_button);
        progressBar = findViewById(R.id.my_progress);
    }

    private void initHandlers() {
        startButton.setOnClickListener(
                v -> getSomeResponse()
        );
    }

    private void getSomeResponse() {
        progressBar.setVisibility(View.VISIBLE);

        final String someUrl = "https://jsonplaceholder.typicode.com/";

        final SomeRepository repository = new SomeRepository();

        Single<String> response = repository.getNews(someUrl);

        disposable = response
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseSuccess -> {
                            progressBar.setVisibility(View.GONE);
                            someTextView.setText(responseSuccess);
                        },
                        error -> {
                            progressBar.setVisibility(View.GONE);
                            Log.d("wtf", "error: " + error);
                        }
                );


        instance.add(disposable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.dispose();
    }
}
