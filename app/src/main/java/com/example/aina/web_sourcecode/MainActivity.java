package com.example.aina.web_sourcecode;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    TextView Tx_View;
    Spinner spinner;
    Button tombol;
    EditText inputtext;
    public  String url = null;
    ProgressBar  loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tx_View = (TextView)findViewById(R.id.MyResult);
        spinner = (Spinner) findViewById(R.id.Spinn);
        tombol  = (Button)findViewById(R.id.Tombol);
        inputtext = (EditText) findViewById(R.id.InputText);
        loading = (ProgressBar) findViewById(R.id.Loading);

        ArrayAdapter<CharSequence> array = ArrayAdapter.createFromResource(this, R.array.Spinner,android.R.layout.simple_spinner_item);
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(array);
        loading.setVisibility(View.GONE);
    }


    public void doSomething(View view) {
        url = spinner.getSelectedItem()+inputtext.getText().toString();
        boolean valid = Patterns.WEB_URL.matcher(url).matches();

        if (valid)
        {
            getSupportLoaderManager().restartLoader(0,null,this);
            loading.setVisibility(View.VISIBLE);
            Tx_View.setVisibility(View.GONE);
        }
        else
        {
            Loader loader = getSupportLoaderManager().getLoader(0);
            if (loader != null)
            {
                loader.cancelLoad();
            }
            loading.setVisibility(View.GONE);
            Tx_View.setVisibility(View.VISIBLE);
            Tx_View.setText("Website Tidak Ditemukan");

        }
    }

    @Override
    public Loader <String> onCreateLoader (int id, Bundle args) {

        return new AsyncTaskLoaderWeb(this,url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Tx_View.setText(data);
        loading.setVisibility(View.GONE);
        Tx_View.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
