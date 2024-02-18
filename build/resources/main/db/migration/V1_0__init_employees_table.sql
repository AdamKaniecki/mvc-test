
CREATE TABLE employee
(
    employee_id SERIAL      NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    salary       NUMERIC(7,2) NOT NULL,
    phone VARCHAR(32) not null,
    email VARCHAR(32) not null,
    PRIMARY KEY (employee_id)
);