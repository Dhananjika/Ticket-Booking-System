CREATE TABLE IF NOT EXISTS event (
    event_id INT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    event_date VARCHAR(255) NOT NULL,
    event_time VARCHAR(255) NOT NULL,
    event_normal_ticket_price INT NOT NULL,
    event_vip_ticket_price INT NOT NULL,
    event_image VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS vendor (
    vendor_id VARCHAR(255) NOT NULL,
    vendor_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    event_id INT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES event(event_id)
);

CREATE TABLE IF NOT EXISTS register (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login_type VARCHAR(10) NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    vendor_id VARCHAR(255),
    signup_date  VARCHAR(15) NOT NULL,
    customer_name VARCHAR(255) ,
    customer_email VARCHAR(255) ,
    login_status VARCHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS configuration (
    event_id INT NOT NULL PRIMARY KEY,
    system_status VARCHAR(2) NOT NULL DEFAULT 'I',
    config_status VARCHAR(2) NOT NULL DEFAULT 'I',
    stop_release_count INT,
    stop_pool_size INT,
    FOREIGN KEY (event_id) REFERENCES event(event_id)
);