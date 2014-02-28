package com.kakao.interview.imagegrabber;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            new ProcessParsingTask().execute();
//            android.support.v7.widget.GridLayout = new android.support.v7.widget.GridLayout();
        }

        //AsyncTask<Params,Progress,Result>
        private class ProcessParsingTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                Document doc;
                try {

                    // need http protocol
                    doc = Jsoup.connect("http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx").get();

//                    //get all images
//                    doc = Jsoup.connect("http://www.yahoo.com").get();
                    //서비스 페이지에 따라 204에러 날수 있다.(서비스단에서 컨텐츠 보기를 막음) 해당 에러 처리 필요
                    Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                    for (Element image : images) {

                        Log.d(TAG, "src : " + image.attr("src"));
                        Log.d(TAG, "height : " + image.attr("height"));
                        Log.d(TAG, "width : " + image.attr("width"));
                        Log.d(TAG, "alt : " + image.attr("alt"));

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }


        }

    }

}
