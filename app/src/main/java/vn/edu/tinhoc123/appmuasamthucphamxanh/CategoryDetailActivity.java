package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(categoryName);
        }

        RecyclerView rcv = findViewById(R.id.rcvCategoryProducts);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        List<Product> productList = new ArrayList<>();
        loadDataByCategory(productList, categoryName);

        ProductAdapter adapter = new ProductAdapter(productList, product -> {
            if (MainActivity.instance != null) {
                MainActivity.instance.showAddToCartToast();
            }
            Toast.makeText(this, "Đã thêm " + product.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
        });
        rcv.setAdapter(adapter);
    }

    private void loadDataByCategory(List<Product> list, String category) {
        if (category.equals("Rau, củ")) {
            list.add(new Product("Cà rốt", "10.000đ", R.drawable.raucu));
            list.add(new Product("Súp lơ", "15.000đ", R.drawable.raucu));
        } else if (category.equals("Hoa quả")) {
            list.add(new Product("Táo", "20.000đ", R.drawable.hoaqua));
            list.add(new Product("Nho", "30.000đ", R.drawable.hoaqua));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}