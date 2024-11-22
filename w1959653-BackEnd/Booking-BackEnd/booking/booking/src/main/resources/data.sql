-- Event table
INSERT INTO ticket.event (event_id, event_name, event_type, location, event_date, event_time)
SELECT 1, 'Music Plaza', 'Music', 'London', '20-12-2024', '19:00'
    WHERE NOT EXISTS (SELECT 1 FROM ticket.event WHERE event_id = 1);

-- Vendor table
INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '001', 'John', 'john@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '001');

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '002', 'Robert', 'robert@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '002');

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '003', 'Mary', 'mary@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '003');

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '004', 'Thomas', 'thomas@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '004');

INSERT INTO ticket.vendor (vendor_id, vendor_name, email, event_id)
SELECT '005', 'Linda', 'linda@gmail.com', 1
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = '005');


--configuration table
INSERT INTO ticket.configuration (event_id, system_status, config_status, stop_release_count, stop_pool_size)
SELECT 1, 'I', 'I', 0,0
    WHERE NOT EXISTS (SELECT 1 FROM ticket.configuration WHERE event_id = 1);

UPDATE ticket.configuration SET system_status='I', config_status='I', stop_release_count=0, stop_pool_size=0
                            WHERE event_id=1;