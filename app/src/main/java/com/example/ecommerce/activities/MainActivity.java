package com.example.ecommerce.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.ecommerce.R;
import com.example.ecommerce.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    FirebaseAuth auth;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Log để kiểm tra xem navController và bottomNavigationView có đúng không
        Log.d("MainActivity", "NavController: " + navController);
        Log.d("MainActivity", "BottomNavigationView: " + bottomNavigationView);

        // Log để kiểm tra xem ID của các menu có phù hợp với đúng ID của các fragment không
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            Log.d("MainActivity", "Menu item ID: " + menu.getItem(i).getItemId());
        }

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment homeFragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_my_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
            return true;
        } else if (id == R.id.menu_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            return true;
        }else if (id == R.id.menu_history) {
            startActivity(new Intent(MainActivity.this, OrderDetailsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}