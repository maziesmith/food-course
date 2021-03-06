package com.example.foodshop.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodshop.R;
import com.example.foodshop.StoreActivity;
import com.example.foodshop.cart.CartObj;
import com.example.foodshop.model.Product;

import java.util.List;

public class ProductListViewAdapter extends BaseAdapter {
    List<Product> products;
    CartObj cart;

    public ProductListViewAdapter(List<Product> products, CartObj cart) {
        this.products = products;
        this.cart = cart;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_food_style, viewGroup,false);
        }
        final Product product = products.get(i);
        TextView foodName = view.findViewById(R.id.foodName);
        TextView foodPrice = view.findViewById(R.id.foodPrice);
        ImageView foodImage = view.findViewById(R.id.foodImage);
        foodName.setText(product.getName());
        foodPrice.setText(""+product.getPrice());
        Glide.with(view).load(product.getImageLink()).into(foodImage);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        final Button btnMinus = view.findViewById(R.id.btnMinus);
        final TextView txtQuantty = view.findViewById(R.id.txtQuantity);
        btnMinus.setEnabled(false);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMinus.setEnabled(true);
                cart.addItemToCart(product);
                txtQuantty.setText(cart.getItems().get(product.getIdProduct()).getQuantity()+"");
                double price = cart.getItems().get(product.getIdProduct()).getUnitPrice();
                StoreActivity storeActivity = (StoreActivity) view.getContext();
                storeActivity.takeTotalPrice(price);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double price = cart.getItems().get(product.getIdProduct()).getUnitPrice();
                StoreActivity storeActivity = (StoreActivity) view.getContext();
                storeActivity.removePrice(price);
                if(cart.getItems().get(product.getIdProduct()).getQuantity() == 1) {
                    cart.removeItemFromCart(product);
                    txtQuantty.setText("0");
                    btnMinus.setEnabled(false);
                }else if(cart.getItems().get(product.getIdProduct()).getQuantity() > 1) {
                    cart.removeItemFromCart(product);
                    txtQuantty.setText(cart.getItems().get(product.getIdProduct()).getQuantity() + "");
                }
            }
        });
        return view;
    }
}
