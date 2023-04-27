INSERT INTO address (zip_code, house_number, street, town)
VALUES ('10-100', '3c', 'ul. Kolejowa', 'Poznań'),
       ('20-100', '1', 'ul. Główna', 'Warszawa'),
       ('30-100', '2', 'ul. Uliczna', 'Kraków'),
       ('40-100', '1', 'aleje jakieśtam', 'Wrocław'),
       ('50-100', '12', 'ul. Nienazwana', 'Łódź'),
       ('60-100', '234', 'ul. Kolejowa', 'Gdańsk');

INSERT INTO client(email, first_name, last_name, address_id)
VALUES ('client1@mail.com', 'Jan', 'Kowalski', 1L),
       ('ziomek@mail.com', 'Janina', 'Kowalska', 1L),
       ('koles@mail.com', 'Jan', 'Szymański', 2L);

INSERT INTO pricelist(price_per_day, price_per_week, price_per_month)
VALUES (100, 90, 80),
       (500, 450, 400);

INSERT INTO finances(id)
VALUES (1L);

INSERT INTO company(domain, name, address_id, finances_id)
VALUES('www.web.com', 'fajna firma', 3L, 1L);

INSERT INTO branch_office(address_id, company_id)
VALUES(4L, 1L),
      (5L, 1L),
      (6L, 1L);

INSERT INTO employee (first_name, job_position, last_name, branch_office_id)
VALUES('super', 'SELLER', 'pracownik',1L),
      ('super', 'MANAGER', 'manager',1L),
      ('sprzedawca', 'SELLER', 'z dwójki',2L),
      ('menedżer', 'MANAGER', 'z dwójki',2L),
      ('Johhny', 'MANAGER', 'Bravo',3L);

INSERT INTO car (body_type, color, make, mileage, min_rental_time, model, status, year_of_manufacture, current_branch_office_id, pricelist_id)
VALUES ('SUPERMINI', 'RED', 'Mazda', 20000, 5, '2','AVAILABLE',2015,1L,1L),
       ('SUV', 'BLACK', 'Mercedes', 140, 1, 'GLS','AVAILABLE',2021,2L,2L),
       ('ESTATE', 'WHITE', 'Ford', 8000, 5, 'Focus','UNAVAILABLE',2018,3L,1L);

INSERT INTO car_pick_up(comments,pick_up_date,car_id,employee_id, status)
VALUES ('first rental','2010-10-10',1L,1L,'REALIZED'),
       (null,'2010-10-15',2L,2L,'REALIZED'),
       (null,'2011-02-10',3L,4L,'REALIZED'),
       (null,'2012-10-10',1L,2L,'REALIZED'),
       ('random comment','2013-10-10',1L,1L,'REALIZED'),
       (null,'2014-10-02',2L,1L,'REALIZED'),
       (null,'2014-09-12',2L,3L,'REALIZED'),
       (null,'2015-08-05',1L,1L,'REALIZED'),
       (null,'2016-04-23',1L,1L,'PLANNED');

INSERT INTO car_return (comments, extra_charge, return_date, car_id, employee_id)
VALUES ('first return', 30, '2010-10-12',1L,2L),
       (null, null, '2012-10-12',2L,2L),
       (null, null, '2013-10-12',1L,5L),
       (null, 10, '2015-10-12',3L,5L),
       ('fifth return', 30000, '2020-10-12',1L,2L),
       (null, 18, '2021-10-12',2L,5L);

INSERT INTO reservation (price, reservation_date, car_id, client_id, car_pick_up_id, car_return_id, pick_up_office_id, return_office_id)
VALUES(800,'2000-10-10',1L, 1L, 1L, 1L, 1L, 2L),
      (100,'2010-10-10',2L, 2L, 2L, 4L, 2L, 3L),
      (300,'2012-10-10',1L, 1L, 4L, 2L, 3L, 1L);

INSERT INTO income (income_value, finances_id, reservation_id)
VALUES (300, 1L, 1L),
       (500, 1L, 1L),
       (100, 1L, 2L),
       (1000, 1L, 3L);




