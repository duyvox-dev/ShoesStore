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
import com.example.myshoesstore.R;
import com.example.myshoesstore.ViewAllProductsActivity;
import com.example.myshoesstore.models.CategoryModel;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    Context context;
    List<CategoryModel> homeCategoryList;

    public HomeCategoryAdapter(Context context, List<CategoryModel> homeCategoryList) {
        this.context = context;
        this.homeCategoryList = homeCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(homeCategoryList.get(position).getImg_url()).into(holder.imgHomeCategory);
        holder.txtHomeCategoryName.setText(homeCategoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllProductsActivity.class);
                intent.putExtra("type", homeCategoryList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHomeCategory;
        TextView txtHomeCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHomeCategory = itemView.findViewById(R.id.imageViewHomeCategory);
            txtHomeCategoryName = itemView.findViewById(R.id.textViewHomeCategoryName);
        }
    }
}
