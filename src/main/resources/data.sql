-- Données initiales pour les modes de livraison
INSERT INTO delivery_modes (mode, description, max_days_in_advance, allows_pre_booking)
VALUES ('DRIVE', 'Récupération en magasin', 7, true),
       ('DELIVERY', 'Livraison standard', 14, true),
       ('DELIVERY_TODAY', 'Livraison le jour même', 0, true),
       ('DELIVERY_ASAP', 'Livraison dès que possible', 0, false);

-- Insérer un client de test
INSERT INTO customers (id, name, email, phone_number, address)
VALUES (1, 'SOUFIANE NAIT ALI', 'souf.nait@test.com', '0123456789', '123 Test Avenue');

-- Insérer des TimeSlot
INSERT INTO time_slots (id, date, start_time, end_time, delivery_mode, booked, version)
VALUES ('b93412c2-c64b-42a3-aaef-86b8dfeee687', '2025-07-16', '10:00', '12:00', 'DELIVERY_TODAY', false, 1),
       ('78e4fd8a-fa3a-4c5c-b3c4-9287af5727e4', '2025-07-17', '09:00', '11:00', 'DRIVE', false, 1),
       ('751b19d7-4ba4-4902-96d9-32440c10581a', '2025-07-18', '14:00', '16:00', 'DELIVERY', false, 1),
       ('5309d239-ecc9-4c32-9cb9-ebeba5ba8167', '2025-07-18', '10:00', '11:00', 'DELIVERY_ASAP', false, 1);
