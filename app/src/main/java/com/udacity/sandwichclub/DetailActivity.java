package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Add back arrow to Sandwich Detail Activity
        //Found info on https://www.youtube.com/watch?v=s3pheMAmaPI
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Find AKA textview, cycle through AKA array and append name to sandwich
        TextView mAlsoKnownAs = findViewById(R.id.also_known_tv);
        for(int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            mAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(i) + "\n");
        }

        //Find Ingredients textview, cycle and append items found in array.
        TextView mIngredients = findViewById(R.id.ingredients_tv);
        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            mIngredients.append(sandwich.getIngredients().get(i) + "\n");
        }

        //Find Description textview and enter text
        TextView mDescription = findViewById(R.id.description_tv);
        mDescription.setText(sandwich.getDescription());

        //Find Origin textview and enter text
        TextView mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
    }
}
