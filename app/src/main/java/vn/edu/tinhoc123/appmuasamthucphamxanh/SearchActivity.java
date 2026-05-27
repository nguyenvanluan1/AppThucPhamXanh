package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements CartListener {
    private SearchView searchView;
    private RecyclerView rcvSearchResult;
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayList = new ArrayList<>();
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        initViews();
        loadAllProducts();
        setupRecyclerView();
        setupSearchView();
    }

    private void initViews() {
        searchView = findViewById(R.id.searchView);
        rcvSearchResult = findViewById(R.id.rcvSearchResult);
    }

    private void loadAllProducts() {
        allProducts = DataHelper.getInstance().getAllProducts();
        displayList.addAll(allProducts);
    }

    private void setupRecyclerView() {
        rcvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(displayList, this, false, null);
        rcvSearchResult.setAdapter(adapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        displayList.clear();
        if (text.isEmpty()) {
            displayList.addAll(allProducts);
        } else {
            String lowerCaseText = text.toLowerCase();
            for (Product p : allProducts) {
                if (p.getName().toLowerCase().contains(lowerCaseText)) {
                    displayList.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToCart(Product p) {
        CartManager.getInstance().addProduct(p);
        Toast.makeText(this, "Đã thêm " + p.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
    }
}