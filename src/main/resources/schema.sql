-- Création des tables
CREATE TABLE IF NOT EXISTS delivery_modes (
    mode VARCHAR(50) PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    max_days_in_advance INT NOT NULL,
    allows_pre_booking BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS time_slots (
    id UUID PRIMARY KEY,
    date VARCHAR(50) NOT NULL,
    start_time VARCHAR(50) NOT NULL,
    end_time VARCHAR(50) NOT NULL,
    delivery_mode VARCHAR(50) NOT NULL,
    booked BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_time_slots_delivery_mode FOREIGN KEY (delivery_mode) REFERENCES delivery_modes(mode)
);

CREATE TABLE IF NOT EXISTS customers (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS bookings (
    id UUID PRIMARY KEY,
    customer_id INT NOT NULL,
    time_slot_id UUID NOT NULL,
    date VARCHAR(50) NOT NULL,
    start_time VARCHAR(50) NOT NULL,
    end_time VARCHAR(50) NOT NULL,
    delivery_mode VARCHAR(50) NOT NULL,
    booking_time VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_bookings_customer_id FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_bookings_time_slot_id FOREIGN KEY (time_slot_id) REFERENCES time_slots(id),
    CONSTRAINT fk_bookings_delivery_mode FOREIGN KEY (delivery_mode) REFERENCES delivery_modes(mode)
);

-- Indexes pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_time_slots_date ON time_slots(date);
CREATE INDEX IF NOT EXISTS idx_time_slots_delivery_mode ON time_slots(delivery_mode);
CREATE INDEX IF NOT EXISTS idx_bookings_customer_id ON bookings(customer_id);
CREATE INDEX IF NOT EXISTS idx_bookings_time_slot_id ON bookings(time_slot_id);
