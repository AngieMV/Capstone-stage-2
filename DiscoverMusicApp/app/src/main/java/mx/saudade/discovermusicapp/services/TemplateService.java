package mx.saudade.discovermusicapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by angie on 6/26/16.
 */
public abstract class TemplateService extends IntentService {

    protected static final String TAG = TemplateService.class.getSimpleName();;

    private String event;

    private String extra;

    public TemplateService(String tag, String event, String extra) {
        super(tag);
        this.event = event;
        this.extra = extra;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;

        try {
            Uri uri = getUri(intent);

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream == null) {
                return;
            }

            String input = convertStream(inputStream);

            Parcelable result = getResult(input);

            sendMessage(result);

        } catch (MalformedURLException exception) {
            Log.e(TAG, "MalformedURLException " + exception);

        } catch (IOException ioException) {
            Log.e(TAG, "IOException " + ioException);

        } catch (Exception exception) {
            Log.e(TAG, "Exception " + exception);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return;
    }

    private void sendMessage(Parcelable parcelable) {
        Log.d(TAG, "Broadcasting result: " + parcelable);
        Intent intent = new Intent(event);
        intent.putExtra(extra, parcelable);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private String convertStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        String temp = buffer.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                , StringUtils.EMPTY);
        temp = temp.replace("&", "and");
        Log.d(TAG, "READER " + temp);
        return temp;
    }

    protected abstract Uri getUri(Intent intent);

    protected abstract Parcelable getResult(String inputStream) throws Exception;

}
