package vn.edu.tinhoc123.appmuasamthucphamxanh;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    private static DataHelper instance;
    private List<Product> allProducts = new ArrayList<>();

    public static DataHelper getInstance() {
        if (instance == null) instance = new DataHelper();
        return instance;
    }

    public List<Product> getAllProducts() {
        if (allProducts.isEmpty()) {
            allProducts.add(new Product("Cà rốt", "Rau, củ", "10.000đ", R.drawable.carot));
            allProducts.add(new Product("Súp lơ", "Rau, củ", "15.000đ", R.drawable.suplo));
            allProducts.add(new Product("Cải thìa", "Rau, củ", "7.000đ", R.drawable.caithia));
            allProducts.add(new Product("Nấm", "Rau, củ", "8.000đ", R.drawable.nam));
            allProducts.add(new Product("Rau muống", "Rau, củ", "5.000đ", R.drawable.raumuong));
            allProducts.add(new Product("Chanh", "Rau, củ", "3.000đ", R.drawable.chanh));

            allProducts.add(new Product("Táo", "Hoa quả", "20.000đ", R.drawable.tao));
            allProducts.add(new Product("Nho", "Hoa quả", "30.000đ", R.drawable.nho));
            allProducts.add(new Product("Chuối", "Hoa quả", "20.000đ", R.drawable.chuoi));
            allProducts.add(new Product("Mận", "Hoa quả", "50.000đ", R.drawable.man));
            allProducts.add(new Product("Bơ", "Hoa quả", "25.000đ", R.drawable.bo));
            allProducts.add(new Product("Ổi", "Hoa quả", "18.000đ", R.drawable.oi));


            allProducts.add(new Product("Thịt bò", "Thịt, Cá", "150.000đ", R.drawable.thit));
            allProducts.add(new Product("Cá liệt", "Thịt, Cá", "50.000đ", R.drawable.caliet));
            allProducts.add(new Product("Thịt gà", "Thịt, Cá", "150.000đ", R.drawable.thitga));
            allProducts.add(new Product("Thịt Heo", "Thịt, Cá", "150.000đ", R.drawable.thitheo));
            allProducts.add(new Product("Cá dò", "Thịt, Cá", "150.000đ", R.drawable.cado));



            allProducts.add(new Product("Cá viên chiên", "Đồ ăn", "30.000đ", R.drawable.cavienchien));
            allProducts.add(new Product("Lạp xưởng nướng đá", "Đồ ăn", "15.000đ", R.drawable.lapxuong));
            allProducts.add(new Product("Trà sữa socola", "Đồ ăn", "25.000đ", R.drawable.trasua));
            allProducts.add(new Product("Pizza", "Đồ ăn", "65.000đ", R.drawable.pizza));
            allProducts.add(new Product("Hotdog", "Đồ ăn", "25.000đ", R.drawable.hotdog));

        }
        return allProducts;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getCategory().equals(category)) {
                filtered.add(p);
            }
        }
        return filtered;
    }
}