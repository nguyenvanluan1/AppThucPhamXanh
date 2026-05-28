package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        Order order = (Order) getIntent().getSerializableExtra("ORDER_DATA");

        if (order != null) {
            txtOrderId.setText("Mã đơn: " + order.getOrderId());
            txtTotal.setText("Tổng tiền: " + String.format("%,dđ", (int) order.getTotalAmount()));

            rcvDetail.setLayoutManager(new LinearLayoutManager(this));
            ProductAdapter adapter = new ProductAdapter(
                    order.getItems(),
                    null,
                    false,
                    null
            );
            rcvDetail.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin đơn hàng!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}