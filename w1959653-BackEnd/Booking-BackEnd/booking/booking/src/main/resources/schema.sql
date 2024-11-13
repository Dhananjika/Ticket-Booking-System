CREATE TABLE IF NOT EXISTS event (
    event_id INT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS vendor (
    vendor_id VARCHAR(255) PRIMARY KEY,
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
    login_status VARCHAR(1) NOT NULL,
    FOREIGN KEY (vendor_id) REFERENCES vendor(vendor_id)
);