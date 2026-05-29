package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private Toolbar toolbar;
    private ImageView btnMenu;
    private ImageView btnSearch;
    private FrameLayout btnCart;
    private TextView txtCartBadge;
    private RecyclerView rcvVeges, rcvFruits, rcvMeats, rcvFoods;
    private CardView cardLayoutToast;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.google.firebase.FirebaseApp.initializeApp(this);
        instance = this;
        setContentView(R.layout.activity_main);
        initViews();
        setupRecyclerViews();
        setupBottomNavigation();
        setupHeaderClicks();
        setupCategoryClicks();
        setupMostPurchasedClicks();
        updateVoucherUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
        updateVoucherUI();
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

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_offers) {
                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, VoucherActivity.class));
                } else {
                    Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                return true;
            }
            if (id == R.id.nav_orders) {
                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
                } else {
                    Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                return true;
            }
            if (id == R.id.nav_profile) {
                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else {
                    Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                return true;
            }
            return false;
        });
    }
    private void setupMostPurchasedClicks() {
        findViewById(R.id.btnRauCuNhieuNhat).setOnClickListener(v -> handleMostPurchased("Rau, củ"));
        findViewById(R.id.btnHoaQuaNhieuNhat).setOnClickListener(v -> handleMostPurchased("Hoa quả"));
        findViewById(R.id.btnThitNhieuNhat).setOnClickListener(v -> handleMostPurchased("Thịt, Cá"));
        findViewById(R.id.btnDoAnNhieuNhat).setOnClickListener(v -> handleMostPurchased("Đồ ăn"));
    }

    private void handleMostPurchased(String category) {
        Intent intent = new Intent(this, TopProductActivity.class);
        intent.putExtra("CATEGORY_KEY", category);
        startActivity(intent);
    }

    private void updateCartBadge() {
        int count = CartManager.getInstance().getCartItems().size();
        if (txtCartBadge != null) {
            txtCartBadge.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
            if (count > 0) txtCartBadge.setText(String.valueOf(count));
        }
    }

    private void setupHeaderClicks() {
        btnSearch.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
        btnCart.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void setupCategoryClicks() {
        findViewById(R.id.layoutRauCu).setOnClickListener(v -> openCategoryDetail("Rau, củ"));
        findViewById(R.id.layoutHoaQua).setOnClickListener(v -> openCategoryDetail("Hoa quả"));
        findViewById(R.id.layoutThit).setOnClickListener(v -> openCategoryDetail("Thịt, Cá"));
        findViewById(R.id.layoutDoAn).setOnClickListener(v -> openCategoryDetail("Đồ ăn"));
    }

    private void openCategoryDetail(String categoryName) {
        Intent intent = new Intent(MainActivity.this, CategoryDetailActivity.class);
        intent.putExtra("CATEGORY_NAME", categoryName);
        startActivity(intent);
    }

    private void setupRecyclerViews() {
        rcvVeges.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvFruits.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvMeats.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvFoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void updateVoucherUI() {
        View voucherItem = bottomNavigation.findViewById(R.id.nav_offers);
        if (voucherItem != null) {
            boolean isLogged = FirebaseAuth.getInstance().getCurrentUser() != null;
            voucherItem.setAlpha(isLogged ? 1.0f : 0.3f);
            voucherItem.setEnabled(isLogged);
        }
    }

    public void showAddToCartToast() {
        updateCartBadge();
        if (cardLayoutToast != null) {
            cardLayoutToast.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> cardLayoutToast.setVisibility(View.GONE), 2000);
        }
    }
}