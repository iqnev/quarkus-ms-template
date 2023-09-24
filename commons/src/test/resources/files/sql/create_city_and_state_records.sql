INSERT INTO states (id, name, region, public_id, created_at, last_modified)
VALUES (1, 'California', 'West', '4C1BQ4ESCRVBVZQYVVADQR8L', NOW(), NOW()),
       (2, 'New York', 'Northeast', '5QQDQKV64QXF8P9R2ECVPYQJ', NOW(), NOW());

INSERT INTO cities (id, name, state_id, public_id, created_at, last_modified)
VALUES (1, 'Los Angeles', 1, '57V6VA7KT7RWZB1PSBCRVPZR', NOW(), NOW()),
       (2, 'San Francisco', 1, '4ED12E9P9EV4HR4ZS2XSVJY1', NOW(), NOW()),
       (3, 'New York City', 2, '4K42AP78K7BPB2H8RBWKEZ55', NOW(), NOW());