package de.sweetode.ShipMatrix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShipMatrix {

    public static void main(String[] args) throws IOException {
        ShipMatrix matrix = new ShipMatrix();
        matrix.execute();
    }

    //---
    private final static OkHttpClient client = new OkHttpClient.Builder().build();
    private final static Gson gson = new Gson();

    public ShipMatrix() {}

    public void execute() throws IOException {
        FileUtils.writeStringToFile(new File(String.format("data/%s.json", CompareMatrix.dateFormat.format(Date.from(Instant.now())))), gson.toJson(this.fetch()), Charset.forName("UTF-8"));
    }

    public List<JsonObject> fetch() throws IOException {

        List<JsonObject> matrix = new LinkedList<>();

        final int MAX_OVERFLOW = 10;
        int currentOverflow = MAX_OVERFLOW;
        for(int i = 1;; i++) {

            Request request = new Request.Builder()
                                .get()
                                .url(String.format("https://robertsspaceindustries.com/ship-matrix/index?chassis_id=%d", i))
                            .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();
            response.close();

            //--- Parse
            JsonArray object = gson.fromJson(body, JsonObject.class).getAsJsonArray("data");

            if(object.size() == 0) {
                currentOverflow--;

                if(currentOverflow <= 0) {
                    System.out.println("Done!");
                    break;
                }

                continue;
            }
            currentOverflow = MAX_OVERFLOW;

            object.forEach(e -> {

                JsonObject entry = e.getAsJsonObject();

                //--- Remove media
                entry.remove("media");
                entry.getAsJsonObject("manufacturer").remove("media");

                matrix.add(entry);

            });

        }

        client.dispatcher().cancelAll();
        return matrix;

    }

}
