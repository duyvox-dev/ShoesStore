package com.example.myshoesstore.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.MainActivity;
import com.example.myshoesstore.R;

import com.example.myshoesstore.models.CategoryModel;
import com.example.myshoesstore.models.MyCartModel;
import com.example.myshoesstore.models.PopularModel;
import com.example.myshoesstore.models.ProductModel;
import com.example.myshoesstore.models.RecommendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import adapters.HomeCategoryAdapter;
//import adapters.PopularAdapter;
import adapters.ProductAdapter;
import adapters.RecommendedAdapter;
import adapters.ViewAllProductsAdapter;

public class HomeFragment extends Fragment {
    ScrollView svHome;
    ProgressBar pbHome;
    EditText edtSearch;
    RecyclerView rvPopularProducts, rvHomeCategory, rvRecommended, rvSearch;
    FirebaseFirestore db;

    List<ProductModel> productModelList;
    ProductAdapter productAdapter;

    List<ProductModel> productModelList2;
    RecommendedAdapter recommendedAdapter;


    List<ProductModel> productModelList3;
    ViewAllProductsAdapter viewAllProductsAdapter;

    List<CategoryModel> homeCategoryList;
    HomeCategoryAdapter homeCategoryAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);




        db = FirebaseFirestore.getInstance();

        svHome = root.findViewById(R.id.scrollViewHome);
        pbHome = root.findViewById(R.id.progressBarHome);
        rvPopularProducts = root.findViewById(R.id.recyclerViewPopularProducts);
        rvHomeCategory = root.findViewById(R.id.recyclerViewHomeCategory);
        rvRecommended = root.findViewById(R.id.recyclerViewRecommended);
        rvSearch = root.findViewById(R.id.recyclerViewSearch);
        edtSearch = root.findViewById(R.id.edtSearchBox);

        pbHome.setVisibility(View.VISIBLE);
        svHome.setVisibility(View.GONE);

        productModelList3 = new ArrayList<>();
        viewAllProductsAdapter = new ViewAllProductsAdapter(getContext(), productModelList3);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearch.setAdapter(viewAllProductsAdapter);
        rvSearch.setHasFixedSize(true);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    productModelList3.clear();
                    viewAllProductsAdapter.notifyDataSetChanged();
                }else{
                    searchProduct(s.toString());
                }
            }
        });

        rvPopularProducts.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(getActivity(), productModelList);
        rvPopularProducts.setAdapter(productAdapter);

        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pId = document.getId();
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productModel.setpId(pId); // Set the document ID in the ProductModel

                                productModelList.add(productModel);
                                productAdapter.notifyDataSetChanged();

                                pbHome.setVisibility(View.GONE);
                                svHome.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }

                });


        rvHomeCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeCategoryList = new ArrayList<>();
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), homeCategoryList);
        rvHomeCategory.setAdapter(homeCategoryAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                homeCategoryList.add(categoryModel);
                                homeCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        rvRecommended.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        productModelList2 = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), productModelList2);
        rvRecommended.setAdapter(recommendedAdapter);

        db.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String pId = document.getId();
                                ProductModel productModel = document.toObject(ProductModel.class);
                                productModel.setpId(pId); // Set the document ID in the ProductModel

                                productModelList2.add(productModel);
                                recommendedAdapter.notifyDataSetChanged();

                                pbHome.setVisibility(View.GONE);
                                svHome.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        return root;
    }

    private void searchProduct(String type) {
        if (!type.isEmpty()){
            db.collection("Products").whereEqualTo("typeId", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null){
                        productModelList3.clear();
                        viewAllProductsAdapter.notifyDataSetChanged();
                        for (DocumentSnapshot doc : task.getResult().getDocuments()){
                            ProductModel productModel = doc.toObject(ProductModel.class);
                            productModelList3.add(productModel);
                            viewAllProductsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }


}