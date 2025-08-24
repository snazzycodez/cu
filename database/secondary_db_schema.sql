-- Secondary Database Schema (secondary_db)
CREATE DATABASE IF NOT EXISTS secondary_db;

USE secondary_db;

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    category VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 인덱스 생성
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_name ON products(name);

-- 샘플 데이터 삽입
INSERT INTO products (name, description, price, stock, category) VALUES
('Laptop', 'High-performance laptop', 999.99, 10, 'Electronics'),
('Smartphone', 'Latest smartphone model', 699.99, 15, 'Electronics'),
('Coffee Maker', 'Automatic coffee machine', 89.99, 20, 'Home'),
('Running Shoes', 'Comfortable running shoes', 129.99, 30, 'Sports'),
('Book Shelf', 'Wooden book shelf', 199.99, 5, 'Furniture');