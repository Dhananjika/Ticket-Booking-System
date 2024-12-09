-- Event table
INSERT INTO ticket.event (event_id, event_name, event_type, location, event_date, event_time,event_normal_ticket_price, event_vip_ticket_price, event_image)
SELECT 1, 'Music Plaza', 'Music', 'Lotus Tower Car Park Colombo', '20-12-2024', '19:00', 1500, 3000, "1"
    WHERE NOT EXISTS (SELECT 1 FROM ticket.event WHERE event_id = 1);

INSERT INTO ticket.event (event_id, event_name, event_type, location, event_date, event_time,event_normal_ticket_price, event_vip_ticket_price, event_image)
SELECT 2, 'Christmas Concert', 'Music', 'Bishops College Auditorium', '24-12-2024', '22:00', 2000, 5000, "2"
    WHERE NOT EXISTS (SELECT 1 FROM ticket.event WHERE event_id = 2);

INSERT INTO ticket.event (event_id, event_name, event_type, location, event_date, event_time,event_normal_ticket_price, event_vip_ticket_price, event_image)
SELECT 3, 'Dance On Sunset', 'Dance', 'University Of Moratuwa', '16-12-2024', '18:00', 1250, 2500, "3"
    WHERE NOT EXISTS (SELECT 1 FROM ticket.event WHERE event_id = 3);

-- Vendor table
INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '001', 'John', 'john@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '001' AND event_id = 1);

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '002', 'Robert', 'robert@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '002' AND event_id = 1);

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '003', 'Mary', 'mary@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '003' AND event_id = 1);

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '004', 'Thomas', 'thomas@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '004' AND event_id = 1);

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '005', 'Linda', 'linda@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '005' AND event_id = 1);

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '001', 'John', 'john@gmail.com', 2
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '001' AND event_id = 2);


--configuration table
INSERT INTO ticket.configuration (event_id, system_status, config_status, stop_release_count, stop_pool_size)
SELECT 1, 'I', 'I', 0,0
    WHERE NOT EXISTS (SELECT 1 FROM ticket.configuration WHERE event_id = 1);

INSERT INTO ticket.configuration (event_id, system_status, config_status, stop_release_count, stop_pool_size)
SELECT 2, 'I', 'I', 0,0
    WHERE NOT EXISTS (SELECT 1 FROM ticket.configuration WHERE event_id = 2);

INSERT INTO ticket.configuration (event_id, system_status, config_status, stop_release_count, stop_pool_size)
SELECT 3, 'I', 'I', 0,0
    WHERE NOT EXISTS (SELECT 1 FROM ticket.configuration WHERE event_id = 3);

UPDATE ticket.configuration SET system_status='I', config_status='I', stop_release_count=0, stop_pool_size=0
                            WHERE event_id=1;
UPDATE ticket.configuration SET system_status='I', config_status='I', stop_release_count=0, stop_pool_size=0
                            WHERE event_id=2;
UPDATE ticket.configuration SET system_status='I', config_status='I', stop_release_count=0, stop_pool_size=0
                            WHERE event_id=3;