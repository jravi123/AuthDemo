package authdemo.gae.com.authdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void performNoAuthFlow(View v){

        new AsyncNoAuthFlow().execute(new Pair<Context, String>(this, "Welcome"));

    }

    public void performAuthFlow(View v){

        new AsyncAuthFlow().execute(new Pair<Context, String>(this, "Welcome"));

    }
}
