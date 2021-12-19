package com.geekbrains.backend.db;

import java.util.List;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.CategoriesExample;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;

public class TestDb {

    public static void main(String[] args) {

        DbService dbService = new DbService();
        ProductsMapper productsMapper = dbService.getProductsMapper();
        Products product = productsMapper.selectByPrimaryKey(1L);
        //System.out.println(product);

        Products forCreate = new Products();
        forCreate.setTitle("Coca cola");
        forCreate.setPrice(50);
        forCreate.setCategoryId(1L);
        productsMapper.insert(forCreate);

        ProductsExample filter = new ProductsExample();

        List<Products> products = productsMapper.selectByExample(filter);
        System.out.println(products);

        filter.createCriteria()
                .andCategoryIdEqualTo(2L);

        System.out.println(productsMapper.selectByExample(filter));

        filter.clear();
        filter.createCriteria()
                .andPriceBetween(51, 1000);

        System.out.println(productsMapper.selectByExample(filter));

        product.setPrice(105);
        productsMapper.updateByPrimaryKey(product);

        System.out.println(productsMapper.selectByPrimaryKey(1L));

        // HomeWork 6 **************************************************************************************************
        CategoriesMapper categoriesMapper = dbService.getCategoriesMapper();
        Categories category = categoriesMapper.selectByPrimaryKey(1L);
        System.out.println(category);

        /*String[] catName = {"Clothes", "Shoes", "Toys", "SportGoods"};
        Categories catCreate = new Categories();
        for (int i=0; i<=3; i++) {
            catCreate.setTitle(catName[i]);
            categoriesMapper.insert(catCreate);
        }*/


        CategoriesExample catFilter = new CategoriesExample();
        List<Categories> categories = categoriesMapper.selectByExample(catFilter);
        System.out.println(categories);
    }

}

