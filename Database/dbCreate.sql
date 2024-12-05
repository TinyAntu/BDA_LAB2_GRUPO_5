-------------------------------------------------------
-- Crear lab1bda
-- Database: lab1bda
-------------------------------------------------------
DROP DATABASE IF EXISTS "lab1bda";

CREATE DATABASE "lab1bda"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Chile.1252'
    LC_CTYPE = 'Spanish_Chile.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    TEMPLATE = template0;

\c lab1bda

CREATE EXTENSION postgis;

-------------------------------------------------------
-- Table "categoria"
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS "categoria" (
    "id_categoria" SERIAL PRIMARY KEY,
    "nombre" VARCHAR(100) NOT NULL
);

-------------------------------------------------------
-- Table "producto"
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS "producto" (
    "id_producto" SERIAL PRIMARY KEY,
    "nombre" VARCHAR(255) NOT NULL,
    "descripcion" TEXT,
    "precio" DECIMAL(10, 2) NOT NULL,
    "stock" INT NOT NULL,
    "estado" VARCHAR(50) NOT NULL CHECK ("estado" IN ('disponible', 'agotado')),
    "id_categoria" INTEGER,
    CONSTRAINT "fk_categoria"
        FOREIGN KEY ("id_categoria")
        REFERENCES "categoria" ("id_categoria")
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-------------------------------------------------------
-- Table "cliente"
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS "cliente" (
    "id_cliente" SERIAL PRIMARY KEY,
    "nombre" VARCHAR(255) NOT NULL,
    "direccion" VARCHAR(255),
    "email" VARCHAR(100) UNIQUE,
    "telefono" VARCHAR(20),
    "password" VARCHAR(255)
);

-------------------------------------------------------
-- Table "orden"
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS "orden" (
    "id_orden" SERIAL PRIMARY KEY,
    "fecha_orden" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "estado" VARCHAR(50) NOT NULL CHECK ("estado" IN ('pendiente', 'pagada', 'enviada')),
    "id_cliente" INTEGER,
    "total" DECIMAL(10, 2) NOT NULL,
    CONSTRAINT "fk_cliente"
        FOREIGN KEY ("id_cliente")
        REFERENCES "cliente" ("id_cliente")
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-------------------------------------------------------
-- Table "detalle_orden"
-------------------------------------------------------
CREATE TABLE IF NOT EXISTS "detalle_orden" (
    "id_detalle" SERIAL PRIMARY KEY,
    "id_orden" INTEGER,
    "id_producto" INTEGER,
    "cantidad" INT NOT NULL,
    "precio_unitario" DECIMAL(10, 2) NOT NULL,
    CONSTRAINT "fk_orden"
        FOREIGN KEY ("id_orden")
        REFERENCES "orden" ("id_orden")
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT "fk_producto"
        FOREIGN KEY ("id_producto")
        REFERENCES "producto" ("id_producto")
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-------------------------------------------------------
-- Table de auditoria
-------------------------------------------------------

CREATE TABLE query_audit (
    id_query SERIAL PRIMARY KEY,
    id_cliente INTEGER,
    tipo_operacion VARCHAR(10),   -- "INSERT", "UPDATE", "DELETE"
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    consulta TEXT                 -- Consulta ejecutada
);

-------------------------------------------------------
-- TABLA UTIL PARA LA AUDITORIA
-------------------------------------------------------

CREATE TABLE session_users (
    session_id SERIAL PRIMARY KEY,
    id_cliente INTEGER NOT NULL
);

-------------------------------------------------------
------- FUNCION PARA AUDITORIA
-------------------------------------------------------
CREATE OR REPLACE FUNCTION registrar_actividad()
RETURNS TRIGGER AS $$
DECLARE
    logged_user INTEGER;
BEGIN
    SELECT id_cliente INTO logged_user
    FROM session_users;

    INSERT INTO query_audit (id_cliente, tipo_operacion, consulta)
    VALUES (logged_user, TG_OP, current_query());

    IF (TG_OP = 'DELETE') THEN
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------
------------ TRIGGERS DE PROC ALMACENADO
-------------------------------------------------------

-- Trigger para la tabla cliente
CREATE TRIGGER audit_client
AFTER INSERT OR UPDATE OR DELETE ON cliente
FOR EACH ROW EXECUTE FUNCTION registrar_actividad();

-- Trigger para la tabla detalle_orden
CREATE TRIGGER audit_detail_order
AFTER INSERT OR UPDATE OR DELETE ON detalle_orden
FOR EACH ROW EXECUTE FUNCTION registrar_actividad();

-- Trigger para la tabla categoria
CREATE TRIGGER audit_category
AFTER INSERT OR UPDATE OR DELETE ON categoria
FOR EACH ROW EXECUTE FUNCTION registrar_actividad();

-- Trigger para la tabla producto
CREATE TRIGGER audit_product
AFTER INSERT OR UPDATE OR DELETE ON producto
FOR EACH ROW EXECUTE FUNCTION registrar_actividad();

-- Trigger para la tabla orden
CREATE TRIGGER audit_order
AFTER INSERT OR UPDATE OR DELETE ON orden
FOR EACH ROW EXECUTE FUNCTION registrar_actividad();

-------------------------------------------------------
-- Procedimiento almacenado para el reporte
-------------------------------------------------------

CREATE OR REPLACE FUNCTION reporte_act_client()
RETURNS TABLE(
    usuario INTEGER,
    inserts BIGINT,
    updates BIGINT,
    deletes BIGINT,
    total BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        query_audit.id_cliente,
        COUNT(CASE WHEN tipo_operacion = 'INSERT' THEN 1 END) AS inserts,
        COUNT(CASE WHEN tipo_operacion = 'UPDATE' THEN 1 END) AS updates,
        COUNT(CASE WHEN tipo_operacion = 'DELETE' THEN 1 END) AS deletes,
        COUNT(*) AS total
    FROM query_audit
    WHERE id_cliente IS NOT NULL
    GROUP BY query_audit.id_cliente
    ORDER BY total DESC;
END;
$$ LANGUAGE plpgsql;

-------------------------------------------------------
------------ FUNCIÓN DE TRIGGER
-------------------------------------------------------

CREATE OR REPLACE FUNCTION update_inventory()
RETURNS TRIGGER AS $$
BEGIN
    -- Disminuir el stock del producto basado en la cantidad insertada en detalle_orden
    UPDATE producto
    SET stock = stock - NEW.cantidad
    WHERE id_producto = NEW.id_producto;

    --Cambio del estado
    UPDATE producto
    SET estado = 'agotado'
    WHERE id_producto = NEW.id_producto AND stock = 0;

    RETURN NEW;

END;
$$ LANGUAGE plpgsql;


-------------------------------------------------------
------------ TRIGGER
-------------------------------------------------------

CREATE TRIGGER after_insert_detalle_orden
AFTER INSERT ON detalle_orden
FOR EACH ROW
EXECUTE FUNCTION update_inventory();
