CREATE TABLE client (
    client_id integer IDENTITY,
    company_name varchar(100) NOT NULL,
    website_uri varchar(100),
    phone_number varchar(20),
    address varchar(100)
);

CREATE TABLE person (
    person_id integer IDENTITY,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email_address varchar(50) NOT NULL,
    street_address varchar(50) NOT NULL,
    city varchar(50) NOT NULL,
    state varchar(2) NOT NULL,
    zip_code varchar(5) NOT NULL,
    client_id integer,  -- to establish a relationship with the Client table
    FOREIGN KEY (client_id) REFERENCES client(client_id)  -- to define the foreign key constraint
);

CREATE TABLE client_person (
    client_id integer,
    person_id integer,
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    FOREIGN KEY (person_id) REFERENCES person(person_id)
);

INSERT INTO client (company_name, website_uri, phone_number, address) VALUES ('Sample Company', 'http://www.samplecompany.com', '123-456-7890', '123 Sample St, Sample City, SC');

INSERT INTO person (first_name, last_name, email_address, street_address, city, state, zip_code, client_id)
VALUES ('John', 'Doe', 'john.doe@example.com', '123 Main St', 'Anytown', 'NY', '12345', 1),
       ('Jane', 'Doe', 'jane.doe@example.com', '456 Elm St', 'Anytown', 'NY', '12345', 1),
       ('Alice', 'Smith', 'alice.smith@example.com', '789 Oak St', 'Anytown', 'NY', '12345', 1);

INSERT INTO client_person (client_id, person_id) VALUES (1, 1);
INSERT INTO client_person (client_id, person_id) VALUES (1, 2);
INSERT INTO client_person (client_id, person_id) VALUES (1, 3);