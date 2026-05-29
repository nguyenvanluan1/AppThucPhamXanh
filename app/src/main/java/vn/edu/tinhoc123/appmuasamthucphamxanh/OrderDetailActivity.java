package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private RecyclerView rcvDetail;
    private ImageView btnBack;
    private TextView txtOrderId, txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        rcvDetail = findViewById(R.id.rcvDetail);
        txtOrderId = findViewById(R.id.txtOrderId);
        txtTotal = findViewById(R.id.txtTotal);

        btnBack.setOnClickListener(v -> finish());

        String orderId = getIntent().getStringExtra("ORDER_ID");

        if (orderId != null && !orderId.isEmpty()) {
            loadOrderDetails(orderId);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin đơn hàng!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadOrderDetails(String orderId) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance("https://appmuasamthucphamxanh-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("Orders")
                .child(userId)
                .orderByChild("orderId")
                .equalTo(orderId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            Toast.makeText(OrderDetailActivity.this, "Không tìm thấy dữ liệu đơn hàng", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String id = ds.child("orderId").getValue(String.class);
                            Double total = ds.child("totalAmount").getValue(Double.class);

                            txtOrderId.setText("Mã đơn: " + (id != null ? id : "N/A"));
                            txtTotal.setText("Tổng tiền: " + String.format("%,dđ", (int)(total != null ? total : 0)));

                            List<Product> productList = new ArrayList<>();
                            DataSnapshot itemsSnapshot = ds.child("items");

                            for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                                Product p = itemSnapshot.getValue(Product.class);
                                if (p != null) productList.add(p);
                            }

                            rcvDetail.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
                            ProductAdapter adapter = new ProductAdapter(productList, null, false, null);
                            rcvDetail.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(OrderDetailActivity.this, "Lỗi kết nối: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}