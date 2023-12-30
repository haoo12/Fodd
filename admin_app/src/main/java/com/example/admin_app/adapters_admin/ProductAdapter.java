package com.example.admin_app.adapters_admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admin_app.R;
import com.example.admin_app.activities_admin.Admin_MainActivity;
import com.example.admin_app.models.Product_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Admin_MainActivity activity;
    //private Update_Pro_Activity updateProActivity;
    private Context context;
    List<Product_Model> ProductModelList;
    Product_Model product;
    private ImageView imageitems;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth;

    DocumentReference document;
    public ProductAdapter(Context context, List<Product_Model> productModelList) {
        this.context = context;
        ProductModelList = productModelList;
        this.imageitems = imageitems;
    }

//    public void updateData(int position){
//        Product_Model item = ProductModelList.get(position);
//        Bundle bundle = new Bundle();
//
//        bundle.putString("description" , item.getDescription());
//        bundle.putString("img_url" , item.getImg_url());
//        bundle.putString("name" , item.getName());
//        bundle.putString("price" , String.valueOf(item.getPrice()));
//        bundle.putString("rating" , item.getRating());
//        bundle.putString("type" , item.getType());
//        Intent intent = new Intent( activity, Update_Pro_Activity.class);
//        intent.putExtras(bundle);
//        activity.startActivity(intent);
//
//    }

    public interface OnProductDeleteListener {
        void onProductDelete(Product_Model product);
    }

    private OnProductDeleteListener onProductDeleteListener;

    public void setOnProductDeleteListener(OnProductDeleteListener listener) {
        this.onProductDeleteListener = listener;
    }

    public void setProductList(List<Product_Model> productList) {
        this.ProductModelList = productList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdapter.ViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(com.example.admin_app.R.layout.product_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int adapterPosition = holder.getAdapterPosition();
        Glide.with(context).load(ProductModelList.get(adapterPosition).getImg_url()).into(holder.imgitem);
        holder.nameitem.setText(ProductModelList.get(adapterPosition).getName());
        holder.priceitem.setText("$"+String.valueOf(ProductModelList.get(adapterPosition).getPrice()));


        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String proId = product.getProId();
                firestore.collection("ShowAll").document(ProductModelList.get(position).getProId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    if (product != null || !product.getProId().isEmpty()) {
                                        ProductModelList.remove(ProductModelList.get(adapterPosition).getProId());
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, "Item is null", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(context, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

//        holder.update_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Bundle bundle = new Bundle();
//                Intent intent = new Intent( context, Update_Pro_Activity.class);
//                //intent.putExtras(bundle);
//                context.startActivity(intent);
//
//                //updateData(position);
//            }
//        });

    }





    @Override
    public int getItemCount() {
        return ProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgitem;
        Button delete_item, update_item;
        RecyclerView recyclerView;
        private TextView nameitem, priceitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgitem = itemView.findViewById(com.example.admin_app.R.id.add_image);
            nameitem = itemView.findViewById(com.example.admin_app.R.id.product_name);
            priceitem = itemView.findViewById(com.example.admin_app.R.id.product_price);
            delete_item = itemView.findViewById(R.id.delete_pro);
            update_item = itemView.findViewById(R.id.update_pro);

        }
    }
}
