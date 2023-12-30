package com.example.admin_app.activities_admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_app.R;
import com.example.admin_app.adapters_admin.ProductAdapter;
import com.example.admin_app.models.Product_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AdminPostCart_Activity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView del_pro, update_pro;

    //ImageButton imgupload;
    EditText type, name, description, rating, price, imgupload;

    String Urlimg, Type, Name, Descripttion, Rating;
    int Price;
    Toolbar toolbar;
    Context context;
    Button addProductBtn;
    List<Product_Model> ProductModelList;
    //Product_Model productModel;
    ProductAdapter productAdapter;
    Uri imageuri;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseStorage storage;
    Product_Model productModel;
    RecyclerView recyclerView;
    StorageReference storageReference;
    //@SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_cart);



        firestore = FirebaseFirestore.getInstance();
        firestore.getFirestoreSettings();
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.addpro_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.items_recycler);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        type = findViewById(R.id.type);
        description = findViewById(R.id.description);
        name = findViewById(R.id.name);
        rating = findViewById(R.id.rating);
        price = findViewById(R.id.price);
        addProductBtn = findViewById(R.id.post);
        imgupload = findViewById(R.id.image_upload);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertdata();
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
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            // Tiến hành tải lên hình ảnh
        }
    }



    public void insertdata()
    {
        //p.setProId(UUID.randomUUID().toString().trim());
        productModel = new Product_Model();
        //HashMap<String, Object> items = productModel.convertHashMap();
        productModel.setProId(UUID.randomUUID().toString());
        HashMap<String, Object> items = new HashMap<>();
        //items.put("id", productModel.getProId());
        items.put( "description", description.getText().toString().trim());
        items.put( "img_url", imgupload.getText().toString().trim());
        items.put( "name", name.getText().toString().trim());
        items.put( "price", Integer.parseInt(String.valueOf(price.getText())));
        items.put( "rating", rating.getText().toString().trim());
        items.put( "type", type.getText().toString().trim());

        Urlimg = imgupload.getText().toString();
        Name = name.getText().toString();
        Type = type.getText().toString();
        Descripttion = description.getText().toString();
        Price = Integer.parseInt(String.valueOf(price.getText()));
        Rating = rating.getText().toString();

        firestore.collection("ShowAll").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Lấy ID của tài liệu vừa thêm vào Firestore
                            // Tạo một đối tượng Product_Model mới
                            String documentId = task.getResult().getId();
                            productModel.setProId(documentId.toString());
                            productModel.setDescription(description.getText().toString().trim());
                            productModel.setImg_url(imgupload.getText().toString().trim());
                            productModel.setName(name.getText().toString().trim());
                            productModel.setPrice(Integer.parseInt(String.valueOf(price.getText())));
                            productModel.setRating(rating.getText().toString().trim());
                            productModel.setType(type.getText().toString().trim());
                            updateUI(productModel);

                            firestore.collection("ShowAll").document(productModel.getProId()).get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            // Chuyển dữ liệu từ DocumentSnapshot thành đối tượng Product
                                            Product_Model product = documentSnapshot.toObject(Product_Model.class);
                                            // Lấy tài liệu tương ứng với ID đã cho

                                            // Kiểm tra xem product có giá trị không
                                            if (product != null) {
                                                // Cập nhật giao diện người dùng với dữ liệu từ model
                                                updateUI(product);
                                                //updateFirestoreData(documentId,"", product.getProId());
                                                //updateFirestoreData(product.getProId(documentId.toString()));
                                                updateFirestoreData(String.valueOf(description), "description", Descripttion);
                                                updateFirestoreData(String.valueOf(imgupload), "img_url", Urlimg);
                                                updateFirestoreData(String.valueOf(name), "name", Name);
                                                updateFirestoreData(String.valueOf(price), "price", String.valueOf(Integer.parseInt(String.valueOf(Price))));
                                                updateFirestoreData(String.valueOf(rating), "rating", Rating);
                                                updateFirestoreData(String.valueOf(type), "type", Type);
                                            }

                                        }
                                    });
                            Toast.makeText(AdminPostCart_Activity.this,
                                    "Your Product has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }



    private void updateUI(Product_Model product) {

        product.getProId();
        description.setText(product.getDescription());
        imgupload.setText(product.getImg_url());
        name.setText(product.getName());

        // Chuyển giá trị price thành số nguyên
        int priceInt = Integer.parseInt(String.valueOf(product.getPrice()));
        price.setText(priceInt);

        rating.setText(product.getRating());
        type.setText(product.getType());

    }


    private void updateFirestoreData(String documentId, String field, String value) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put(field, value);

        firestore.collection("ShowAll").document(documentId)
                .update(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminPostCart_Activity.this,
                                "Data updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminPostCart_Activity.this,
                                "Failed to update data", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}