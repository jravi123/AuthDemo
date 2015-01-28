package authdemo.gae.com.authdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.gae.authdemo.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

/**
 * Created by jravi on 1/28/15.
 */
class AsyncAuthFlow extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    /**
     * Class instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    /**
     * Class instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        context = params[0].first;
        String name = params[0].second;


        if (myApiService == null) {
            GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                    context, Constants.ANDROID_AUDIENCE);
            credential.setSelectedAccountName("jayashree.ravi.raj@gmail.com");// hard coded my email Id for this test..



            // Both the ways are throwing NullPointer
//            MyApi.Builder builder = new MyApi.Builder(HTTP_TRANSPORT,  // Tried out this way too..
//                    JSON_FACTORY, credential);
//
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(),
                    credential).
                    setRootUrl("https://mbcc-maps.appspot.com/_ah/api/").  // as per online doc these are not needed. Added anyways
                    setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() // as per online doc these are not needed. Added anyways
            {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });

            myApiService = builder.build();

        }


        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}