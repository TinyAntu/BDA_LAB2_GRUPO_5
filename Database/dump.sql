
SET client_encoding TO 'UTF8';

-- Insertar direcciones
INSERT INTO direccion (geom, formatted_address) VALUES
(ST_SetSRID(ST_MakePoint(-70.57577, -33.61169), 4326), 'Puente Alto'), -- Puente Alto
(ST_SetSRID(ST_MakePoint(-70.3954, -23.65236), 4326), 'Antofagasta'), -- Antofagasta
(ST_SetSRID(ST_MakePoint(-71.55183, -33.02457), 4326), 'Viña del Mar'), -- Viña del Mar
(ST_SetSRID(ST_MakePoint(-71.62963, -33.036), 4326), 'Valparaíso'), -- Valparaíso
(ST_SetSRID(ST_MakePoint(-73.11684, -36.72494), 4326), 'Talcahuano'), -- Talcahuano
(ST_SetSRID(ST_MakePoint(-70.6996, -33.59217), 4326), 'San Bernardo'), -- San Bernardo
(ST_SetSRID(ST_MakePoint(-72.94237, -41.4693), 4326), 'Puerto Montt'), -- Puerto Montt
(ST_SetSRID(ST_MakePoint(-72.59842, -38.73965), 4326), 'Temuco'), -- Temuco
(ST_SetSRID(ST_MakePoint(-73.04977, -36.82699), 4326), 'Concepción'), -- Concepción
(ST_SetSRID(ST_MakePoint(-70.29792, -18.4746), 4326), 'Arica'), -- Arica
(ST_SetSRID(ST_MakePoint(-70.74444, -34.17083), 4326), 'Rancagua'), -- Rancagua
(ST_SetSRID(ST_MakePoint(-70.63419, -33.58331), 4326), 'La Pintana'), -- La Pintana
(ST_SetSRID(ST_MakePoint(-71.65542, -35.4264), 4326), 'Talca'), -- Talca
(ST_SetSRID(ST_MakePoint(-70.15027, -20.21326), 4326), 'Iquique'), -- Iquique
(ST_SetSRID(ST_MakePoint(-68.92371, -22.45667), 4326), 'Calama'), -- Calama
(ST_SetSRID(ST_MakePoint(-71.33947, -29.95332), 4326), 'Coquimbo'), -- Coquimbo
(ST_SetSRID(ST_MakePoint(-71.24894, -29.90453), 4326), 'La Serena'), -- La Serena
(ST_SetSRID(ST_MakePoint(-72.10344, -36.60664), 4326), 'Chillán'), -- Chillán
(ST_SetSRID(ST_MakePoint(-71.3729, -33.04823), 4326), 'Villa Alemana') -- Villa Alemana
(ST_SetSRID(ST_MakePoint(-70.6065351, -33.41761942828365), 4326), 'Av. Andrés Bello 2425, 7510689 Providencia, Región Metropolitana'), -- Costanera Center
(ST_SetSRID(ST_MakePoint(-70.68011784232786, -33.45027786847647), 4326), "Av. Alameda Libertador Bernardo O'Higgins, 9170022 Estación Central, Región Metropolitana"); -- USACH

-- Insertar categorías
INSERT INTO categoria (nombre) VALUES 
('Electrónica'),
('Ropa'),
('Hogar'),
('Deportes'),
('Juguetes');

-- Insertar productos
INSERT INTO producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES
('Televisor 4K', 'Televisor de 55 pulgadas con resolución 4K', 499.99, 50, 'disponible', 1),
('Laptop Gaming', 'Laptop con procesador i7 y 16GB de RAM', 1299.99, 20, 'disponible', 1),
('Camisa de Algodón', 'Camisa cómoda y ligera', 25.50, 100, 'disponible', 2),
('Pantalón Jeans', 'Jeans de alta calidad', 45.99, 75, 'disponible', 2),
('Sofá de 3 Plazas', 'Sofá cómodo para sala de estar', 650.00, 10, 'disponible', 3),
('Mesa de Comedor', 'Mesa de comedor de madera', 299.99, 15, 'disponible', 3),
('Bicicleta de Montaña', 'Bicicleta resistente para montaña', 350.00, 30, 'disponible', 4),
('Pelota de Fútbol', 'Pelota oficial de fútbol', 20.00, 200, 'disponible', 4),
('Muñeca de Juguete', 'Muñeca para niños', 15.00, 150, 'disponible', 5),
('Tren de Juguete', 'Tren eléctrico para niños', 80.00, 50, 'disponible', 5),
('Auriculares Inalámbricos', 'Auriculares con cancelación de ruido', 150.00, 40, 'disponible', 1),
('Impresora 3D', 'Impresora 3D para proyectos creativos', 400.00, 5, 'disponible', 1),
('Silla Ergonomica', 'Silla cómoda para oficina', 200.00, 25, 'disponible', 3),
('Reloj Deportivo', 'Reloj con funciones de fitness', 120.00, 60, 'disponible', 4),
('Cuna para Bebé', 'Cuna ajustable para bebés', 220.00, 12, 'disponible', 3),
('Juguete Educativo', 'Juguete que ayuda al desarrollo', 30.00, 80, 'disponible', 5),
('Monitor Curvo', 'Monitor de 32 pulgadas con curvatura', 299.99, 0, 'agotado', 1);

