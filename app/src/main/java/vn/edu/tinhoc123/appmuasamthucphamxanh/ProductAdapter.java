package vn.edu.tinhoc123.appmuasamthucphamxanh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private CartListener cartListener;
    private boolean isCartScreen;
    private OnCartChangedListener cartChangedListener;

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    public ProductAdapter(List<Product> productList, CartListener cartListener, boolean isCartScreen, OnCartChangedListener listener) {
        this.productList = productList;
        this.cartListener = cartListener;
        this.isCartScreen = isCartScreen;
        this.cartChangedListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getPrice());
        holder.imgProduct.setImageResource(product.getImageRes());

        if (product.getQuantity() > 1) {
            holder.txtQuantity.setText("x" + product.getQuantity());
            holder.txtQuantity.setVisibility(View.VISIBLE);
        } else {
            holder.txtQuantity.setVisibility(View.GONE);
        }

        if (isCartScreen) {
            holder.btnAddToCart.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);

            holder.btnDelete.setOnClickListener(v -> {
                CartManager.getInstance().removeProduct(product);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());

                if (cartChangedListener != null) {
                    cartChangedListener.onCartChanged();
                }
            });
        } else {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnAddToCart.setVisibility(View.VISIBLE);
            holder.btnAddToCart.setOnClickListener(v -> {
                if (cartListener != null) cartListener.onAddToCart(product);
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnAddToCart, btnDelete;
        TextView txtName, txtPrice, txtQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}