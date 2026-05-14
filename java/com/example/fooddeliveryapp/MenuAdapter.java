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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuModel> menuModelList;
    private Context context;
    private MenuModel selectedMenuItem;
    private List<MenuModel> originalMenuModelList;

    public void addItemToCart(MenuModel menuModel) {
        Cart.addToCart(new CartItem(menuModel.getimage(), menuModel.getFoodItem(), menuModel.getPrice()));
    }

    public void setData(List<MenuModel> newData) {
        menuModelList.clear();
        menuModelList.addAll(newData);
        originalMenuModelList = new ArrayList<>(newData);
        notifyDataSetChanged();
        Log.d("MenuAdapter", "Data set updated. Item count: " + getItemCount());
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
    private AddToCartListener addToCartListener;
    public void setAddToCartListener(AddToCartListener listener) {
        this.addToCartListener = listener;
    }
    public MenuAdapter(Context context, List<MenuModel> menuModelList){
        this.context = context;
        this.menuModelList = menuModelList;
        this.originalMenuModelList = new ArrayList<>(menuModelList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maincourse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        private TextView priceTv;
        private Button addbtn;

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
                Log.e("MenuAdapter", "itemName TextView is null");
            }
            imageView = itemView.findViewById(R.id.foodImageView);
            textView = itemView.findViewById(R.id.foodNameTextView);
            priceTv = itemView.findViewById(R.id.foodPriceTextView);
            addbtn = itemView.findViewById(R.id.addbtn);
            if (textView == null) {
                Log.e("MenuAdapter", "itemName TextView is null");
            }
        }

        public void bind(MenuModel item) {
            if (item != null) {
                textView.setText(item.getFoodItem());
                priceTv.setText(String.format(Locale.getDefault(), "$%s", item.getPrice()));

                int imageResourceId = getImageResourceId(context, item.getimage());
                Log.d("MenuAdapter", "Image Resource ID: " + imageResourceId);
                Glide.with(context)
                        .load(imageResourceId)
                        .into(imageView);
            } else {
                Log.e("MenuAdapter", "Item is null in bind method");
            }
        }

        private int getImageResourceId(Context context, String imageName) {
            int resourceId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
            Log.d("MenuAdapter", "Image Resource ID for " + imageName + ": " + resourceId);
            return resourceId;
        }
    }
}



