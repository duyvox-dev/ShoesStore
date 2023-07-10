package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.R;
import com.example.myshoesstore.models.MyCartModel;
import com.example.myshoesstore.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> cartModelList;

    FirebaseDatabase database;
    FirebaseAuth auth;
    public MyOrderAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        holder.txtOrderProductName.setText(cartModelList.get(position).getProductName());
        holder.txtOrderProductPrice.setText(cartModelList.get(position).getProductPrice());
        holder.txtOrderDate.setText(cartModelList.get(position).getCurrentDate());
        holder.txtOrderTime.setText(cartModelList.get(position).getCurrentTime());
        holder.txtOrderQuantity.setText(cartModelList.get(position).getQuantity());
        holder.txtOrderTotalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                holder.txtOrderUsername.setText(user.getName());
                holder.txtOrderUserPhoneNum.setText(user.getPhoneNumber());
                holder.txtOrderUserAddress.setText(user.getAddress());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderDate, txtOrderTime, txtOrderProductName, txtOrderProductPrice, txtOrderQuantity, txtOrderTotalPrice, txtOrderUsername, txtOrderUserPhoneNum, txtOrderUserAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            txtOrderTime = itemView.findViewById(R.id.textViewOrderTime);
            txtOrderProductName = itemView.findViewById(R.id.textViewOrderProductName);
            txtOrderProductPrice = itemView.findViewById(R.id.textViewOrderProductPrice);
            txtOrderQuantity = itemView.findViewById(R.id.textViewOrderQuantity);
            txtOrderTotalPrice = itemView.findViewById(R.id.textViewOrderPrice);
            txtOrderUsername = itemView.findViewById(R.id.textViewOrderUsername);
            txtOrderUserPhoneNum = itemView.findViewById(R.id.textViewOrderUserPhoneNumber);
            txtOrderUserAddress = itemView.findViewById(R.id.textViewOrderUserAddress);
        }
    }
}
