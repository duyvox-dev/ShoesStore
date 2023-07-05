package adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshoesstore.R;
import com.example.myshoesstore.models.RecommendedModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    Context context;
    List<RecommendedModel> recommendedModelList;

    public RecommendedAdapter(Context context, List<RecommendedModel> recommendedModelList) {
        this.context = context;
        this.recommendedModelList = recommendedModelList;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(recommendedModelList.get(position).getImg_url()).into(holder.imgRecommeded);
        holder.txtRecommededName.setText(recommendedModelList.get(position).getName());
        holder.txtRecommededDescription.setText(recommendedModelList.get(position).getDescription());
        holder.txtRecommendedRating.setText(recommendedModelList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return recommendedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRecommeded;
        TextView txtRecommededName, txtRecommededDescription, txtRecommendedRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgRecommeded = itemView.findViewById(R.id.imageViewRecommended);
            txtRecommededName = itemView.findViewById(R.id.textViewRecommendedName);
            txtRecommededDescription = itemView.findViewById(R.id.textViewRecommendDescription);
            txtRecommendedRating = itemView.findViewById(R.id.textViewRecommendedRating);
        }
    }
}
