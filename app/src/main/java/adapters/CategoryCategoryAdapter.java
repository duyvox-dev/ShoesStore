package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoesstore.R;

public class CategoryCategoryAdapter extends RecyclerView.Adapter<CategoryCategoryAdapter.ViewHolder> {
    @NonNull
    @Override
    public CategoryCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCategoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView txtNameCategory, txtDescriptionCategory, txtDiscountCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.imageViewCategory);
            txtNameCategory = itemView.findViewById(R.id.textViewNameCategory);
            txtDescriptionCategory = itemView.findViewById(R.id.textViewDescriptionCategory);
            txtDiscountCategory = itemView.findViewById(R.id.textViewDiscountCategory);
        }
    }
}
