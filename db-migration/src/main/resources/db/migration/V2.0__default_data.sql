-- Insert data into the 'states' table
INSERT INTO states (name, region, public_id, created_at, last_modified)
VALUES ('California', 'West', '4C1BQ4ESCRVBVZQYVVADQR8L', NOW(), NOW()),
       ('New York', 'Northeast', '5QQDQKV64QXF8P9R2ECVPYQJ', NOW(), NOW()),
       ('Texas', 'South', '5B4KYWFP28S9H8S7SWS2TJ33', NOW(), NOW()),
       ('Florida', 'South', '5VVPE42YRBKAZZ2EF4DSXT6F', NOW(), NOW());

-- Insert data into the 'cities' table
INSERT INTO cities (name, state_id, public_id, created_at, last_modified)
VALUES ('Los Angeles', 1, '57V6VA7KT7RWZB1PSBCRVPZR', NOW(), NOW()),
       ('San Francisco', 1, '4ED12E9P9EV4HR4ZS2XSVJY1', NOW(), NOW()),
       ('New York City', 2, '4K42AP78K7BPB2H8RBWKEZ55', NOW(), NOW()),
       ('Buffalo', 2, '5XC2HZZCJ34W3MQAABZT5W17', NOW(), NOW()),
       ('Austin', 3, '5S5Q7XTW3WSS9S5RAQXZT42R', NOW(), NOW()),
       ('Houston', 3, '47EJS8C25HZAQZ1K28VCSRVW', NOW(), NOW()),
       ('Miami', 4, '4XH7HWW2WKZ84JRA4HJP9J2K', NOW(), NOW());