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
    client_id integer, 
    FOREIGN KEY (client_id) REFERENCES client(client_id)
);

CREATE TABLE client_person (
    client_id integer,
    person_id integer,
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    FOREIGN KEY (person_id) REFERENCES person(person_id)
);