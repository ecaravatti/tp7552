-- Prestaciones
insert ignore into prestacion (codigo, descripcion, costo_adicional)
values
(1,"Colposcopía", 20),
(2,"Radiografías simples (no contrastadas)", 15),
(3,"Electrocardiograma", 40),
(4,"Mamografía simple", 50),
(5,"Ecocardiograma completo", 50),
(6,"Ergometría", 37),
(7,"Ecografía renal", 35),
(8,"Ecografía de Tiroides", 37),
(9,"Audiometría", 10),
(10,"Análisis de Orina", 10),
(11,"Impedanciometria", 150),
(12,"Análisis de sangre", 15),
(13,"Hisopado", 30),
(14,"Rigntoteritis", 70),
(15,"Masajes Varios", 400),
(16,"Zondrobacion Vascular", 75),
(17,"Enemas Varios", 350),
(18,"Metrocardiograma", 70),
(19,"Menchogometria", 35),
(20,"Implante de Mamas", 10000);

-- Planes
insert ignore into plan (codigo, costo_mensual, descripcion)
values
(1,250,"A-210"),
(2,350,"Rj-310"),
(3,450,"HP-410"),
(4,350,"LX-450"),
(5,100,"PQ-950");

-- Coberturas
insert ignore  into cobertura (id, costo_adicional, prestacion_id)
values
(1,20,1),
(2,15,2),
(3,22,3),
(4,60,4),
(5,44,5),
(6,40,6),
(7,34,7),
(8,80,8),
(9,40,9),
(10,40,10),
(11,41,11),
(12,40,12),
(13,44,13),
(14,40,14),
(15,34,15),
(16,80,16),
(17,40,17),
(18,40,18),
(19,41,19),
(20,40,20);


-- Relacion Planes-Coberturas
insert ignore into plan_cobertura (plan_id, cobertura_id)
values
(1,1),
(1,2),
(1,3),
(1,4),
(2,5),
(2,6),
(2,7),
(2,8),
(3,9),
(3,10),
(3,11),
(3,12),
(4,13),
(4,14),
(4,15),
(4,16),
(5,17),
(5,18),
(5,19),
(5,20);


-- Historia Clinica
insert ignore into historia_clinica (id)
values (1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10);


-- Tabla de Pacientes
insert ignore into paciente (id, apellido, fecha_ingreso, nombre, plan_id, historia_clinica_id)
values(1, "Godines", "2008-05-01", "Juanelo", 1, 1),
(2, "Medina", "2007-06-01", "Teto Ariel", 3, 2),
(3, "Carlos", "2006-05-11", "Roberto", 5, 3),
(4, "Rodriguez Bombita", "2005-08-01", "Laura", 2, 4),
(5, "Pepo", "2008-04-01", "Esteban", 2, 5),
(6, "Maravilla", "2008-01-01", "Ricardo", 3, 6),
(7, "Dizzi", "2008-04-01", "Emilio", 1, 7),
(8, "Avellana", "2008-03-01", "Cacho", 2, 8),
(9, "Silly", "2008-08-01", "Roque", 3, 9),
(10, "Edgar", "2008-07-01", "Vivar", 4, 10);

-- Tabla de Medicos
insert ignore into medico (id, nombre, especialidad)
values(1, "Dr. Cureta", "Cirugia"),
(2, "Dr. He-Man", "Masajista"),
(3, "Dr. Chapatin", "Curandero"),
(4, "Dr. Lamato", "Niños"),
(5, "Dra. Alfano", "Cirugia Plástica"),
(6, "Dra. Moria", "Psicologa de niños"),
(7, "Dra. Rimollo", "Dietologa no matriculada"),
(8, "Dr. Pocho la Pantera", "Espiritista"),
(9, "Dr. Roberto Edgar Volcan", "Entretener enfermos"),
(10, "Dr. Bilardo", "Tactica y estrategia");

