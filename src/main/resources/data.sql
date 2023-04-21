INSERT INTO address (id, zip_code, house_number, street, town)
VALUES (1L, '10-100', '3c', 'ul. Kolejowa', 'Poznań'),
       (2L, '20-100', '1', 'ul. Główna', 'Warszawa'),
       (3L, '30-100', '2', 'ul. Uliczna', 'Kraków'),
       (4L, '40-100', '1', 'aleje jakieśtam', 'Wrocław'),
       (5L, '50-100', '12', 'ul. Nienazwana', 'Łódź'),
       (6L, '60-100', '234', 'ul. Kolejowa', 'Gdańsk');

INSERT INTO client(id, email, first_name, last_name, address_id)
VALUES (1L, 'client1@mail.com', 'Jan', 'Kowalski', 1L),
       (2L, 'ziomek@mail.com', 'Janina', 'Kowalska', 1L),
       (3L, 'koles@mail.com', 'Jan', 'Szymański', 2L);

INSERT INTO pricelist(id, price_per_day, price_per_week, price_per_month)
VALUES (1L, 100, 90, 80),
       (2L, 500, 450, 400);

INSERT INTO finances(id)
VALUES (1L);

INSERT INTO car_rental_company(id, domain, name, address_id, finances_id)
VALUES(1L, 'www.web.com', 'fajna firma', 3L, 1L);

INSERT INTO branch_office(id, address_id, car_rental_company_id)
VALUES(1L, 4L, 1L),
      (2L, 5L, 1L),
      (3L, 6L, 1L);

INSERT INTO employee (id, first_name, job_position, last_name, branch_office_id)
VALUES(1L,'super', 'SELLER', 'pracownik',1L),
      (2L,'super', 'MANAGER', 'manager',1L),
      (3L,'sprzedawca', 'SELLER', 'z dwójki',2L),
      (4L,'menedżer', 'MANAGER', 'z dwójki',2L),
      (5L,'Johhny', 'MANAGER', 'Bravo',3L);

INSERT INTO car (id, body_type, color, make, mileage, min_rental_time, model, status, year_of_manufacture, current_branch_office_id, pricelist_id)
VALUES (1L, 'SUPERMINI', 'RED', 'Mazda', 20000, 5, '2','AVAILABLE',2015,1L,1L),
       (2L, 'SUV', 'BLACK', 'Mercedes', 140, 1, 'GLS','AVAILABLE',2021,2L,2L),
       (3L, 'ESTATE', 'WHITE', 'Ford', 8000, 5, 'Focus','UNAVAILABLE',2018,3L,1L);

INSERT INTO car_pick_up(id,comments,pick_up_date,car_id,employee_id, status)
VALUES (1L, 'first rental','2010-10-10',1L,1L,'REALIZED'),
       (2L, null,'2010-10-15',2L,2L,'REALIZED'),
       (3L, null,'2011-02-10',3L,4L,'REALIZED'),
       (4L, null,'2012-10-10',1L,2L,'REALIZED'),
       (5L, 'random comment','2013-10-10',1L,1L,'REALIZED'),
       (6L, null,'2014-10-02',2L,1L,'REALIZED'),
       (7L, null,'2014-09-12',2L,3L,'REALIZED'),
       (8L, null,'2015-08-05',1L,1L,'REALIZED'),
       (9L, null,'2016-04-23',1L,1L,'PLANNED');

INSERT INTO car_return (id, comments, extra_charge, return_date, car_id, employee_id)
VALUES (1L, 'first return', 30, '2010-10-12',1L,2L),
       (2L, null, null, '2012-10-12',2L,2L),
       (3L, null, null, '2013-10-12',1L,5L),
       (4L, null, 10, '2015-10-12',3L,5L),
       (5L, 'fifth return', 30000, '2020-10-12',1L,2L),
       (6L, null, 18, '2021-10-12',2L,5L);

INSERT INTO reservation (id, price, reservation_date, car_id, client_id, car_pick_up_id, car_return_id, pick_up_office_id, return_office_id)
VALUES(1L,800,'2000-10-10',1L, 1L, 1L, 1L, 1L, 2L),
      (2L,100,'2010-10-10',2L, 2L, 2L, 4L, 2L, 3L),
      (3L,300,'2012-10-10',1L, 1L, 4L, 2L, 3L, 1L);

INSERT INTO income (id, income_value, finances_id, reservation_id)
VALUES (1L, 300, 1L, 1L),
       (2L, 500, 1L, 1L),
       (3L, 100, 1L, 2L),
       (4L, 1000, 1L, 3L);




