package com.example.myshoesstore.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.R;

import com.example.myshoesstore.models.CategoryModel;
import com.example.myshoesstore.models.PopularModel;
import com.example.myshoesstore.models.RecommendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.HomeCategoryAdapter;
import adapters.PopularAdapter;
import adapters.RecommendedAdapter;

public class HomeFragment extends Fragment {
    ScrollView svHome;
    ProgressBar pbHome;
    RecyclerView rvPopularProducts, rvHomeCategory, rvRecommended;
    FirebaseFirestore db;

    List<PopularModel> popularModelList;
    PopularAdapter popularAdapter;


    List<CategoryModel> homeCategoryList;
    HomeCategoryAdapter homeCategoryAdapter;


    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        svHome = root.findViewById(R.id.scrollViewHome);
        pbHome = root.findViewById(R.id.progressBarHome);
        rvPopularProducts = root.findViewById(R.id.recyclerViewPopularProducts);
        rvHomeCategory = root.findViewById(R.id.recyclerViewHomeCategory);
        rvRecommended = root.findViewById(R.id.recyclerViewRecommended);

        pbHome.setVisibility(View.VISIBLE);
        svHome.setVisibility(View.GONE);

        rvPopularProducts.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(), popularModelList);
        rvPopularProducts.setAdapter(popularAdapter);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapter.notifyDataSetChanged();

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
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), recommendedModelList);
        rvRecommended.setAdapter(recommendedAdapter);

        db.collection("RecommendedProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        return root;
    }


}