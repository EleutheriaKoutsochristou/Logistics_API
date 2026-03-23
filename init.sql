-- ============================================
-- Logistics API - PostgreSQL Database Dump
-- ============================================
-- This file can be imported into any PostgreSQL database
-- Database Name: myappdb
--
-- IMPORT INSTRUCTIONS:
-- For Linux/Mac:
--   psql -U myappuser -d myappdb -f init.sql
--
-- For Windows:
--   psql -U myappuser -d myappdb -f init.sql
--
-- Or using pgAdmin:
--   1. Create database 'myappdb'
--   2. Tools > Query Tool
--   3. Open and run this file

-- ============================================
-- Drop existing tables if they exist
-- ============================================
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;

-- ============================================
-- Create Orders Table
-- ============================================
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(128) NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    destination VARCHAR(128) NOT NULL,
    shipping_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    cost DOUBLE PRECISION NOT NULL
);
--
-- Create indexes for better query performance
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_destination ON orders(destination);
CREATE INDEX idx_orders_shipping_type ON orders(shipping_type);

-- ============================================
-- Create Vehicles Table
-- ============================================
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    license_plate VARCHAR(255) NOT NULL UNIQUE,
    capacity DOUBLE PRECISION NOT NULL,
    max_speed DOUBLE PRECISION NOT NULL,
    status VARCHAR(255) NOT NULL
);

-- Create indexes for better query performance
CREATE INDEX idx_vehicles_type ON vehicles(type);
CREATE INDEX idx_vehicles_status ON vehicles(status);
CREATE INDEX idx_vehicles_license_plate ON vehicles(license_plate);

-- ============================================
-- Insert Actual Orders Data
-- ============================================
-- INSERT INTO orders (id, cost, customer_name, destination, shipping_type, status, weight)
-- VALUES
--     (18, 25, 'C1', 'Italy', 'STANDARD', 'PENDING', 10),
--     (21, 25, 'C1', 'Italy', 'STANDARD', 'PENDING', 10),
--     (22, 0, 'C1', 'Italy', 'STANDARD', 'PENDING', 10),
--     (20, 7.5, 'C3', 'Italy', 'STANDARD', 'READY', 3),
--     (19, 17.5, 'C2', 'Italy', 'STANDARD', 'DELAYED', 7),
--     (24, 11.25, 'C5', 'Africa', 'EXPRESS', 'READY', 3),
--     (23, 7.5, 'C5', 'France', 'STANDARD', 'DELAYED', 3);

-- ============================================
-- Insert Actual Vehicles Data
-- ============================================
INSERT INTO vehicles (id, capacity, license_plate, max_speed, status, type)
VALUES
    (1, 1000, 'ABC123', 120, 'available', 'TRUCK'),
    (2, 500, 'XYZ789', 100, 'in_service', 'VAN'),
    (3, 200, 'LMN456', 80, 'available', 'DRONE');

-- ============================================
-- Data Summary
-- ============================================
-- Total orders: 7
-- Total vehicles: 3
--
-- Orders by status:
--   PENDING: 3 records
--   READY: 2 records
--   DELAYED: 2 records
--
-- Orders by shipping type:
--   STANDARD: 6 records
--   EXPRESS: 1 record
--
-- Vehicles by type:
--   TRUCK: 1 record
--   VAN: 1 record
--   DRONE: 1 record
--
-- Vehicles by status:
--   available: 2 records
--   in_service: 1 record
