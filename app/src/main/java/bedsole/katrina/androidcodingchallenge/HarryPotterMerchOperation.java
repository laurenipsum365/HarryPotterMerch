package bedsole.katrina.androidcodingchallenge;

import android.content.Context;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HarryPotterMerchOperation {

    public String url = "http://de-coding-test.s3.amazonaws.com/books.json";

    private Context context;

    private HPMerchDataListener hpMerchDataListener;

    public interface HPMerchDataListener {
        void onHPDataLoaded(List<HPMerch> hpMerchList);
    }

    public HarryPotterMerchOperation(Context context) {
        this.context = context;
    }

    public void setHpMerchDataListener(HPMerchDataListener hpMerchDataListener) {
        this.hpMerchDataListener = hpMerchDataListener;
    }

    public void getHPData() {

        final ArrayList<HPMerch> harryPotterMerchList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            Handler mainHandler = new Handler(context.getMainLooper());

            @Override
            public void onFailure(final Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                String responseData = null;
                try {
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray jsonArray = new JSONArray(responseData);

                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String title = object.optString("title");
                            String imageUrl = object.optString("imageURL");
                            String author = object.optString("author");

                            HPMerch harryPotterMerch = new HPMerch(title, imageUrl, author);
                            harryPotterMerchList.add(harryPotterMerch);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (hpMerchDataListener != null) {
                            hpMerchDataListener.onHPDataLoaded(harryPotterMerchList);
                        }
                    }
                });
            }
        });

    }
}
