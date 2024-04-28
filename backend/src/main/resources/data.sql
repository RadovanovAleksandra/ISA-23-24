INSERT INTO public.loyalty_programs(
	discount_rate, min_number_of_points, name, on_reservation, on_cancel)
	VALUES (3, 5, 'White', 2, 2);

INSERT INTO public.loyalty_programs(
	discount_rate, min_number_of_points, name, on_reservation, on_cancel)
	VALUES (7, 10, 'Green', 3, 4);

INSERT INTO public.loyalty_programs(
	discount_rate, min_number_of_points, name, on_reservation, on_cancel)
	VALUES (10, 20, 'Red', 4, 8);


INSERT INTO public.users(
	city, email, lastname, name, password, phone, points, profession, role, state, verified, loyalty_program_id)
	VALUES (
		'Novi Sad',
		'regular@user.com',
		'Regular',
		'User',
		'$2a$10$b/LGI7H/67jPpXxAkTzk.eFPslTzoIWKrKnUjAfNCvWuEJQmyHJuG',
		'111-222',
		5,
		'IT',
		'USER',
		'Serbia',
		true,
		1);

INSERT INTO public.users(
	city, email, lastname, name, password, phone, points, profession, role, state, verified, loyalty_program_id)
	VALUES (
		'Novi Sad',
		'com_adm1@user.com',
		'Company Admin',
		'1',
		'$2a$10$b/LGI7H/67jPpXxAkTzk.eFPslTzoIWKrKnUjAfNCvWuEJQmyHJuG',
		'212-333',
		0,
		'Admin',
		'COMPANY_ADMIN',
		'Serbia',
		true,
		null);

INSERT INTO public.users(
	city, email, lastname, name, password, phone, points, profession, role, state, verified, loyalty_program_id)
	VALUES (
		'Novi Sad',
		'com_adm2@user.com',
		'Company Admin',
		'2',
		'$2a$10$b/LGI7H/67jPpXxAkTzk.eFPslTzoIWKrKnUjAfNCvWuEJQmyHJuG',
		'555-333',
		0,
		'Admin',
		'COMPANY_ADMIN',
		'Serbia',
		true,
		null);

INSERT INTO public.users(
	city, email, lastname, name, password, phone, points, profession, role, state, verified, loyalty_program_id)
	VALUES (
		'Novi Sad',
		'com_adm3@user.com',
		'Company Admin',
		'3',
		'$2a$10$b/LGI7H/67jPpXxAkTzk.eFPslTzoIWKrKnUjAfNCvWuEJQmyHJuG',
		'555-777',
		0,
		'Admin',
		'COMPANY_ADMIN',
		'Serbia',
		true,
		null);

INSERT INTO public.users(
	city, email, lastname, name, password, phone, points, profession, role, state, verified, loyalty_program_id)
	VALUES (
		'Novi Sad',
		'admin@user.com',
		'Admin',
		'Admin',
		'$2a$10$b/LGI7H/67jPpXxAkTzk.eFPslTzoIWKrKnUjAfNCvWuEJQmyHJuG',
		'000-000',
		0,
		'Admin',
		'ADMIN',
		'Serbia',
		true,
		null);

INSERT INTO public.companies(
	address, city, name, working_hours_end, working_hours_start)
	VALUES ('Bavarska 1', 'Novi sad', 'MediProdukt', '22:00', '08:00');

INSERT INTO public.companies(
	address, city, name, working_hours_end, working_hours_start)
	VALUES ('Ohridska 2', 'Novi sad', 'HealthCare', '18:00','10:00');

INSERT INTO public.companies(
	address, city, name, working_hours_end, working_hours_start)
	VALUES ('Julija Cezara 15', 'Novi sad', 'Optics House', '23:00', '04:00');


INSERT INTO public.company_admins(
	company_id, user_id)
	VALUES (1, 2);

INSERT INTO public.company_admins(
	company_id, user_id)
	VALUES (2, 3);

INSERT INTO public.company_admins(
	company_id, user_id)
	VALUES (2, 4);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('Stetoskop', 1, 2, 2000);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('Merac pritiska', 1, 4, 2500);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('EKG', 1, 1, 80000);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('Skener', 1, 2, 200000);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('Operacioni sto', 2, 10, 17000);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('Stolica za doktora', 2, 0, 9000);

INSERT INTO public.equipments(
	name, company_id, available_quantity, price)
	VALUES ('Zubarska stolica', 3, 3, 900000);

INSERT INTO public.terms(
	duration_in_minutes, irregular, start, admin_id, company_id, reservation_id)
	VALUES (20, false, '2024-06-15 10:30', 2, 1, null);

INSERT INTO public.terms(
	duration_in_minutes, irregular, start, admin_id, company_id, reservation_id)
	VALUES (20, false, '2024-06-15 11:00', 2, 1, null);

INSERT INTO public.terms(
	duration_in_minutes, irregular, start, admin_id, company_id, reservation_id)
	VALUES (20, false, '2024-06-16 10:30', 2, 1, null);

INSERT INTO public.terms(
	duration_in_minutes, irregular, start, admin_id, company_id, reservation_id)
	VALUES (20, false, '2024-06-16 11:30', 2, 1, null);

INSERT INTO public.terms(
	duration_in_minutes, irregular, start, admin_id, company_id, reservation_id)
	VALUES (20, false, '2024-06-16 12:30', 3, 2, null);