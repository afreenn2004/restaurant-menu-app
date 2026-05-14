package com.example.fooddeliveryapp;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    public CartAdapter(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position){
        CartItem cartItem = cartItems.get(holder.getAdapterPosition());
        holder.bind(cartItem, holder.itemView.getContext());

        holder.itemView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItem.increaseQuantity();
                notifyItemChanged(holder.getAdapterPosition());
                Log.d("CartAdapter", "Item increased at position: " + holder.getAdapterPosition());
            }
        });

        holder.itemView.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItem.decreaseQuantity();
                notifyItemChanged(holder.getAdapterPosition());
                Log.d("CartAdapter", "Item decreased at position: " + holder.getAdapterPosition());
            }
        });
        holder.itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                cartItems.remove(cartItem);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(holder.getAdapterPosition(), cartItems.size());
            }
        });
    }
    @Override
    public int getItemCount(){
        return cartItems.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView quantityTextView;

        public CartViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nametextview);
            priceTextView = itemView.findViewById(R.id.priceTv);
            quantityTextView = itemView.findViewById(R.id.number);
        }

        public void bind(CartItem cartItem,  Context context){
            imageView.setImageResource(cartItem.getImageResource(context));
            nameTextView.setText(cartItem.getName());
            priceTextView.setText("$" + cartItem.getPrice());
            quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        }
    }
}
