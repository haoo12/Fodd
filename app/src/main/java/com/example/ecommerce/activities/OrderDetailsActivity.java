package com.example.ecommerce.activities;

// com.example.ecommerce.activities.OrderDetailsActivity

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerce.R;
import com.example.ecommerce.adapters.OrderItemAdapter;
import com.example.ecommerce.models.OrderItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Retrieve order details from the intent or other sources
        String orderId = "123456";
        Date orderDate = new Date();  // Replace with your actual order date
        ArrayList<OrderItem> orderItems = createDummyOrderItems();

        // Populate TextViews with order details
        TextView orderIdTextView = findViewById(R.id.order_id);
        TextView orderDateTextView = findViewById(R.id.order_date);
        TextView orderTimeTextView = findViewById(R.id.order_time);

        orderIdTextView.setText("Order ID: #" + orderId);

        // Format date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

        orderDateTextView.setText("Order Date: " + dateFormat.format(orderDate));
        orderTimeTextView.setText("Order Time: " + timeFormat.format(orderDate));

        // Populate ListView with order items using ArrayAdapter
        ListView orderItemsListView = findViewById(R.id.order_items_list);
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(this, R.layout.order_item_layout, orderItems);
        orderItemsListView.setAdapter(orderItemAdapter);

        // Handle "Back to Home" button click
        Button backToHomeButton = findViewById(R.id.back_to_home_button);
        backToHomeButton.setOnClickListener(v -> {
            // Navigate back to the main activity or home screen
            finish();
        });
    }

    private ArrayList<OrderItem> createDummyOrderItems() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        // Replace this with your actual data
        orderItems.add(new OrderItem("Croissant", 1, 10.00, "Processing"));

        return orderItems;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
