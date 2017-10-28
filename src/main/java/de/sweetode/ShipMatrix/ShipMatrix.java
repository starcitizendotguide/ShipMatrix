package de.sweetode.ShipMatrix;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class ShipMatrix {

    public static void main(String[] args) throws IOException {
        ShipMatrix matrix = new ShipMatrix();
        FileUtils.writeStringToFile(new File("data.json"),  gson.toJson(matrix.fetch()), Charset.forName("UTF-8"));
    }


    //---
    private final static OkHttpClient client = new OkHttpClient.Builder().build();
    private final static Gson gson = new Gson();

    public ShipMatrix() {}

    public List<JsonObject> fetch() throws IOException {

        List<JsonObject> matrix = new LinkedList<>();

        final int LOWER = 1;
        final int UPPER = 57;

        for(int i = LOWER; i <= UPPER; i++) {

            Request request = new Request.Builder()
                                .get()
                                .url(String.format("https://robertsspaceindustries.com/ship-matrix/index?chassis_id=%d", i))
                            .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();
            response.close();

            //--- Parse
            JsonObject object = gson.fromJson(body, JsonObject.class);
            object.getAsJsonArray("data").forEach(e -> matrix.add(e.getAsJsonObject()));

            System.out.println(String.format("%d/%d", i, UPPER));

        }

        client.dispatcher().cancelAll();
        return matrix;


    }

}
