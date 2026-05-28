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

    public static List<Product> mostPurchasedList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (mostPurchasedList != null) {
                getSupportActionBar().setTitle("Mua nhiều nhất");
            } else {
                getSupportActionBar().setTitle(categoryName);
            }
        }

        RecyclerView rcv = findViewById(R.id.rcvCategoryProducts);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        List<Product> productList = new ArrayList<>();
        if (mostPurchasedList != null) {
            productList.addAll(mostPurchasedList);
            mostPurchasedList = null;
        } else if (categoryName != null) {
            loadDataByCategory(productList, categoryName);
        } else {
            productList.addAll(DataHelper.getInstance().getAllProducts());
        }

        ProductAdapter adapter = new ProductAdapter(
                productList,
                product -> {
                    CartManager.getInstance().addProduct(product);
                    Toast.makeText(this, "Đã thêm " + product.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
                },
                false,
                null
        );
        rcv.setAdapter(adapter);
    }

    private void loadDataByCategory(List<Product> list, String category) {
        List<Product> all = DataHelper.getInstance().getAllProducts();
        for (Product p : all) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                list.add(p);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}