-- Insertar clientes
INSERT INTO cliente (nombre, id_direccion, email, telefono, password) VALUES 
('Juan Perez', 1, 'juan.perez@example.com', '123456789', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('María Gemez', 2, 'maria.gomez@example.com', '987654321', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Luis Rodriguez', 3, 'luis.rodriguez@example.com', '654321789', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Ana Martinez', 4, 'ana.martinez@example.com', '456789123', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Carlos Lopez', 5, 'carlos.lopez@example.com', '321654987', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Patricia Torres', 6, 'patricia.torres@example.com', '789123456', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Fernando Jimenez', 7, 'fernando.jimenez@example.com', '159753486', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Laura Fernandez', 8, 'laura.fernandez@example.com', '789654123', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Javier Sanchez', 9, 'javier.sanchez@example.com', '852147963', '$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Sandra Moreno', 10, 'sandra.moreno@example.com', '951753486','$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi'),
('Admin', 21, 'admin@master.com', '999999999','$2a$10$rnkQhE53UVWbrBukJj3qs.OglvQA8rJ.qaoFzaf2Ru6.0WQEm9Hpi');

-- Insertar ordenes
INSERT INTO orden (estado, id_cliente, total) VALUES 
('enviada', 1, 875.48),
('enviada', 2, 1745.97),
('enviada', 3, 1200.00),
('enviada', 4, 439.99),
('enviada', 5, 295.00),
('enviada', 1, 2314.97),
('enviada', 1, 4405.46);

-- Insertar detalles de orden
INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 499.99),
(1, 3, 2, 25.50),
(2, 2, 1, 1299.99),
(2, 4, 1, 45.99),
(3, 5, 1, 650.00),
(3, 7, 1, 350.00),
(4, 6, 1, 299.99),
(4, 8, 1, 20.00),
(5, 9, 1, 15.00),
(5, 10, 1, 80.00),
(1, 11, 1, 150.00),
(2, 12, 1, 400.00),
(3, 13, 1, 200.00),
(4, 14, 1, 120.00),
(5, 15, 1, 220.00),
(6, 2, 1, 1399.99),
(6, 6, 1, 299.99),
(6, 9, 1, 15.00),
(6, 1, 1, 599.99),
(7, 12, 1, 400.00),
(7, 1, 1, 599.99),
(7, 2, 1, 1399.99),
(7, 3, 1, 25.50),
(7, 6, 1, 299.99),
(7, 9, 1, 15.00),
(7, 10, 1, 80.00),
(7, 8, 1, 22.00),
(7, 7, 1, 360.00),
(7, 5, 1, 600.00),
(7, 4, 1, 42.99),
(7, 11, 1, 200.00),
(7, 13, 1, 210.00),
(7, 14, 1, 125.00),
(7, 16, 1, 25.00);

INSERT INTO almacen (nombre, id_direccion, capacidad, estado) VALUES
('Almacén Arica', 11, 5000, 'activo'),
('Almacén Rancagua', 12, 4000, 'activo'),
('Almacén La Pintana', 13, 3500, 'activo'),
('Almacén Talca', 14, 3000, 'activo'),
('Almacén Iquique', 15, 2500, 'activo'),
('Almacén Calama', 16, 2000, 'activo'),
('Almacén Coquimbo', 17, 1500, 'activo'),
('Almacén La Serena', 18, 1000, 'inactivo'),
('Almacén Chillán', 19, 800, 'inactivo'),
('Almacén Villa Alemana', 20, 1200, 'activo'),
('Almacen Principal', 21, 10000, 'activo');