-- Areas
insert ignore into area (id, nombre) values (1, "Análisis Clínicos");
insert ignore into area (id, nombre) values (2, "Ecografías");
insert ignore into area (id, nombre) values (3, "Radiología");
insert ignore into area (id, nombre) values (4, "Resonancias");
insert ignore into area (id, nombre) values (5, "Tomografías");

-- Tecnicos
insert ignore into tecnico (id, edad, nombre, salario)
values  (1,27,"Homero Simpson",2000),
		(2,35,"Niccolino Roche",1500),
		(3,26,"Bombita Rodriguez",3600),
        (4,46,"Pedro Picapiedra",600),
        (5,56,"Claudio Paul Pajaro Caniggia",1600),
        (6,66,"Wanda Nara de Lopez",800),
        (7,37,"Esteban Bichi Fuertes",900),
        (8,58,"Maria Laura Ciruelanga",50),
        (9,26,"Rodrigo Hiena Barrios",2600),
        (10,26,"Isabel Coca Sarli",8000);


-- Relacion Tecnico-Area
insert ignore into tecnico_area (tecnico_id, area_id)
values	(1,1), (1,2), (1,3),
		(2,1),  
        (3,2), (3,3),
        (4,3), (4,5),
        (5,1), (5,3),
        (6,1), (6,3), (6,5),
        (7,2), (7,3),        
        (8,3), (8,5),        
        (9,1), (9,2), (9,3),        
        (10,1), (10,2),	(10,3);   
        
-- Practicas
insert ignore into practica (id, aprobada, fecha_realizacion, fecha_resultado, prestacion)
values	(1,1,NULL,NULL,1),
		(2,0,NULL,NULL,2),
		(3,1,NULL,NULL,14),
		(4,1,NULL,NULL,5),
		(5,0,NULL,NULL,18),
		(6,1,NULL,NULL,2),
	  	(7,0,NULL,NULL,11),
		(8,1,NULL,NULL,7),
		(9,1,NULL,NULL,5),
		(10,0,NULL,NULL,8),
	    (11,1,NULL,NULL,9),
	  	(12,0,NULL,NULL,2),
		(13,1,NULL,NULL,10),
		(14,1,NULL,NULL,20),
		(15,0,NULL,NULL,18),
		(16,1,NULL,NULL,2),
	  	(17,0,NULL,NULL,19),
		(18,1,NULL,NULL,4),
		(19,1,NULL,NULL,17),
		(20,0,NULL,NULL,8),
		(21,1,NULL,NULL,16),
		(22,1,NULL,NULL,13);

-- Tareas
insert ignore into tarea (id, descripcion, estado, area_id, practica_id)
values	(1,"Radiografia de torax","NoAsignada",3,1),
		(2,"Radiografia de brazo","Asignada",3,2),
		(3,"Ecografia hepatica","EnProgreso",2,3),
		(4,"Ecografia pancreatica","EnProgreso",2,4),
		(5,"Resonancia de rodilla","NoAsignada",4,5),
	    (6,"Radiografia de tibia","Asignada",3,6),
		(7,"Radiografia de femur","Asignada",3,7),
	    (8,"Radiografia de Perone","Asignada",3,7),
    	(9,"Radiografia de Nariz","EnProgreso",3,7),
        (10,"Radiografia de Dedo","NoAsignada",3,7),
        (11,"Radiografia de Costilla","Asignada",3,7),
        (12,"Radiografia de Muñeca","Asignada",3,7),
        (13,"Radiografia de Cerebro","Asignada",3,7),
        (14,"Radiografia de Oreja","Asignada",3,7),
        (15,"Radiografia de Algo","NoAsignada",3,7),
        (16,"Radiografia de Mentón","EnProgreso",3,7),
        (17,"Radiografia de Tobillo","Asignada",3,7),
        (18,"Radiografia de Hombro","Asignada",3,7),
        (19,"Radiografia de Rodilla","Asignada",3,7),
        (20,"Radiografia de Rótula","Asignada",3,7),
        (21,"Radiografia de Cráneo","EnProgreso",3,7),
        (22,"Radiografia de Peroné","Asignada",3,7),
        (23,"Radiografia de Húmero","Asignada",3,7),
        (24,"Radiografia de Cúbito","Asignada",3,7);

