package com.waracle.cakemgr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.dto.Cake;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@PropertySource("classpath:cake-manager.properties")
@Service
public class CakeServiceImpl implements CakeService {

    @Value("${server.file.url}")
    private String filename;

    @PostConstruct
    public void init() {
        try (InputStream inputStream = new URL(filename).openStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            cakeMap.clear();
            StringBuilder builder = new StringBuilder();
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                builder.append(line);
            }
            JSONParser parser = new JSONParser();
            //Read JSON file
            JSONArray cakeList = (JSONArray) parser.parse(builder.toString());

            //Iterate over cake array
            for (Object cakeObject : cakeList) {
                JSONObject cakeJson = (JSONObject) cakeObject;
                Cake cake = new Cake();
                cake.setTitle((String) cakeJson.get("title"));
                cake.setDescription((String) cakeJson.get("desc"));
                cake.setImage((String) cakeJson.get("image"));
                cakeMap.put(cake.getTitle().toLowerCase().trim(), cake);
            }
        }
        catch (ParseException e) {
            throw new RuntimeException("Unable to parse JSON from file " + e);
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to read file from server " + e);
        }
    }

    @Override
    public List<Cake> getCakes() {
        Collection<Cake> cakes = cakeMap.values();
        return new ArrayList<>(cakes);
    }

    @Override
    public Cake addCake(Cake cake) {
        return cakeMap.put(cake.getTitle(), cake);
    }

    @Override
    public Cake getCake(String title) {
        return cakeMap.get(title.toLowerCase().trim());
    }

    @Override
    public String getCakesAsJson() {
        String cakes;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            cakes = objectMapper.writeValueAsString(getCakes());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cakes;
    }
}
