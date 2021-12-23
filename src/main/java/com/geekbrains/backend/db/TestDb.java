package com.geekbrains.backend.db;

import java.util.Collections;
import java.util.List;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.CategoriesExample;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;
import org.apache.ibatis.javassist.runtime.Desc;

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
        //Закомментирован так как создается один раз
        //productsMapper.insert(forCreate);


        ProductsExample filter = new ProductsExample();

        List<Products> products = productsMapper.selectByExample(filter);
        //System.out.println(products);

        filter.createCriteria()
                .andCategoryIdEqualTo(2L);

        //System.out.println(productsMapper.selectByExample(filter));

        filter.clear();
        filter.createCriteria()
                .andPriceBetween(51, 1000);

        //System.out.println(productsMapper.selectByExample(filter));

        product.setPrice(105);
        productsMapper.updateByPrimaryKey(product);

        //System.out.println(productsMapper.selectByPrimaryKey(1L));

        // HomeWork 6 **************************************************************************************************

        // 1 Задание. Создать 4 категории
        // Закомментировал так как в базе уже создал эти 4 категории
        String[] catName = {"Clothes", "Shoes", "Toys", "SportGoods"};
        Categories catCreate = new Categories();
        for (int i=0; i<=3; i++) {
            catCreate.setTitle(catName[i]);
            //categoriesMapper.insert(catCreate);
        }

        // 2 Задание. Cоздать по 3 продукта в каждой категории
        // Закомментировал так как в базе уже создал продукты
        String[] clothesItems = {"Pants", "T-shirt", "Dress"};
        int [] priceClothesItems = {2000, 1500, 5000};

        String[] shoesItems = {"Boots", "Crocs", "Flip-flop"};
        int [] priceShoesItems = {3000, 1200, 1000};

        String[] toysItems = {"Doll", "Water-Gun", "Lego"};
        int [] priceToysItems = {800, 500, 3500};

        String[] sportItems = {"Ball", "Bike", "Boat"};
        int [] priceSportItems = {1800, 15000, 24000};

        for (int i=0; i<3; i++) {
            forCreate.setTitle(clothesItems[i]);
            forCreate.setPrice(priceClothesItems[i]);
            forCreate.setCategoryId(3L);
            //productsMapper.insert(forCreate);
        }
        for (int i=0; i<3; i++) {
            forCreate.setTitle(shoesItems[i]);
            forCreate.setPrice(priceShoesItems[i]);
            forCreate.setCategoryId(4L);
            //productsMapper.insert(forCreate);
            }
        for (int i=0; i<3; i++) {
            forCreate.setTitle(toysItems[i]);
            forCreate.setPrice(priceToysItems[i]);
            forCreate.setCategoryId(5L);
            //productsMapper.insert(forCreate);
                }
        for (int i=0; i<3; i++) {
            forCreate.setTitle(sportItems[i]);
            forCreate.setPrice(priceSportItems[i]);
            forCreate.setCategoryId(6L);
            //productsMapper.insert(forCreate);
        }

        // 3 Задание.Вывести продукты в каждой из категорий

                for (long i =1L; i <=6L; i++) {
                    filter.clear();
                    filter.createCriteria()
                    .andCategoryIdEqualTo(i);
                    System.out.println("Продукты из категории " + i + " :");
                    System.out.println(productsMapper.selectByExample(filter));
                    System.out.println("______________________________________________________");

                }

        // 4 Задание.Вывести топ 3 самых дорогих продукта

        Collections.sort(products);
       for (int i=0; i<3; i++){
            System.out.println("Cамый дорогой продукт № " + (i+1) + ":");
            System.out.println(products.get(i));
        }

             // Делаем коммит в базу и закрываем сессию
        dbService.commitSession();
        dbService.closeSession();
    }

}

