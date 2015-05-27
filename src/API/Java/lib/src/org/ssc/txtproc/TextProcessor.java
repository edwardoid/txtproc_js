/*
	jtxtproc
	Copyright (C) 2014  Edward Sargsyan
	avr_libs is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	jtxtproc is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	You should have received a copy of the GNU General Public License
	along with avr_libs.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.ssc.txtproc;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.istack.internal.NotNull;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduards on 5/26/2015.
 */
public class TextProcessor {

    private String mHost = "localhost";
    private Integer mPort = new Integer(8080);

    public TextProcessor(@NotNull String host, @NotNull Integer port) {
        mHost = host;
        mPort = port;
    }

    private HttpGet buildRequest(@NotNull Options opt) {
        HttpGet connMethod = new HttpGet(URI.create(mHost + ":" + mPort.toString()));

        if(opt.GetOriginalText)
            connMethod.setHeader("include_original_doc", "true");

        if(opt.GetTokens)
            connMethod.setHeader("include_tokens", "true");

        if(!opt.GetStops)
            connMethod.setHeader("exclude_stops", "true");

        if(opt.GetNonStops)
            connMethod.setHeader("include_non_stops", "true");

        if(opt.StemByPorter)
            connMethod.setHeader("run_porter", "true");

        if(opt.StemByLancaster)
            connMethod.setHeader("run_lancaster", "true");

        if(opt.MeasureByLeveshtineDistance)
            connMethod.setHeader("calculate_levenshtine", "true");

        if(opt.MeasureByJaroWinkler)
            connMethod.setHeader("calculate_jaro_winkler", "true");

        if(opt.GetNGrams > 0)
            connMethod.setHeader("include_n_grams", (new Integer(opt.GetNGrams)).toString());

        return connMethod;
    }

    public Result process(@NotNull String text, @NotNull Options opt) throws IOException {
        HttpGet connMethod = buildRequest(opt);

        connMethod.setHeader("doc", text);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(connMethod);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        String output = "";
        String line = null;
        while ((line = rd.readLine()) != null) {
            output += line;
        }

        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(output);
        JsonObject obj = rootElement.getAsJsonObject();
        if(obj == null)
            return null;

        Result res = new Result();

        JsonElement origMbr = obj.get("original");
        if(origMbr != null) {
            res.setOriginalText(origMbr.getAsString());
        }

        res.setTokens(getAsStringArray(obj.get("tokens")));
        res.setTokens(getAsStringArray(obj.get("stops")));
        res.setTokens(getAsStringArray(obj.get("non_stops")));

        JsonObject stemmedMbr = obj.getAsJsonObject("stemmed");
        if(stemmedMbr != null) {
            res.setPorterStems(getAsStringArray(stemmedMbr.get("porter")));
            res.setLancasterStems(getAsStringArray(stemmedMbr.get("lancaster")));
        }

        JsonObject normalizedMbr = obj.getAsJsonObject("normalized");
        if(normalizedMbr != null) {
            JsonElement porterChild = normalizedMbr.get("porter");
            if(porterChild != null)
                res.setNormalizedByPorter(porterChild.getAsString());
            JsonElement lancasterChild = normalizedMbr.get("lancaster");
            if(lancasterChild != null)
                res.setNormalizedByLancaster(lancasterChild.getAsString());
        }


        JsonObject distancesMbr = obj.getAsJsonObject("distances");
        if(distancesMbr != null) {
            res.setLevenshtineDist(getAsDoubleArray(distancesMbr.get("levenshtine")));
            res.setJaroWinkler(getAsDoubleArray(distancesMbr.get("jaro_winkler")));
        }

        JsonArray ngramsMbr = obj.getAsJsonArray("ngrams");
        if(ngramsMbr != null) {
            ArrayList<List<String> > arrayOfNGrams = new ArrayList<List<String> >();
            for(JsonElement singleNGram : ngramsMbr) {
                List<String> words = getAsStringArray(singleNGram);
                arrayOfNGrams.add(words);
            }
            res.setNGrams(arrayOfNGrams);
        }

        return res;
    }

    private List<String> getAsStringArray(JsonElement element) {
        if(element == null)
            return null;
        JsonArray childElems = element.getAsJsonArray();
        if(childElems == null)
            return null;
        ArrayList<String> values = new ArrayList<String>(childElems.size());
        for (JsonElement elem : childElems) {
            values.add(elem.getAsString());
        }

        return values;
    }

    private List<Double> getAsDoubleArray(JsonElement element) {
        if(element == null)
            return null;
        JsonArray childElems = element.getAsJsonArray();
        if(childElems == null)
            return null;
        ArrayList<Double> values = new ArrayList<Double>(childElems.size());
        for (JsonElement elem : childElems) {
            values.add(new Double(elem.getAsDouble()));
        }

        return values;
    }
}
