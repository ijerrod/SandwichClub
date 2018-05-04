package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        try {

            //Create the sandwich object
            JSONObject sandwichJSONObject = new JSONObject(json);

            //Use optJSONObject rather than 'get' in case there is null
            JSONObject name = sandwichJSONObject.optJSONObject("name");
            String mainName = name.optString("mainName");

            //create and add values for AKA Array
            JSONArray akaArray = name.optJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for(int i = 0; i < akaArray.length(); i++) {
                alsoKnownAs.add(akaArray.getString(i));
            }

            //create strings for origin, description, and image
            String placeOfOrigin = sandwichJSONObject.optString("placeOfOrigin");
            String description = sandwichJSONObject.optString("description");
            String image = sandwichJSONObject.optString("image");

            //create and add values for ingredients array.
            JSONArray ingredientsArray = sandwichJSONObject.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();
            for(int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.optString(i));
            }

            //return values
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }

        catch(JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
