-- Script único para recrear todo (crear BD + datos + ejecutar):

Create database if not exists tpiprogra2;
use tpiprogra2;


CREATE TABLE envio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    tracking VARCHAR(40) NOT NULL UNIQUE,
    empresa VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    fechaDespacho DATE NOT NULL,
    fechaEstimada DATE NOT NULL,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    numero VARCHAR(20) NOT NULL UNIQUE,
    fecha DATE NOT NULL,
    clienteNombre VARCHAR(120) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    estado VARCHAR(20) NOT NULL,

    envio_id INT NOT NULL UNIQUE,
    FOREIGN KEY (envio_id) REFERENCES envio(id)
);


INSERT INTO envio (
    eliminado, tracking, empresa, tipo, costo, fechaDespacho, fechaEstimada, estado
) VALUES
(FALSE, 'TTK-001', 'ANDREANI', 'ESTANDAR', 1500.00, '2025-01-02', '2025-01-05', 'EN_PREPARACION'),
(FALSE, 'TTK-002', 'OCA', 'EXPRES', 2300.50, '2025-01-03', '2025-01-04', 'EN_TRANSITO'),
(FALSE, 'TTK-003', 'CORREO_ARG', 'ESTANDAR', 1200.75, '2025-01-01', '2025-01-06', 'ENTREGADO'),
(FALSE, 'TTK-004', 'ANDREANI', 'EXPRES', 2800.00, '2025-01-04', '2025-01-05', 'EN_TRANSITO'),
(FALSE, 'TTK-005', 'OCA', 'ESTANDAR', 1600.20, '2025-01-02', '2025-01-07', 'EN_PREPARACION'),
(FALSE, 'TTK-006', 'CORREO_ARG', 'EXPRES', 2500.90, '2025-01-05', '2025-01-06', 'EN_PREPARACION'),
(FALSE, 'TTK-007', 'ANDREANI', 'ESTANDAR', 1400.10, '2025-01-03', '2025-01-08', 'EN_TRANSITO'),
(FALSE, 'TTK-008', 'OCA', 'EXPRES', 3000.00, '2025-01-06', '2025-01-07', 'EN_TRANSITO'),
(FALSE, 'TTK-009', 'CORREO_ARG', 'ESTANDAR', 1100.55, '2025-01-04', '2025-01-09', 'EN_PREPARACION'),
(FALSE, 'TTK-010', 'ANDREANI', 'EXPRES', 2700.00, '2025-01-07', '2025-01-08', 'ENTREGADO');


INSERT INTO pedido (
    eliminado, numero, fecha, clienteNombre, total, estado, envio_id
) VALUES
(FALSE, 'PED-001', '2025-01-02', 'Juan Pérez', 12500.00, 'NUEVO', 11),
(FALSE, 'PED-002', '2025-01-03', 'María Gómez', 18300.50, 'FACTURADO', 2),
(FALSE, 'PED-003', '2025-01-01', 'Lucas Fernández', 9500.75, 'ENVIADO', 3),
(FALSE, 'PED-004', '2025-01-04', 'Carla López', 21000.00, 'FACTURADO', 4),
(FALSE, 'PED-005', '2025-01-02', 'Sofía Martínez', 13200.20, 'NUEVO', 5),
(FALSE, 'PED-006', '2025-01-05', 'Martín Sánchez', 15800.90, 'FACTURADO', 6),
(FALSE, 'PED-007', '2025-01-03', 'Ana Torres', 7800.10, 'ENVIADO', 7),
(FALSE, 'PED-008', '2025-01-06', 'Ricardo Gómez', 29900.00, 'FACTURADO', 8),
(FALSE, 'PED-009', '2025-01-04', 'Valentina Díaz', 11500.55, 'NUEVO', 9),
(FALSE, 'PED-010', '2025-01-07', 'Tomás Herrera', 18700.00, 'ENVIADO', 10);