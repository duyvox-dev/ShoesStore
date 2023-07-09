package com.example.myshoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myshoesstore.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.ViewAllProductsAdapter;

public class ViewAllProductsActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    RecyclerView rv;
    ViewAllProductsAdapter viewAllProductsAdapter;
    List<ProductModel> productModelList;
    Toolbar tb;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_products);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        rv = findViewById(R.id.recyclerViewAllProducts);
        rv.setVisibility(View.GONE);
        tb = findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        rv.setLayoutManager(new LinearLayoutManager(this));

        productModelList = new ArrayList<>();
        viewAllProductsAdapter = new ViewAllProductsAdapter(this, productModelList);
        rv.setAdapter(viewAllProductsAdapter);

        if (type != null && type.equalsIgnoreCase("sneaker")){
            firestore.collection("Products").whereEqualTo("typeId","sneaker").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("flipflops")){
            firestore.collection("Products").whereEqualTo("typeId","flipflops").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("dressshoes")){
            firestore.collection("Products").whereEqualTo("typeId","dressshoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("boots")){
            firestore.collection("Products").whereEqualTo("typeId","boots").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("flats")){
            firestore.collection("Products").whereEqualTo("typeId","flats").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("sandals")){
            firestore.collection("Products").whereEqualTo("typeId","sandals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        if (type != null && type.equalsIgnoreCase("highheels")){
            firestore.collection("Products").whereEqualTo("typeId","highheels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
                        productModelList.add(productModel);
                        viewAllProductsAdapter.notifyDataSetChanged();
                        pb.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}