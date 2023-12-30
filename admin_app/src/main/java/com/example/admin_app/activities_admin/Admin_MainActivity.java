package com.example.admin_app.activities_admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_app.R;
import com.example.admin_app.adapters_admin.ProductAdapter;
import com.example.admin_app.models.Product_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin_MainActivity extends AppCompatActivity {

    Context context;
    private Admin_MainActivity activity;
    ImageButton addItem;

    Product_Model productModel;
    RecyclerView recyclerView;
    List<Product_Model> ProductModelList;
    Product_Model product;
    ProductAdapter productAdapter;
    Fragment homeFragment;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        product = (Product_Model) getIntent().getSerializableExtra("product");

        firestore = FirebaseFirestore.getInstance();
        firestore.getFirestoreSettings();

        //ProId = ProductModelList.set(pr ,documentReference.getId())

        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.all_product_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_loginout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(Admin_MainActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //String ShowAll = getIntent().getStringExtra("ShowAll");

        recyclerView = findViewById(R.id.items_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductModelList = new ArrayList<>();
        productAdapter = new ProductAdapter( this ,ProductModelList);
        recyclerView.setAdapter(productAdapter);
        //deleteItem = findViewById(R.id.delete_pro);
        addItem = findViewById(R.id.fadd);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin_MainActivity.this, AdminPostCart_Activity.class);
                startActivity(intent);
            }
        });

        //ProductAdapter productAdapter = new ProductAdapter(ProductModelList);

        recyclerView.setAdapter(productAdapter);


        firestore.collection("ShowAll")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                firestore.getFirestoreSettings();
                                Product_Model productModel = doc.toObject(Product_Model.class);
                                //String documentId = doc.getId();
                                productModel.setProId(productModel.getProId());
                                ProductModelList.add(productModel);
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }
}