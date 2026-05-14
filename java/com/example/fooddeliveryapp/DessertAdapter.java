package com.example.fooddeliveryapp;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DessertAdapter extends RecyclerView.Adapter<DessertAdapter.ViewHolder> {
    private List<MenuModel> menuModelList;
    private List<MenuModel> originalMenuModelList;
    private MenuModel selectedMenuItem;
    private Context context;

    public void setData(List<MenuModel> newData) {
        menuModelList.clear();
        menuModelList.addAll(newData);
        originalMenuModelList = new ArrayList<>(newData);
        notifyDataSetChanged();
        Log.d("DessertAdapter", "Data set updated. Item count: " + getItemCount());
    }
    public MenuModel getSelectedMenuItem() {
        return selectedMenuItem;
    }
    public interface AddToCartListener {
        void onAddToCart(MenuModel selectedItem);
    }
    public void addToCart(MenuModel selectedItem) {
        if (addToCartListener != null) {
            addToCartListener.onAddToCart(selectedItem);
        }
    }
    private AppetizerAdapter.AddToCartListener addToCartListener;
    public void setAddToCartListener(AppetizerAdapter.AddToCartListener listener) {
        this.addToCartListener = listener;
    }

    public DessertAdapter(Context context, List<MenuModel> menuModelList) {
        this.context = context;
        this.menuModelList = menuModelList;
        this.originalMenuModelList = new ArrayList<>(menuModelList);
    }
    @NonNull
    @Override
    public DessertAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.desserts, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DessertAdapter.ViewHolder holder, int position){
        MenuModel item = menuModelList.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMenuItem = item;
                notifyDataSetChanged(); // Refresh the view to highlight the selected item
            }
        });
    }


    @Override
    public int getItemCount() {
        return menuModelList.size();
    }

    public void clear() {
        menuModelList.clear();
        notifyDataSetChanged();
    }

    public void filter(String query) {
        menuModelList.clear();

        if (query.isEmpty()) {
            menuModelList.addAll(originalMenuModelList);
        } else {
            query = query.toLowerCase(Locale.getDefault());
            for (MenuModel model : originalMenuModelList) {
                if (model.getFoodItem().toLowerCase(Locale.getDefault()).contains(query)) {
                    menuModelList.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private Button addbtn;
        private TextView priceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            addbtn = itemView.findViewById(R.id.addbtn);
            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call a method to add the item to the cart
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        MenuModel selectedItem = menuModelList.get(position);
                        addToCart(selectedItem);
                    }
                }
            });
            if (textView == null) {
                Log.e("DessertAdapter", "itemName TextView is null");
            }
            imageView = itemView.findViewById(R.id.foodImageView4);
            textView = itemView.findViewById(R.id.foodNameTextView4);
            priceTv = itemView.findViewById(R.id.foodPriceTextView4);
            if (textView == null) {
                Log.e("DrinksAdapter", "itemName TextView is null");
            }
        }
        public void bind(MenuModel item) {
            if (item != null) {
                textView.setText(item.getFoodItem());
                priceTv.setText(String.format(Locale.getDefault(), "$%s", item.getPrice()));

                int imageResourceId = getImageResourceId(context, item.getimage());
                Log.d("DessertAdapter", "Image Resource ID: " + imageResourceId);
                Glide.with(context)
                        .load(imageResourceId)
                        .into(imageView);
            } else {
                Log.e("DessertAdapter", "Item is null in bind method");
            }
        }
        private int getImageResourceId(Context context, String imageName) {
            int resourceId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
            Log.d("DessertAdapter", "Image Resource ID for " + imageName + ": " + resourceId);
            return resourceId;
        }
    }
    }



