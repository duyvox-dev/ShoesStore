package com.example.myshoesstore.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.R;
import com.example.myshoesstore.models.CategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.CategoryCategoryAdapter;
import adapters.HomeCategoryAdapter;
import adapters.ViewAllProductsAdapter;

public class CategoryFragment extends Fragment {
    FirebaseFirestore db;

    RecyclerView rvCategory;
    List<CategoryModel> categoryList;
    CategoryCategoryAdapter categoryCategoryAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragment_category, container, false);
        db = FirebaseFirestore.getInstance();

        rvCategory = root.findViewById(R.id.recyclerViewCategory);

        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryList = new ArrayList<>();
        categoryCategoryAdapter = new CategoryCategoryAdapter(getActivity(), categoryList);
        rvCategory.setAdapter(categoryCategoryAdapter);



        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryList.add(categoryModel);
                                categoryCategoryAdapter.notifyDataSetChanged();
                           }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }

                });

        return root;
    }

}