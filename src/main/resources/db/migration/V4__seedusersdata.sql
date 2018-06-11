INSERT INTO users(id, enable, first_name, last_name, password, user_name)
	VALUES ( 1, true, 'admin', 'admin', '$2a$10$ealn.eQrbxIIPaaQVUSkJ.o4M7lKrK1maQvPmlAkqsN2nzm3/TKi.', 'admin');
	

INSERT INTO roles(id, name)	VALUES (1, 'ADMIN');

INSERT INTO public.users_roles(	user_id, role_id)
	VALUES (1, 1);
	
