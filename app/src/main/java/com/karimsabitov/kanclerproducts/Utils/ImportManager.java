package com.karimsabitov.kanclerproducts.Utils;

import com.karimsabitov.kanclerproducts.model.Product;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportManager {

    public List<Product> fetchProducts(String fileName) throws IOException {
        List<Product> products = new ArrayList<>();

        File file = new File(fileName);

        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));

        Sheet sheet = book.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String title = row.getCell(0).getStringCellValue();
            int count = (int) row.getCell(1).getNumericCellValue();
            products.add(new Product(title, count, 0, false));
        }

        return products;
    }
}
