package com.abc.springRestCRUD.service;

import com.abc.springRestCRUD.entity.Category;
import com.abc.springRestCRUD.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(Long id){
        return categoryRepository.findById(id);
    }

    public Category create(Category c){
        return categoryRepository.save(c);
    }

    public Category update(Long id, Category updatedCategory){
        Category existing = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with ID:" + id));
        BeanUtils.copyProperties(updatedCategory, existing);
        return categoryRepository.save(existing);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

}
