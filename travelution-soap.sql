CREATE TABLE logging (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(256) NOT NULL,
    IP VARCHAR(16) NOT NULL,
    endpoint VARCHAR(256) NOT NULL,
    req_time TIMESTAMP NOT NULL
);

CREATE TABLE subscription (
    creator_id INT NOT NULL,
    subscriber_id INT NOT NULL,
    status ENUM ('PENDING', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (creator_id, subscriber_id)
);