package com.example.myshoesstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myshoesstore.models.ProductModel;

public class DetailedActivity extends AppCompatActivity {

    ImageView imgDetailed, imgIncreaseQuantity, imgDecreaseQuantity;
    TextView txtDetailedPrice, txtDetailedRating, txtDetailedDescription, txtQuantity;
    Button btnAddToCart;
    Toolbar tbDetailed;
    ProductModel productModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        imgDetailed = findViewById(R.id.imageViewDetailed);
        imgIncreaseQuantity = findViewById(R.id.imageViewAdd);
        imgDecreaseQuantity = findViewById(R.id.imageViewMinus);
        txtDetailedPrice = findViewById(R.id.textViewDetailedPrice);
        txtDetailedRating = findViewById(R.id.textViewDetailedRating);
        txtDetailedDescription = findViewById(R.id.textViewDetailedDescription);
        txtQuantity = findViewById(R.id.textViewQuantity);
        btnAddToCart = findViewById(R.id.buttonAddToCart);
        tbDetailed = findViewById(R.id.toolBarDetailed);
        setSupportActionBar(tbDetailed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ProductModel){
            productModel = (ProductModel) object;
        }
        if (productModel != null){
            Glide.with(getApplicationContext()).load(productModel.getImg_url()).into(imgDetailed);
            txtDetailedRating.setText(productModel.getRating());
            txtDetailedDescription.setText(productModel.getDescription());
            txtDetailedPrice.setText("Giá : " + productModel.getPrice() + " VNĐ");


        }
    }
}