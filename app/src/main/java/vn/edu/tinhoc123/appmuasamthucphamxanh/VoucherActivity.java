package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class VoucherActivity extends AppCompatActivity {
    private RecyclerView rcvVoucher;
    private VoucherAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        // Khởi tạo Toolbar
        toolbar = findViewById(R.id.toolbarVoucher);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ưu đãi của tôi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        rcvVoucher = findViewById(R.id.rcvVoucher);
        rcvVoucher.setLayoutManager(new LinearLayoutManager(this));

        List<String> voucherList = new ArrayList<>();
        voucherList.add("Giảm 10.000đ cho đơn hàng rau củ");
        voucherList.add("Giảm 20.000đ cho đơn hàng thịt cá");
        voucherList.add("Giảm 10% cho đơn hàng từ 500k với bất kì gian hàng");

        adapter = new VoucherAdapter(voucherList);
        rcvVoucher.setAdapter(adapter);
    }
}