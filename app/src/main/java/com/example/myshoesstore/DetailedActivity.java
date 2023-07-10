package com.example.myshoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myshoesstore.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    int quantity = 1;
    int price = 0;
    ImageView imgDetailed, imgIncreaseQuantity, imgDecreaseQuantity;
    TextView txtDetailedPrice, txtDetailedName, txtDetailedRating, txtDetailedDescription, txtQuantity;
    Button btnAddToCart;
    Toolbar tbDetailed;
    ProductModel productModel = null;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        imgDetailed = findViewById(R.id.imageViewDetailed);
        imgIncreaseQuantity = findViewById(R.id.imageViewAdd);
        imgDecreaseQuantity = findViewById(R.id.imageViewMinus);
        txtDetailedName = findViewById(R.id.textViewDetailedName);
        txtDetailedPrice = findViewById(R.id.textViewDetailedPrice);
        txtDetailedRating = findViewById(R.id.textViewDetailedRating);
        txtDetailedDescription = findViewById(R.id.textViewDetailedDescription);
        txtQuantity = findViewById(R.id.textViewQuantity);
        btnAddToCart = findViewById(R.id.buttonAddToCart);
        tbDetailed = findViewById(R.id.toolBarDetailed);
        setSupportActionBar(tbDetailed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ProductModel){
            productModel = (ProductModel) object;
        }
        if (productModel != null){
            Glide.with(getApplicationContext()).load(productModel.getImg_url()).into(imgDetailed);
            txtDetailedName.setText(productModel.getName());
            txtDetailedRating.setText(productModel.getRating());
            txtDetailedDescription.setText(productModel.getDescription());
            txtDetailedPrice.setText(productModel.getPrice() + " đ");
            price = productModel.getPrice() * quantity;

        }
        imgIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < 10){
                    quantity++;
                    txtQuantity.setText(String.valueOf(quantity));
                    price = productModel.getPrice() * quantity;
                }
            }
        });

        imgDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1){
                    quantity--;
                    txtQuantity.setText(String.valueOf(quantity));
                    price = productModel.getPrice() * quantity;

                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", productModel.getName());
        cartMap.put("productPrice", txtDetailedPrice.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("quantity", txtQuantity.getText().toString());
        cartMap.put("totalPrice", price);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }
}