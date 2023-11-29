CREATE TABLE employee_reference_book
(
    id            UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    first_name    VARCHAR(20)                                       NOT NULL,
    last_name     VARCHAR(20)                                       NOT NULL,
    middle_name   VARCHAR(20),
    position_held VARCHAR(20)                                       NOT NULL,
    email         VARCHAR(50) UNIQUE                                NOT NULL,
    phone_number  VARCHAR(11)                                       NOT NULL
);
