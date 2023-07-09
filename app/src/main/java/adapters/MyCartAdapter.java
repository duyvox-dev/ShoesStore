package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.R;
import com.example.myshoesstore.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice = 0;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
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

        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice, txtDate, txtTime, txtTotalPrice, txtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.textViewCartProductName);
            txtProductPrice = itemView.findViewById(R.id.textViewCartProductPrice);
            txtDate = itemView.findViewById(R.id.textViewCurrentDate);
            txtTime = itemView.findViewById(R.id.textViewCurrentTime);
            txtQuantity = itemView.findViewById(R.id.textViewCartQuantity);
            txtTotalPrice = itemView.findViewById(R.id.textViewCartPrice);
        }
    }
}
