import co.uk.orm.demo.domain.Customer


table(Customer, ['firstname', 'lastname', 'email']) {
    customer_1 = row 'Beatrice', 'Huber', '' + UUID.randomUUID() + '@mailosaur.com'
    customer_2 = row 'Drake', 'Sutton', '' + UUID.randomUUID() + '@mailosaur.com'
    customer_3 = row 'Orion', 'Sampson', '' + UUID.randomUUID() + '@mailosaur.com'
    customer_4 = row 'Ezra', 'Hamilton', '' + UUID.randomUUID() + '@mailosaur.com'
    customer_5 = row 'Paloma', 'Zhang', '' + UUID.randomUUID() + '@mailosaur.com'
}







/*

INSERT INTO Customer (firstname, lastname, email)
VALUES
    ('Beatrice', 'Huber', '@mailosaur.com'),
    ('Drake', 'Sutton', '@mailosaur.com'),
    ('Orion', 'Sampson', '@mailosaur.com'),
    ('Ezra', 'Hamilton', '@mailosaur.com'),
    ('Paloma', 'Zhang', '@mailosaur.com');

 */