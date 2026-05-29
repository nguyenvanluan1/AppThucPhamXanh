package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) { this.orderList = orderList; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtOrderId.setText("Mã đơn: " + order.getOrderId());
        holder.txtStatus.setText("Trạng thái: " + order.getStatus());
        holder.txtTotal.setText(String.format("%,dđ", (int)order.getTotalAmount()));

        holder.itemView.setOnClickListener(v -> {
            String orderId = order.getOrderId();
            if (orderId != null) {
                android.content.Intent intent = new android.content.Intent(v.getContext(), OrderDetailActivity.class);
                intent.putExtra("ORDER_ID", orderId);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return orderList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtStatus, txtTotal;
        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }
    }
}