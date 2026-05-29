package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class TopProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private String categoryFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_product);

        categoryFilter = getIntent().getStringExtra("CATEGORY_KEY");

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList, null, false, null);
        recyclerView.setAdapter(adapter);

        loadTopProductsFromFirebase();
    }

    private void loadTopProductsFromFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://appmuasamthucphamxanh-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Orders");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot orderSnapshot : userSnapshot.getChildren()) {
                        DataSnapshot itemsSnapshot = orderSnapshot.child("items");

                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                            Product p = itemSnapshot.getValue(Product.class);
                            if (p != null && p.getName() != null) {
                                if (categoryFilter == null || categoryFilter.equals(p.getCategory())) {
                                    productList.add(p);
                                }
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("DEBUG_LOG", "Đã load xong " + productList.size() + " sản phẩm cho danh mục: " + categoryFilter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DEBUG_LOG", "Lỗi Firebase: " + error.getMessage());
            }
        });
    }
}