package com.example.myshoesstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myshoesstore.models.MyCartModel;
import com.example.myshoesstore.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapters.MyCartAdapter;
import adapters.MyOrderAdapter;


public class MyOrdersFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView rvOrder;
    MyOrderAdapter orderAdapter;
    List<MyCartModel> cartModelList;
    User user;
    ProgressBar pb;
    ConstraintLayout emptyLayout, nonEmptyLayout;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        emptyLayout = root.findViewById(R.id.constraintOrder1);
        nonEmptyLayout = root.findViewById(R.id.constraintOrder2);
        nonEmptyLayout.setVisibility(View.GONE);


        rvOrder = root.findViewById(R.id.recyclerViewOrder);
        pb = root.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getActivity(), cartModelList);
        rvOrder.setAdapter(orderAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments() ){
                                String documentId = documentSnapshot.getId();

                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);


                                orderAdapter.notifyDataSetChanged();
                                if (cartModelList.isEmpty()) {
                                    // Show empty layout if the cart is empty
                                    pb.setVisibility(View.GONE);
                                    nonEmptyLayout.setVisibility(View.GONE);
                                    emptyLayout.setVisibility(View.VISIBLE);
                                } else {
                                    // Show non-empty layout if the cart has items
                                    pb.setVisibility(View.GONE);
                                    nonEmptyLayout.setVisibility(View.VISIBLE);
                                    emptyLayout.setVisibility(View.GONE);
                                }


                            }
                        }
                    }
                });

        return root;
    }
}
