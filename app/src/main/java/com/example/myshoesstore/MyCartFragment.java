package com.example.myshoesstore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myshoesstore.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapters.MyCartAdapter;


public class MyCartFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView txtTotalAmount;
    RecyclerView rvCart;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    ProgressBar pb;
    Button btnOrder;
    ConstraintLayout emptyLayout, nonEmptyLayout;
    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        emptyLayout = root.findViewById(R.id.constraint1);
        nonEmptyLayout = root.findViewById(R.id.constraint2);
        nonEmptyLayout.setVisibility(View.GONE);
        rvCart = root.findViewById(R.id.recyclerViewCart);
        btnOrder = root.findViewById(R.id.buttonOrder);

        txtTotalAmount = root.findViewById(R.id.textViewTotalAmount);
        pb = root.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartModelList);
        rvCart.setAdapter(cartAdapter);
            db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Clear the cartModelList before adding new items
                                cartModelList.clear();

                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    String documentId = documentSnapshot.getId();
                                    MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                    cartModel.setDocumentId(documentId);
                                    cartModelList.add(cartModel);

                                }

                                // Notify the adapter about the data change
                                cartAdapter.notifyDataSetChanged();

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
                            } else {
                                // Handle error case here if necessary
                            }
                        }
                    });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);
            }
        });
        return root;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount",0);
            txtTotalAmount.setText("Tổng cộng: "+totalBill+ " đ");
        }
    };
}