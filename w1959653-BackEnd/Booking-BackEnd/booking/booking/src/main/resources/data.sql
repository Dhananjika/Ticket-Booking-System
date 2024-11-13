-- Event table
INSERT INTO ticket.event (event_id, event_name, event_type)
SELECT 1, 'Music Plaza', 'Music'
    WHERE NOT EXISTS (SELECT 1 FROM ticket.vendor WHERE vendor_id = 1);

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