-- Relacion Tecnico-Tarea
insert ignore into tecnico_tarea (tecnico_id, tarea_id)
values	(2,2),
        (3,4), (3,6),
        (4,7), (4,8),
        (5,9), (5,11),
        (6,12), (6,13),
        (7,14), 
		(8,16), (8,17), (8,18),
		(9,3), (9,19), (9,20), (9,21),
        (10,22), (10,23), (10,24); 

-- Relacion Area-Tarea
insert ignore into area_tarea (area_id, tarea_id)
values	(3,1), (4,5), (3,10), (3,15);

-- Supervisores
insert ignore into supervisor (id, edad, nombre, salario)
values	(1,45,"Nicolas Cabrera",20000),
	(2,56,"Mariano Martinoza",13000),
	(3,135,"Enrique PIPO Cipolatti",10000),
    (4,95,"Claudio Turco Garcia",10000),
    (5,75,"Diego Gambetita Latorre",18000);
    

-- Tabla de relación entre técnicos y supervisores
insert ignore into supervisor_tecnico (supervisor_id, tecnico_id)
values (1,1), (1,2), (2,3), (2,4), (3,5), (3,6), (4,7), (5,8), (5,9), (5,10);


-- Tabla de tipos de Resultados
insert ignore into tipo_resultado(id,descripcion)
values  (1,"Booleano"),(2,"Porcentual"), (3,"Cadena"),(4,"Entero"),(5,"Booleano"), (6,"Entero"),
		(7,"Booleano"),(8,"Cadena"), (9,"Entero"),(10,"Porcentual"),(11,"Booleano"), (12,"Decimal"),
		(13,"Cadena"),(14,"Decimal"), (15,"Decimal"),(16,"Cadena"),(17,"Porcentual"), (18,"Booleano"),
		(19,"Decimal"),(20,"Decimal"), (21,"Porcentual"),(22,"Entero"),(23,"Decimal"), (24,"Decimal"),
		(25,"Entero"),(26,"Porcentual"),(27,"Decimal"),(28,"Booleano"),(29,"Booleano"), (30,"Decimal"),
		(31,"Porcentual"),(32,"Entero");

-- Tabla relacion Prestacion-Tipo Resultado
insert ignore into prestacion_tipo_resultado (prestacion_codigo, parametrosAEvaluar_id, mapkey)
values  (1,1,"Rafalado (S/N)"),
	    (1,2,"Porcentaje de efrulación"),
	    (1,24,"Prolongación colposcópica (cm)"),
	    (2,3,"Observaciones"),
		(2,4,"Cantidad de costillas"),
		(2,25,"Fracturas expuestas detectadas"),
		(2,26,"Porcentaje de simpleza"),
		(3,5,"Falla de corazón (S/N)"), 
		(3,6,"Cantidad de picos"),
		(3,27,"Morbo por pulsación"),
		(4,7,"Posee microcalcificaciones (S/N)"), 
		(5,8,"Observaciones"),
		(5,28,"Vivo (S/N)"),
		(6,9,"Cantidad de problemas"),
	    (7,10,"Porcentaje de genialidad"),
	    (8,11,"Necesita operación (S/N)"),
		(9,12,"Decibeles que escucha"),
		(9,29,"Sordo (S/N)"),
		(10,13,"Color"), 
		(11,14,"Toneladas de impedancia"),
		(12,15,"Millones de glóbulos rojos"), 
		(12,30,"Millones de glóbulos blancos"),
		(12,31,"Hematócrito (%)"),
		(12,32,"Hemoglobina"),
		(13,16,"Bacterias encontradas"),
	    (14,17,"Porcentaje de flogger"),
	    (15,18,"Satisfactorios (S/N)"),
		(16,19,"Kilos zondrobados"),
		(17,20,"Litros suministrados"), 
		(18,21,"Porcentaje de metrosexualidad"),
		(19,22,"Mechones cortados"), 
		(20,23,"Gramos de silicona suministrados");
		