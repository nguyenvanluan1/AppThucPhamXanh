package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView; // Import thêm ImageView
import android.widget.TextView;
import android.widget.Toast; // Dùng nếu muốn test nhanh sự kiện click
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView btnMenu;
    private ImageView btnSearch;
    private FrameLayout btnCart;
    private TextView txtCartBadge;
    private RecyclerView rcvVeges;
    private RecyclerView rcvFruits;
    private RecyclerView rcvMeats;
    private RecyclerView rcvFoods;
    private CardView cardLayoutToast;
    private BottomNavigationView bottomNavigation;

    private int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerViews();
        setupBottomNavigation();
        setupHeaderClicks();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        btnMenu = findViewById(R.id.btnMenu);
        btnSearch = findViewById(R.id.btnSearch);
        btnCart = findViewById(R.id.btnCart);
        txtCartBadge = findViewById(R.id.txtCartBadge);
        rcvVeges = findViewById(R.id.rcvVeges);
        rcvFruits = findViewById(R.id.rcvFruits);
        rcvMeats = findViewById(R.id.rcvMeats);
        rcvFoods = findViewById(R.id.rcvFoods);
        cardLayoutToast = findViewById(R.id.cardLayoutToast);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupHeaderClicks() {
        btnMenu.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Menu tùy chọn", Toast.LENGTH_SHORT).show();
        });

        btnSearch.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "tìm kiếm sản phẩm", Toast.LENGTH_SHORT).show();
        });

        btnCart.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Bạn vừa bấm giỏ hàng", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerViews() {
        LinearLayoutManager layoutManagerVeges = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvVeges.setLayoutManager(layoutManagerVeges);

        LinearLayoutManager layoutManagerFruits = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvFruits.setLayoutManager(layoutManagerFruits);

        LinearLayoutManager layoutManagerMeats = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvMeats.setLayoutManager(layoutManagerMeats);

        LinearLayoutManager layoutManagerFoods = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvFoods.setLayoutManager(layoutManagerFoods);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            return true;
        });
    }

    public void showAddToCartToast() {
        cartCount++;
        if (txtCartBadge != null) {
            txtCartBadge.setText(String.valueOf(cartCount));
            if (txtCartBadge.getVisibility() == View.GONE) {
                txtCartBadge.setVisibility(View.VISIBLE);
            }
        }

        if (cardLayoutToast != null) {
            cardLayoutToast.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                cardLayoutToast.setVisibility(View.GONE);
            }, 2000);
        }
    }
}