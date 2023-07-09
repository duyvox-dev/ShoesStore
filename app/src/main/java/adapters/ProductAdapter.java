package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshoesstore.DetailedActivity;
import com.example.myshoesstore.R;
import com.example.myshoesstore.models.ProductModel;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> productModelList;

    public ProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(productModelList.get(position).getImg_url()).into(holder.imgPopularProduct);
        holder.txtPopularProductName.setText(productModelList.get(position).getName());
        holder.txtPopularProductDescription.setText(productModelList.get(position).getDescription());
        holder.txtPopularProductRating.setText(productModelList.get(position).getRating());
        holder.txtPopularProductPrice.setText(productModelList.get(position).getPrice()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail", productModelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPopularProduct;
        TextView txtPopularProductName, txtPopularProductDescription, txtPopularProductRating, txtPopularProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPopularProduct = itemView.findViewById(R.id.imageViewPopularProduct);
            txtPopularProductName = itemView.findViewById(R.id.textViewPopularProductName);
            txtPopularProductDescription = itemView.findViewById(R.id.textViewPopularProductDescription);
            txtPopularProductRating = itemView.findViewById(R.id.textViewPopularProductRating);
            txtPopularProductPrice = itemView.findViewById(R.id.textViewPopularProductPrice);
        }
    }
}
