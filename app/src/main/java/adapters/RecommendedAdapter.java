package adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
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
import com.example.myshoesstore.models.RecommendedModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> productModelList;

    public RecommendedAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(productModelList.get(position).getImg_url()).into(holder.imgRecommeded);
        holder.txtRecommededName.setText(productModelList.get(position).getName());
        holder.txtRecommededDescription.setText(productModelList.get(position).getDescription());
        holder.txtRecommendedRating.setText(productModelList.get(position).getRating());
        holder.txtRecommendedPrice.setText(productModelList.get(position).getPrice()+" đ");

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

        ImageView imgRecommeded;
        TextView txtRecommededName, txtRecommededDescription, txtRecommendedRating, txtRecommendedPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRecommeded = itemView.findViewById(R.id.imageViewRecommended);
            txtRecommededName = itemView.findViewById(R.id.textViewRecommendedName);
            txtRecommededDescription = itemView.findViewById(R.id.textViewRecommendDescription);
            txtRecommendedRating = itemView.findViewById(R.id.textViewRecommendedRating);
            txtRecommendedPrice = itemView.findViewById(R.id.textViewRecommendedPrice);
        }
    }
}
