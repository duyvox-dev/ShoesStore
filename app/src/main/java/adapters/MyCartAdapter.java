package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.R;
import com.example.myshoesstore.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.txtProductName.setText(cartModelList.get(position).getProductName());
        holder.txtProductPrice.setText(cartModelList.get(position).getProductPrice());
        holder.txtDate.setText(cartModelList.get(position).getCurrentDate());
        holder.txtTime.setText(cartModelList.get(position).getCurrentTime());
        holder.txtQuantity.setText(cartModelList.get(position).getQuantity());
        holder.txtTotalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));


        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(position).getDocumentId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(position));
                                    notifyDataSetChanged();
                                    // Check if the cart is empty after removing the item
                                    if (cartModelList.isEmpty()) {
                                        totalPrice = 0; // Set totalPrice to 0 when the cart is empty
                                    }

                                    calculateTotalPrice(); // Recalculate the total price
                                    Toast.makeText(context, "Đã xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        calculateTotalPrice();
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
    private void calculateTotalPrice() {
        totalPrice = 0;
        for (MyCartModel cartModel : cartModelList) {
            totalPrice += cartModel.getTotalPrice();
        }

        if (cartModelList.isEmpty()) {
            totalPrice = 0; // Set totalPrice to 0 when the cart is empty
        }

        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice, txtDate, txtTime, txtTotalPrice, txtQuantity;
        ImageView imgRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.textViewCartProductName);
            txtProductPrice = itemView.findViewById(R.id.textViewCartProductPrice);
            txtDate = itemView.findViewById(R.id.textViewCurrentDate);
            txtTime = itemView.findViewById(R.id.textViewCurrentTime);
            txtQuantity = itemView.findViewById(R.id.textViewCartQuantity);
            txtTotalPrice = itemView.findViewById(R.id.textViewCartPrice);
            imgRemove = itemView.findViewById(R.id.imageViewRemoveItem);
        }
    }
}
