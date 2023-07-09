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

import org.w3c.dom.Text;

import java.util.List;

public class ViewAllProductsAdapter extends RecyclerView.Adapter<ViewAllProductsAdapter.ViewHolder> {
    Context context;
    List<ProductModel> list;

    public ViewAllProductsAdapter(Context context, List<ProductModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imgCat);
        holder.txtNameCat.setText(list.get(position).getName());
        holder.txtDescriptionCat.setText(list.get(position).getDescription());
        holder.txtRatingCat.setText(list.get(position).getRating());
        holder.txtPriceCat.setText(list.get(position).getPrice()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCat;
        TextView txtNameCat, txtDescriptionCat, txtRatingCat, txtPriceCat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCat = itemView.findViewById(R.id.imageViewCategory);
            txtNameCat = itemView.findViewById(R.id.textViewNameCategory);
            txtDescriptionCat = itemView.findViewById(R.id.textViewDescriptionCategory);
            txtRatingCat = itemView.findViewById(R.id.textViewRatingCategory);
            txtPriceCat = itemView.findViewById(R.id.textViewPriceCategory);
        }
    }
}
