package com.lrosa.munchencli.domain;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import picocli.CommandLine;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "foreign")
public class MunchenService implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "zapnummer provided by kvr")
    private String zapnummer;

    public Integer call() throws Exception {
        System.out.println("Searching for: "+zapnummer);
        var client = HttpClients.createDefault();
        var httpPost = new HttpPost("https://www17.muenchen.de/EATWebSearch/Auskunft");

        var params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("zapnummer", zapnummer));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        var response = client.execute(httpPost);

        var result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        if(!result.contains("liegt noch nicht zur Abholung bereit")) {
            System.out.println("Founded, go to KVR right now!");
        } else {
            System.out.println("Not this time, try again tomorrow");
        }
        return 0;
    }
}
