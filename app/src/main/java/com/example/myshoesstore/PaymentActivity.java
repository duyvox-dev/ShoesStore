package com.example.myshoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshoesstore.Api.CreateOrder;
import com.example.myshoesstore.models.MyCartModel;
import com.example.myshoesstore.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {

    ImageView btnBack;
    Button btnPayment;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    TextView tvName, tvAddress, tvNumberPhone, tvMoney;
    List<MyCartModel> cartModelList;
    int totalBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvName = findViewById(R.id.nameOder);
        tvAddress = findViewById(R.id.addressOrder);
        tvNumberPhone = findViewById(R.id.phoneOder);
        tvMoney = findViewById(R.id.moneyOrder);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BundlePackage");
        if (bundle != null) {
            cartModelList = (List<MyCartModel>) bundle.getSerializable("itemList");
             totalBill = bundle.getInt("totalAmount", 0);
            tvMoney.setText(String.valueOf(totalBill));
        }

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        database = FirebaseDatabase.getInstance();

        DatabaseReference databaseRef = database.getReference().child("Users").child(uid);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    tvName.setText(user.getName());
                    tvAddress.setText(user.getAddress());
                    tvNumberPhone.setText(user.getPhoneNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle canceled event
                Log.e("Firebase", "Lỗi: " + error.getMessage());
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        btnPayment = findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "pay zalo");
                // Pass the total bill
                requestZalo(totalBill);
            }
        });
    }

    private void requestZalo(int money) {
        CreateOrder orderApi = new CreateOrder();

        try {
            String moneyString = String.valueOf(money);
            Log.d("test", moneyString);
            JSONObject data = orderApi.createOrder(moneyString);
            String code = data.getString("return_code");
            Log.d("test", code);
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                Log.d("test", token);
                ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        Log.d("test", "success");
                        Toast.makeText(PaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

                        if (cartModelList != null && cartModelList.size()>0){
                            for (MyCartModel model : cartModelList){
                                final HashMap<String, Object> cartMap = new HashMap<>();
                                cartMap.put("productName", model.getProductName());
                                cartMap.put("productPrice", model.getProductPrice());
                                cartMap.put("currentDate", model.getCurrentDate());
                                cartMap.put("currentTime", model.getCurrentTime());
                                cartMap.put("quantity", model.getQuantity());
                                cartMap.put("totalPrice", model.getTotalPrice());
                                cartMap.put("pId", model.getpId());

                                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(PaymentActivity.this, "Đơn hàng của bạn đã được đặt", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                        finish();
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Log.d("test", "cancel");
                        Toast.makeText(PaymentActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Log.d("test", "error payment");
                        Toast.makeText(PaymentActivity.this, "Lỗi liên kết hệ thống !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
