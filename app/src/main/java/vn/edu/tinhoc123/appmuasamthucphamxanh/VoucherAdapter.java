package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {

    private List<String> voucherList;

    public VoucherAdapter(List<String> voucherList) {
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String voucherName = voucherList.get(position);
        holder.txtVoucherName.setText(voucherName);
        holder.btnUse.setEnabled(true);
        holder.btnUse.setAlpha(1.0f);
        holder.btnUse.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(v.getContext(), CartActivity.class);
            intent.putExtra("VOUCHER_NAME", voucherName);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtVoucherName;
        Button btnUse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVoucherName = itemView.findViewById(R.id.txtVoucherName);
            btnUse = itemView.findViewById(R.id.btnUseVoucher);
        }
    }
}