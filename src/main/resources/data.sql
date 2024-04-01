INSERT INTO client (company_name, website_uri, phone_number, address) 
VALUES ('Sample Company', 'http://www.samplecompany.com', '123-456-7890', '123 Sample St, Sample City, SC'),
       ('Another Company', 'http://www.anothercompany.com', '987-654-3210', '456 Another St, Different City, DC'),
       ('Aquent', 'https://aquent.com/', '617-535-5000', '501 Boylston St, Boston, MA');


INSERT INTO person (first_name, last_name, email_address, street_address, city, state, zip_code, client_id)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '123 Main St', 'Anytown', 'NY', '12345', 1),
    ('Jane', 'Doe', 'jane.doe@example.com', '456 Elm St', 'Anytown', 'NY', '12345', 1),
    ('Alice', 'Smith', 'alice.smith@example.com', '789 Oak St', 'Anytown', 'NY', '12345', 1),
    ('Bob', 'Johnson', 'bob.johnson@example.com', '321 Pine St', 'Othertown', 'CA', '67890', 2),
    ('Sara', 'Miller', 'sara.miller@example.com', '654 Maple St', 'Othertown', 'CA', '67890', 2),
    ('Mike', 'Davis', 'mike.davis@example.com', '987 Birch St', 'Othertown', 'CA', '67890', 2),
    ('John', 'Smith', 'fake1@aquent.com', '123 Any St.', 'Asheville', 'NC', '28801', 3),
    ('Jane', 'Smith', 'fake2@aquent.com', '123 Any St.', 'Asheville', 'NC', '28801', 3);

INSERT INTO client_person (client_id, person_id) 
VALUES (1, 1),
       (1, 2),
       (1, 3), 
       (2, 4),
       (2, 5), 
       (2, 6),
       (3, 7),
       (3, 8);