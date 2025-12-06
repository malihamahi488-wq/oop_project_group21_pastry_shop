package com.example.BranchManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Model class AddNewProduct (implements UML)
 *
 * Attributes:
 * - managerID: int
 * - newProductData: Map<String,String>
 *
 * Methods:
 * + openProductForm(): void      (not strictly necessary in this sample)
 * + validateProductData(): boolean
 * + saveProduct(): boolean
 *
 * This implementation uses an in-memory store (productStore) to simulate persistence.
 * saveProduct writes a small text file under temp directory to simulate persistence as well.
 */
public class AddNewProduct {

    private final int managerID;
    private Map<String,String> newProductData = new HashMap<>();
    private String lastValidationMessage = "";

    // mock store: productId -> map of fields
    private static final Map<String, Map<String,String>> productStore = new LinkedHashMap<>();

    public AddNewProduct(int managerID) {
        this.managerID = managerID;
        seedMock(); // optional
    }

    public void openProductForm() {
        // no-op in model layer; controller handles UI
    }

    public void setNewProductData(Map<String, String> data) {
        this.newProductData = new HashMap<>(data);
    }

    public Map<String, String> getNewProductData() {
        return Collections.unmodifiableMap(newProductData);
    }

    /**
     * Validate fields. Returns true if valid, false otherwise.
     * Use getLastValidationMessage() to read an error explanation.
     */
    public boolean validateProductData(Map<String, String> data) {
        if (data == null) {
            lastValidationMessage = "No data provided.";
            return false;
        }
        String name = Optional.ofNullable(data.get("name")).orElse("").trim();
        String cat = Optional.ofNullable(data.get("category")).orElse("").trim();
        String priceText = Optional.ofNullable(data.get("price")).orElse("").trim();
        String stockText = Optional.ofNullable(data.get("stockQty")).orElse("").trim();

        if (name.isEmpty()) {
            lastValidationMessage = "Product name is required.";
            return false;
        }
        if (cat.isEmpty()) {
            lastValidationMessage = "Category is required.";
            return false;
        }
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                lastValidationMessage = "Price cannot be negative.";
                return false;
            }
        } catch (NumberFormatException e) {
            lastValidationMessage = "Invalid price.";
            return false;
        }
        try {
            int s = Integer.parseInt(stockText);
            if (s < 0) {
                lastValidationMessage = "Stock cannot be negative.";
                return false;
            }
        } catch (NumberFormatException e) {
            lastValidationMessage = "Invalid stock quantity.";
            return false;
        }

        // optional: ensure id is unique if provided
        String id = Optional.ofNullable(data.get("productId")).orElse("").trim();
        if (!id.isEmpty() && productStore.containsKey(id)) {
            lastValidationMessage = "Product ID already exists. Use a different ID or leave blank to auto-generate.";
            return false;
        }

        lastValidationMessage = "Validation OK.";
        return true;
    }

    public String getLastValidationMessage() {
        return lastValidationMessage;
    }

    /**
     * Save product into productStore. If productId is blank, auto-generate one.
     * Also writes a small file to temp dir to simulate persistence (optional).
     *
     * Returns true on success.
     */
    public boolean saveProduct() throws IOException {
        if (!validateProductData(newProductData)) {
            return false;
        }
        String id = Optional.ofNullable(newProductData.get("productId")).orElse("").trim();
        if (id.isEmpty()) {
            id = generateProductId();
            newProductData.put("productId", id);
        }

        // store copy
        Map<String,String> copy = new HashMap<>(newProductData);
        productStore.put(id, copy);

        // simulate persistence: write to temp file (can remove later)
        File f = new File(System.getProperty("java.io.tmpdir"), "product_" + id + ".txt");
        try (FileWriter fw = new FileWriter(f)) {
            for (Map.Entry<String,String> e : copy.entrySet()) {
                fw.write(e.getKey() + ":" + e.getValue() + "\n");
            }
        }

        return true;
    }

    private String generateProductId() {
        // simple auto-generation: P + incremental number
        int next = 100;
        while (productStore.containsKey("P" + next)) next++;
        return "P" + next;
    }

    // helper: to let controller show a list of products, we expose read-only view
    public List<Map<String,String>> listAllProducts() {
        List<Map<String,String>> out = new ArrayList<>();
        for (Map<String,String> m : productStore.values()) out.add(Collections.unmodifiableMap(m));
        return out;
    }

    // Seed a couple of products (optional)
    private void seedMock() {
        if (!productStore.isEmpty()) return;
        Map<String,String> p1 = new HashMap<>();
        p1.put("productId","P100");
        p1.put("name","Chocolate Cake");
        p1.put("category","Cakes");
        p1.put("price","25.50");
        p1.put("stockQty","12");
        p1.put("description","Rich chocolate layered cake.");
        productStore.put(p1.get("productId"), p1);

        Map<String,String> p2 = new HashMap<>();
        p2.put("productId","P101");
        p2.put("name","Vanilla Cupcake");
        p2.put("category","Pastries");
        p2.put("price","3.50");
        p2.put("stockQty","60");
        p2.put("description","Classic vanilla cupcake.");
        productStore.put(p2.get("productId"), p2);
    }
}
