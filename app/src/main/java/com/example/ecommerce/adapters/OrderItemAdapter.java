package com.example.ecommerce.adapters;

// com.example.ecommerce.adapters.OrderItemAdapter

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecommerce.R;
import com.example.ecommerce.models.OrderItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> {

    private Context context;
    private int resource;

    public OrderItemAdapter(@NonNull Context context, int resource, @NonNull List<OrderItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        OrderItem orderItem = getItem(position);

        if (orderItem != null) {
            TextView productNameTextView = view.findViewById(R.id.product_name);
            TextView quantityTextView = view.findViewById(R.id.quantity);
            TextView totalAmountTextView = view.findViewById(R.id.total_amount);
            TextView orderStatusTextView = view.findViewById(R.id.order_status);

            productNameTextView.setText(orderItem.getProductName());
            quantityTextView.setText("Quantity: " + orderItem.getQuantity());
            totalAmountTextView.setText("Total Amount: " + formatCurrency(orderItem.getTotalAmount()));
            orderStatusTextView.setText("Order Status: " + orderItem.getOrderStatus());
        }

        return view;
    }

    private String formatCurrency(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormat.format(amount);
    }
}
