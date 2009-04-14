insert into tipo_resultado(id,descripcion) values (40, "Booleano"),(41, "Cadena");

-- Agregar nuevos parametros a evaluar a la prestacion "Analisis de Sangre" (codigo 12)
insert into prestacion_tipo_resultado (prestacion_codigo, parametrosAEvaluar_id, mapkey) values
(12,40,"algo booleano"), (12,41,"cualquier cosa");