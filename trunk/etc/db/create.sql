CREATE TABLE `area` (
  `id` int(11) NOT NULL auto_increment,
  `nombre` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `prestacion` (
  `codigo` int(11) NOT NULL auto_increment,
  `costo_adicional` double default NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY  (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tipo_resultado` (
  `descripcion` varchar(31) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `prestacion_tipo_resultado` (
  `prestacion_codigo` int(11) NOT NULL,
  `parametrosAEvaluar_id` int(11) NOT NULL,
  `mapkey` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`prestacion_codigo`,`mapkey`),
  UNIQUE KEY `parametrosAEvaluar_id` (`parametrosAEvaluar_id`),
  KEY `FKF67F6321B02DD86E` (`prestacion_codigo`),
  KEY `FKF67F632158C1E23F` (`parametrosAEvaluar_id`),
  CONSTRAINT `FKF67F632158C1E23F` FOREIGN KEY (`parametrosAEvaluar_id`) REFERENCES `tipo_resultado` (`id`),
  CONSTRAINT `FKF67F6321B02DD86E` FOREIGN KEY (`prestacion_codigo`) REFERENCES `prestacion` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `resultado` (
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `resultado_resultados` (
  `resultado_id` int(11) NOT NULL,
  `element` varchar(255) default NULL,
  `mapkey` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`resultado_id`,`mapkey`),
  KEY `FK8B77D9B4D2D43CC7` (`resultado_id`),
  CONSTRAINT `FK8B77D9B4D2D43CC7` FOREIGN KEY (`resultado_id`) REFERENCES `resultado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `practica` (
  `id` int(11) NOT NULL auto_increment,
  `aprobada` bit(1) default NULL,
  `fecha_realizacion` datetime default NULL,
  `fecha_resultado` datetime default NULL,
  `prestacion` int(11) default NULL,
  `practica_resultado` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `practica_prestacion_fk` (`prestacion`),
  KEY `resultado_fk` (`practica_resultado`),
  CONSTRAINT `resultado_fk` FOREIGN KEY (`practica_resultado`) REFERENCES `resultado` (`id`),
  CONSTRAINT `practica_prestacion_fk` FOREIGN KEY (`prestacion`) REFERENCES `prestacion` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tarea` (
  `id` int(11) NOT NULL auto_increment,
  `descripcion` varchar(255) default NULL,
  `estado` varchar(255) default NULL,
  `area_id` int(11) NOT NULL,
  `practica_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `tarea_area_fk` (`area_id`),
  KEY `tarea_practica_fk` (`practica_id`),
  CONSTRAINT `tarea_practica_fk` FOREIGN KEY (`practica_id`) REFERENCES `practica` (`id`),
  CONSTRAINT `tarea_area_fk` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `area_tarea` (
  `area_id` int(11) NOT NULL,
  `tarea_id` int(11) NOT NULL,
  PRIMARY KEY  (`area_id`,`tarea_id`),
  UNIQUE KEY `tarea_id` (`tarea_id`),
  KEY `FK460D4CCFE7A9F43D` (`area_id`),
  KEY `FK460D4CCFC8FB55EA` (`tarea_id`),
  CONSTRAINT `FK460D4CCFC8FB55EA` FOREIGN KEY (`tarea_id`) REFERENCES `tarea` (`id`),
  CONSTRAINT `FK460D4CCFE7A9F43D` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `comportamiento` (
  `descripcion` varchar(31) NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `regla` (
  `id` int(11) NOT NULL auto_increment,
  `nombre` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `regla_compuesta` (
  `id` int(11) NOT NULL,
  `comportamiento_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK14A7BC2B9EDBDCF6` (`id`),
  KEY `regla_compuesta_comportamiento_fk` (`comportamiento_id`),
  CONSTRAINT `regla_compuesta_comportamiento_fk` FOREIGN KEY (`comportamiento_id`) REFERENCES `comportamiento` (`id`),
  CONSTRAINT `FK14A7BC2B9EDBDCF6` FOREIGN KEY (`id`) REFERENCES `regla` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `regla_compuesta_regla` (
  `regla_compuesta_id` int(11) NOT NULL,
  `regla_id` int(11) NOT NULL,
  UNIQUE KEY `regla_id` (`regla_id`),
  KEY `FK5A2903F514A67FF5` (`regla_compuesta_id`),
  KEY `FK5A2903F575D9C7EC` (`regla_id`),
  CONSTRAINT `FK5A2903F575D9C7EC` FOREIGN KEY (`regla_id`) REFERENCES `regla` (`id`),
  CONSTRAINT `FK5A2903F514A67FF5` FOREIGN KEY (`regla_compuesta_id`) REFERENCES `regla_compuesta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `cobertura` (
  `id` int(11) NOT NULL auto_increment,
  `costo_adicional` double default NULL,
  `prestacion_id` int(11) default NULL,
  `regla_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `cobertura_prestacion_fk` (`prestacion_id`),
  KEY `cobertura_regla_fk` (`regla_id`),
  CONSTRAINT `cobertura_regla_fk` FOREIGN KEY (`regla_id`) REFERENCES `regla` (`id`),
  CONSTRAINT `cobertura_prestacion_fk` FOREIGN KEY (`prestacion_id`) REFERENCES `prestacion` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `condicion` (
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKCCA1B2CC9EDBDCF6` (`id`),
  CONSTRAINT `FKCCA1B2CC9EDBDCF6` FOREIGN KEY (`id`) REFERENCES `regla` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `condicion_cantidad` (
  `cantidad_pruebas` int(11) default NULL,
  `frecuencia` int(11) default NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK92ABAB35EE77E5B9` (`id`),
  CONSTRAINT `FK92ABAB35EE77E5B9` FOREIGN KEY (`id`) REFERENCES `condicion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `condicion_carencia` (
  `meses_carencia` int(11) default NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK98ADF031EE77E5B9` (`id`),
  CONSTRAINT `FK98ADF031EE77E5B9` FOREIGN KEY (`id`) REFERENCES `condicion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `condicion_combinacion` (
  `cantidad_practicas` int(11) default NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKC244BF8DEE77E5B9` (`id`),
  CONSTRAINT `FKC244BF8DEE77E5B9` FOREIGN KEY (`id`) REFERENCES `condicion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `condicion_combinacion_lCodigoPractica` (
  `condicion_combinacion_id` int(11) NOT NULL,
  `element` int(11) default NULL,
  KEY `FK608239CA74665803` (`condicion_combinacion_id`),
  CONSTRAINT `FK608239CA74665803` FOREIGN KEY (`condicion_combinacion_id`) REFERENCES `condicion_combinacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `historia_clinica` (
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `historia_clinica_practica` (
  `historia_clinica_id` int(11) NOT NULL,
  `practica_id` int(11) NOT NULL,
  UNIQUE KEY `practica_id` (`practica_id`),
  KEY `FKFCB7FDDFBD5B80D8` (`historia_clinica_id`),
  KEY `FKFCB7FDDF9A3C9E41` (`practica_id`),
  CONSTRAINT `FKFCB7FDDF9A3C9E41` FOREIGN KEY (`practica_id`) REFERENCES `practica` (`id`),
  CONSTRAINT `FKFCB7FDDFBD5B80D8` FOREIGN KEY (`historia_clinica_id`) REFERENCES `historia_clinica` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `plan` (
  `codigo` int(11) NOT NULL auto_increment,
  `costo_mensual` float default NULL,
  `descripcion` varchar(255) default NULL,
  PRIMARY KEY  (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `plan_cobertura` (
  `plan_id` int(11) NOT NULL,
  `cobertura_id` int(11) NOT NULL,
  UNIQUE KEY `cobertura_id` (`cobertura_id`),
  KEY `plan_fk` (`plan_id`),
  KEY `cobertura_fk` (`cobertura_id`),
  CONSTRAINT `cobertura_fk` FOREIGN KEY (`cobertura_id`) REFERENCES `cobertura` (`id`),
  CONSTRAINT `plan_fk` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `paciente` (
  `id` int(11) NOT NULL auto_increment,
  `apellido` varchar(255) default NULL,
  `fecha_ingreso` datetime default NULL,
  `nombre` varchar(255) default NULL,
  `numero_documento` varchar(255) default NULL,
  `sexo` int(11) default NULL,
  `historia_clinica_id` int(11) default NULL,
  `plan_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `paciente_plan_fk` (`plan_id`),
  KEY `paciente_historia_clinia_fk` (`historia_clinica_id`),
  CONSTRAINT `paciente_historia_clinia_fk` FOREIGN KEY (`historia_clinica_id`) REFERENCES `historia_clinica` (`id`),
  CONSTRAINT `paciente_plan_fk` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `medico` (
  `id` int(11) NOT NULL auto_increment,
  `edad` int(11) default NULL,
  `especialidad` varchar(255) default NULL,
  `nombre` varchar(255) default NULL,
  `sexo` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `orden_medica` (
  `id` int(11) NOT NULL auto_increment,
  `aprobada` bit(1) default NULL,
  `fecha_emision` datetime default NULL,
  `medico_emisor_id` int(11) default NULL,
  `paciente_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `orden_medica_medico_fk` (`medico_emisor_id`),
  KEY `orden_medica_paciente_fk` (`paciente_id`),
  CONSTRAINT `orden_medica_paciente_fk` FOREIGN KEY (`paciente_id`) REFERENCES `paciente` (`id`),
  CONSTRAINT `orden_medica_medico_fk` FOREIGN KEY (`medico_emisor_id`) REFERENCES `medico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `orden_medica_practica` (
  `orden_medica_id` int(11) NOT NULL,
  `practica_id` int(11) NOT NULL,
  UNIQUE KEY `practica_id` (`practica_id`),
  KEY `FK667AE0C68DE048D9` (`orden_medica_id`),
  KEY `FK667AE0C69A3C9E41` (`practica_id`),
  CONSTRAINT `FK667AE0C69A3C9E41` FOREIGN KEY (`practica_id`) REFERENCES `practica` (`id`),
  CONSTRAINT `FK667AE0C68DE048D9` FOREIGN KEY (`orden_medica_id`) REFERENCES `orden_medica` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tecnico` (
  `id` int(11) NOT NULL auto_increment,
  `edad` int(11) default NULL,
  `nombre` varchar(255) NOT NULL,
  `salario` double default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tecnico_area` (
  `tecnico_id` int(11) NOT NULL,
  `area_id` int(11) NOT NULL,
  PRIMARY KEY  (`tecnico_id`,`area_id`),
  KEY `FK3BBEAE93E7A9F43D` (`area_id`),
  KEY `FK3BBEAE935C3AC297` (`tecnico_id`),
  CONSTRAINT `FK3BBEAE935C3AC297` FOREIGN KEY (`tecnico_id`) REFERENCES `tecnico` (`id`),
  CONSTRAINT `FK3BBEAE93E7A9F43D` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tecnico_tarea` (
  `tecnico_id` int(11) NOT NULL,
  `tarea_id` int(11) NOT NULL,
  PRIMARY KEY  (`tecnico_id`,`tarea_id`),
  KEY `FK3D1B59BB5C3AC297` (`tecnico_id`),
  KEY `FK3D1B59BBC8FB55EA` (`tarea_id`),
  CONSTRAINT `FK3D1B59BBC8FB55EA` FOREIGN KEY (`tarea_id`) REFERENCES `tarea` (`id`),
  CONSTRAINT `FK3D1B59BB5C3AC297` FOREIGN KEY (`tecnico_id`) REFERENCES `tecnico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `supervisor` (
  `id` int(11) NOT NULL auto_increment,
  `edad` int(11) default NULL,
  `nombre` varchar(255) default NULL,
  `salario` double default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `supervisor_tecnico` (
  `supervisor_id` int(11) NOT NULL,
  `tecnico_id` int(11) NOT NULL,
  PRIMARY KEY  (`supervisor_id`,`tecnico_id`),
  UNIQUE KEY `tecnico_id` (`tecnico_id`),
  KEY `FK78310A25C3AC297` (`tecnico_id`),
  KEY `FK78310A2FB8B3C1D` (`supervisor_id`),
  CONSTRAINT `FK78310A2FB8B3C1D` FOREIGN KEY (`supervisor_id`) REFERENCES `supervisor` (`id`),
  CONSTRAINT `FK78310A25C3AC297` FOREIGN KEY (`tecnico_id`) REFERENCES `tecnico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
