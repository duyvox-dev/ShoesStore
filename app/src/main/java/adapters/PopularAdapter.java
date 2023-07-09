//package adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.myshoesstore.R;
//import com.example.myshoesstore.models.PopularModel;
//
//import java.util.List;
//
//public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
//
//    private Context context;
//    private List<PopularModel> popularModelList;
//
//    public PopularAdapter(Context context, List<PopularModel> popularModelList) {
//        this.context = context;
//        this.popularModelList = popularModelList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(context).load(popularModelList.get(position).getImg_url()).into(holder.imgPopularProduct);
//        holder.txtPopularProductName.setText(popularModelList.get(position).getName());
//        holder.txtPopularProductDescription.setText(popularModelList.get(position).getDescription());
//        holder.txtPopularProductRating.setText(popularModelList.get(position).getRating());
//        holder.txtPopularProductDiscount.setText(popularModelList.get(position).getDiscount());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return popularModelList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imgPopularProduct;
//        TextView txtPopularProductName, txtPopularProductDescription, txtPopularProductRating, txtPopularProductDiscount;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imgPopularProduct = itemView.findViewById(R.id.imageViewPopularProduct);
//            txtPopularProductName = itemView.findViewById(R.id.textViewPopularProductName);
//            txtPopularProductDescription = itemView.findViewById(R.id.textViewPopularProductDescription);
//            txtPopularProductRating = itemView.findViewById(R.id.textViewPopularProductRating);
//            txtPopularProductDiscount = itemView.findViewById(R.id.textViewPopularProductPrice);
//        }
//    }
//}
