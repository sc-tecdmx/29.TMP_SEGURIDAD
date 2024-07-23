CREATE TABLE `jel_renapo_curps` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `fecha_consulta` datetime,
  `respuesta_renapo` varchar(12),
  `respuesta_firmada` varchar(64)
);

CREATE TABLE `tab_cat_destino_documento` (
  `n_id_tipo_destino` int PRIMARY KEY AUTO_INCREMENT,
  `desc_destino_documento` varchar(50)
);

CREATE TABLE `tab_cat_tipo_documento` (
  `n_id_tipo_documento` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_cat_area` int,
  `desc_tipo_documento` varchar(200)
);

CREATE TABLE `tab_cat_prioridad` (
  `n_id_prioridad` int PRIMARY KEY AUTO_INCREMENT,
  `desc_prioridad` varchar(30)
);

CREATE TABLE `tab_cat_tipo_notificacion` (
  `n_id_tipo_notif` int PRIMARY KEY AUTO_INCREMENT,
  `desc_tipo` varchar(30),
  `icon_tipo_notif` varchar(128)
);

CREATE TABLE `tab_notificaciones` (
  `n_id_notificacion` int PRIMARY KEY AUTO_INCREMENT,
  `id_document` int,
  `documento_path` varchar(255),
  `message` varchar(30),
  `n_id_tipo_notif` int
);

CREATE TABLE `tab_expedientes` (
  `n_num_expediente` int PRIMARY KEY AUTO_INCREMENT,
  `s_num_expediente` varchar(100) UNIQUE,
  `s_descripcion` varchar(255),
  `n_id_usuario_creador` int COMMENT 'Creador del Registro.'
);

CREATE TABLE `tab_documentos` (
  `n_id_documento` int PRIMARY KEY AUTO_INCREMENT,
  `chain_id_document` int,
  `n_id_tipo_destino` int,
  `n_id_tipo_documento` int,
  `folio_documento` int,
  `folio_especial` varchar(50),
  `creacion_documento_fecha` datetime,
  `n_id_num_empleado_creador` int,
  `n_id_usuario_creador` int COMMENT 'Creador del documento.',
  `n_num_expediente` int COMMENT 'El nombre se almacenará en la tabla tab_expedientes',
  `n_id_prioridad` int,
  `n_en_orden` int(1),
  `s_asunto` varchar(255),
  `s_notas` varchar(1000),
  `s_contenido` varchar(2048),
  `d_fecha_limite_firma` datetime,
  `s_hash_documento` varchar(64), /*Debería eliminarse este registro*/
  `visible` tinyint(1) NOT NULL DEFAULT '1'
);

CREATE TABLE `tab_documentos_adjuntos` (
  `id_documento_adjunto` int PRIMARY KEY AUTO_INCREMENT,
  `id_document` int,
  `documento_path` varchar(255),
  `documento_hash` varchar(64),
  `documento_filetype` varchar(20),
  `documento_base64` LONGTEXT,
  `fecha_carga` datetime
);

CREATE TABLE `tab_cat_inst_firmantes` (
  `n_id_inst_firmante` int PRIMARY KEY AUTO_INCREMENT,
  `desc_instr_firmante` varchar(20)
);

CREATE TABLE `tab_docs_firmantes` (
  `n_id_documento` int,
  `n_id_num_empleado` int,
  `n_id_inst_firmante` int,
  `secuencia` int(2) COMMENT ' Posición de la secuencia',
  PRIMARY KEY (`n_id_documento`, `n_id_num_empleado`)
);

CREATE TABLE `tab_cat_inst_dest` (
  `n_id_inst_dest` int PRIMARY KEY AUTO_INCREMENT,
  `desc_inst_dest` varchar(20)
);

CREATE TABLE `tab_doc_destinatarios` (
  `n_id_documento` int,
  `n_id_num_empleado` int,
  `n_id_inst_dest` int,
  PRIMARY KEY (`n_id_documento`, `n_id_num_empleado`)
);

CREATE TABLE `tab_cat_etapa_documento` (
  `id_etapa_documento` int PRIMARY KEY AUTO_INCREMENT,
  `s_desc_etapa` varchar(40)
);

CREATE TABLE `tab_documento_workflow` (
  `id_documento_workflow` int PRIMARY KEY AUTO_INCREMENT,
  `id_etapa_documento` int,
  `id_document` int,
  `ult_actualizacion` datetime,
  `workflow_fecha` datetime,
  `workflow_n_id_num_empleado` int
);

CREATE TABLE `tab_cat_doc_config` (
  `n_id_doc_config` int PRIMARY KEY AUTO_INCREMENT,
  `s_atributo` varchar(12),
  `s_valor` varchar(127)
);

CREATE TABLE `tab_doc_config` (
  `n_id_documento` int,
  `n_id_doc_config` int,
  PRIMARY KEY (`n_id_documento`, `n_id_doc_config`)
);

CREATE TABLE `pki_x509_ac_autorizadas` (
  `s_x509_emisor_serial` varchar(60) PRIMARY KEY,
  `s_x509_ac_der_b64` varchar(5125),
  `s_x509_emisor_autoridad` varchar(256),
  `s_tipo_certificado` varchar(10) COMMENT 'Tipo de certificado (OCSP, INTERMEDIO, RAIZ)',
  `s_url` varchar(255),
  `s_x509_emisor_serial_parent` varchar(60)
);

CREATE TABLE `pki_x509_registrados` (
  `s_x509_serial_number` varchar(60) PRIMARY KEY,
  `s_x509_der_b64` varchar(5125),
  `s_x509_sha256_cert` varchar(64),
  `s_x509_emisor_serial` varchar(60),
  `s_x509_subject` varchar(512),
  `s_x509_rfc` varchar(13),
  `s_x509_curp` varchar(18),
  `s_x509_nombre` varchar(50) NOT NULL,
  `s_x509_apellido1` varchar(50) NOT NULL,
  `s_x509_apellido2` varchar(50),
  `s_sha256_registro` varchar(64),
  `s_token_vigencia` varchar(64),
  `d_fecha_registro` datetime,
  `d_fecha_revocacion` datetime
);

CREATE TABLE `pki_usuarios_cert` (
  `n_id_usuario_firma` int,
  `s_x509_serial_number` varchar(60),
  `s_curp` varchar(20),
  `s_rfc` varchar(14),
  `s_sha256_registro` varchar(64),
  PRIMARY KEY (`n_id_usuario_firma`, `s_x509_serial_number`)
);

CREATE TABLE `pki_log_usuarios_cert` (
  `id_log_usuarios_cert` int PRIMARY KEY AUTO_INCREMENT,
  `s_curp` varchar(20),
  `s_x509_serial_number` varchar(60),
  `s_bitacora` varchar(1024),
  `s_sha256_registro` varchar(64)
);

CREATE TABLE `pki_transaccion` (
  `n_id_transaccion` int PRIMARY KEY AUTO_INCREMENT,
  `s_request_uuid_filehash` varchar(32),
  `s_x509_serial_number` varchar(60) NOT NULL,
  `s_uuid_ocsp` varchar(36),
  `s_uuid_tsp` varchar(36),
  `s_cadena_firma` varchar(512),
  `s_request_uuid_filename` varchar(255),
  `s_clob_json_request` text,
  `n_id_transaccion_block` int
);

CREATE TABLE `pki_documento` (
  `s_hash_documento` varchar(64) PRIMARY KEY,
  `n_id_num_empleado_creador` int,
  `fecha_creacion` datetime,
  `n_id_num_empleado_envio` int,
  `fecha_envio` datetime,
  `s_algoritmo` varchar(100),
  `status_documento` varchar(20),
  `n_en_orden` int(1),
  `terminado` int(1)
);

CREATE TABLE `pki_documento_firmantes` (
  `s_hash_documento` varchar(64),
  `n_id_usuario` int COMMENT 'El id usuario será parte de la llave, ya que pueden firmar externos',
  `n_id_transaccion` int,
  `n_id_num_empleado` int,
  `secuencia` int(2) COMMENT ' Posición de la secuencia',
  `fecha_limite` datetime,
  `fecha_firma` datetime,
  `id_tipo_firma` int,
  `id_firma_aplicada` int,
  `s_cadena_firma` varchar(1000) COMMENT 'Greys, confirmar para que se requiere este campo',
  `s_descripcion` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`s_hash_documento`, `n_id_usuario`)
);

CREATE TABLE `pki_cat_tipo_firma` (
  `id_tipo_firma` int PRIMARY KEY AUTO_INCREMENT,
  `desc_tipo_firma` varchar(20) COMMENT 'Graciela, validar la longitud de esta etiqueta'
);

CREATE TABLE `pki_cat_firma_aplicada` (
  `id_firma_aplicada` int PRIMARY KEY AUTO_INCREMENT,
  `desc_firma_aplicada` varchar(30)
);

CREATE TABLE `pki_documento_destinos` (
  `s_hash_documento` varchar(64),
  `n_id_usuario` int,
  `n_id_transaccion` int,
  `n_id_num_empleado` int,
  `id_instruccion_doc` int,
  `fecha_notificacion` datetime,
  `fecha_lectura` datetime,
  `fecha_acuse` datetime,
  `id_firma_aplicada` int,
  `s_descripcion` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`s_hash_documento`, `n_id_usuario`, `n_id_transaccion`)
);

CREATE TABLE `pki_cat_instruccion_doc` (
  `id_instruccion_doc` int PRIMARY KEY AUTO_INCREMENT,
  `desc_instruccion_doc` varchar(30)
);

CREATE TABLE `pki_x509_ocsp` (
  `s_uuid_ocsp` varchar(36) PRIMARY KEY,
  `s_x509_serial_number` varchar(60),
  `s_ocsp_response_der_b64` varchar(4096),
  `s_ocsp_response_path` varchar(255),
  `s_x509_serial_responder` varchar(60),
  `d_fecha_response` datetime,
  `s_ocsp_indicador` varchar(8),
  `s_uuid_ocsp_block` varchar(36)
);

CREATE TABLE `pki_x509_tsp` (
  `s_uuid_tsp` varchar(36) PRIMARY KEY,
  `s_x509_serial_number` varchar(60),
  `s_tsp_response_der_b64` varchar(5125),
  `s_tsp_response_path` varchar(255),
  `s_x509_serial_stamper` varchar(60),
  `d_fecha_response` datetime,
  `s_tsp_indicador` varchar(8),
  `s_uuid_tsp_block` varchar(36)
);

CREATE TABLE `pki_x509_documento_certificado` (
  `n_id_documento_certificado` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_certificado_x509` varchar(20),
  `s_path_documento` varchar(255)
);

CREATE TABLE `pki_x509_jel_autorizacion` (
  `n_id_jel_autorizacion` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_certificado_x509` varchar(20),
  `s_expediente` varchar(80),
  `s_revocado` varchar(80),
  `s_token_vigencia` varchar(64)
);

CREATE TABLE `inst_u_adscripcion_detalle` (
  `n_id_u_adscripcion_detalle` int PRIMARY KEY AUTO_INCREMENT,
  `s_desc_unidad` varchar(255),
  `s_abrev_unidad` varchar(15)
);

CREATE TABLE `inst_cat_areas` (
  `n_id_cat_area` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_u_adscripcion_detalle` int,
  `s_desc_area` varchar(100),
  `s_abrev_area` varchar(15) UNIQUE,
  `n_id_cat_area_padre` int
);

CREATE TABLE `inst_cat_sexo` (
  `id_sexo` int PRIMARY KEY AUTO_INCREMENT,
  `sexo` varchar(1),
  `sexo_desc` varchar(20)
);

CREATE TABLE `inst_cat_puestos` (
  `n_id_cat_puesto` int PRIMARY KEY AUTO_INCREMENT,
  `s_desc_nombramiento` varchar(100),
  `n_tipo_usuario` varchar(1) COMMENT 'J - Jurisdiccional, A - Administrativo'
);

CREATE TABLE `inst_empleado` (
  `n_id_num_empleado` int PRIMARY KEY,
  `nombre` varchar(50),
  `apellido1` varchar(50),
  `apellido2` varchar(50),
  `id_sexo` int,
  `s_email_pers` varchar(256) UNIQUE,
  `s_email_inst` varchar(256) UNIQUE,
  `tel_pers` varchar(10),
  `tel_inst` varchar(10),
  `curp` varchar(18),
  `rfc` varchar(13),
  `path_fotografia` varchar(256),
  `n_id_usuario` int,
  `activo` tinyint(1) NOT NULL DEFAULT '1'
);

CREATE TABLE `inst_empleado_puesto_area` (
  `n_id_empleado_puesto_area` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_num_empleado` int,
  `n_id_cat_area` int,
  `n_id_cat_puesto` int,
  `fecha_alta` date,
  `fecha_conclusion` date,
  `n_tipo_estructura` varchar(1) COMMENT 'O - Orgánica, F - Funcional, si es orgánica es porque así aparece en el directorio, funcional es cuando sus funciones son otras',
  `activo` tinyint(1) NOT NULL DEFAULT '1'
);

CREATE TABLE `inst_titular_u_adscripcion` (
  `n_id_titular_area` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_u_adscripcion_detalle` int,
  `n_id_empleado_puesto_area` int,
  `fecha_inicio` date,
  `fecha_conclusion` date
);

CREATE TABLE `inst_log_empleado` (
  `n_id_log_empleado` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_num_empleado` int,
  `bitacora` varchar(1024) COMMENT 'Registrar aquí todos los campos en la información del empleado en formato JSON',
  `n_session_id` int
);

CREATE TABLE `seg_cat_estado_usuario` (
  `n_id_estado_usuario` int PRIMARY KEY AUTO_INCREMENT,
  `s_descripcion` varchar(255)
);

CREATE TABLE `seg_cat_nivel_modulo` (
  `n_id_nivel` int(2) PRIMARY KEY AUTO_INCREMENT,
  `desc_nivel` varchar(20)
);

CREATE TABLE `seg_usuario_estado_usuario` (
  `n_id_usuario_status` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_usuario` int,
  `n_id_estado_usuario` int,
  `fingerprint_dispositivo` varchar(255),
  `d_fecha_status` datetime,
  `n_session_id` int
);

CREATE TABLE `seg_usuarios` (
  `n_id_usuario` int PRIMARY KEY AUTO_INCREMENT,
  `s_usuario` varchar(20),
  `s_contrasenia` varchar(255),
  `s_desc_usuario` varchar(100),
  `s_email` varchar(256) UNIQUE,
  `n_id_estado_usuario` int,
  `s_token` varchar(255)
);

CREATE TABLE `seg_roles_usuarios` (
  `n_id_rol_usuario` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_rol` int,
  `n_id_usuario` int,
  `n_id_empleado_puesto_area` int,
  `n_session_id` int
);

CREATE TABLE `seg_roles` (
  `n_id_rol` int PRIMARY KEY AUTO_INCREMENT,
  `s_etiqueta_rol` varchar(15) UNIQUE,
  `s_descripcion` varchar(40),
  `n_id_rol_padre` int,
  `n_rec_activo` int(1),
  `n_session_id` int
);

CREATE TABLE `seg_roles_modulos` (
  `n_id_rol` int,
  `n_id_modulo` int,
  `crear` varchar(1) COMMENT 'S- Si, N-No, Null-No',
  `leer` varchar(1),
  `editar` varchar(1),
  `eliminar` varchar(1),
  `publico` varchar(1) COMMENT 'S- Si, se mostrará al publico sin autenticar',
  `n_session_id` int COMMENT 'Guardar la sesión que modificó el registro',
  PRIMARY KEY (`n_id_rol`, `n_id_modulo`)
);

CREATE TABLE `seg_usuarios_modulos` (
  `n_id_usuario` int,
  `n_id_modulo` int,
  `d_fecha_alta` datetime,
  `d_fecha_baja` datetime,
  `status` varchar(20),
  `n_session_id` int COMMENT 'Guardar la sesión que modificó el registro',
  PRIMARY KEY (`n_id_usuario`, `n_id_modulo`)
);

CREATE TABLE `seg_modulos` (
  `n_id_modulo` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_nivel` int(2),
  `codigo` varchar(10) UNIQUE,
  `desc_modulo` varchar(100),
  `n_id_modulo_padre` int,
  `menu` varchar(1) COMMENT 'S- Si, forma parte del menú',
  `menu_desc` varchar(60),
  `menu_url` varchar(60),
  `menu_pos` int(3) COMMENT 'Sirve para presentar la posición del menú'
);

CREATE TABLE `seg_log_sesion` (
  `n_session_id` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_usuario` int,
  `d_fecha_inicio` timestamp,
  `d_fecha_fin` timestamp COMMENT 'El usuario realizo un logout',
  `n_end_sesion` bigint COMMENT 'Fin de la sesión en milisegundos',
  `chain_n_session_id` int
);

CREATE TABLE `seg_log_sistema` (
  `id_log_sistema` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_usuario_org` int COMMENT ' Solo para usuarios de organización',
  `n_id_usuario_jel` int COMMENT ' Solo para usuarios JEL',
  `d_sistema` datetime,
  `bitacora` varchar(1024)
);

CREATE TABLE `seg_log_usuario` (
  `id_log_usuario` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_usuario` int,
  `d_sistema` datetime,
  `n_session_id` int COMMENT 'Guardar la sesión que modificó el registro',
  `bitacora` varchar(1024)
);


/*Actualización 5/dic-2023*/
CREATE TABLE `tab_doc_grupos_firmas` (
  `n_id_grupo_firmas` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_cat_area` int,
  `c_tipo_grupo` varchar(20), /*FIRMANTE, DESTINATARIO*/
  `s_nombre_gpo_firmante` varchar(30)
);

CREATE TABLE `tab_doc_grupo_firmas_personas` (
  `n_id_grupo_personas` int,
  `n_id_num_empleado` int,
  `n_id_inst_firmante` int,
  `n_id_inst_destinatario` int,
  PRIMARY KEY (`n_id_grupo_personas`, `n_id_num_empleado`)
);
/*End actualización 5/dic-2023*/


/*-------->ADECUACIÓN 23 JULIO 2024*/

CREATE TABLE `jel_cat_entidad_federativa` (
  `n_id_entidad_federativa` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);

CREATE TABLE `jel_cat_tipo_medio` (
  `n_id_tipo_medio` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);

CREATE TABLE `jel_cat_autoridad_responsable` (/*En el sistema se relacionan con la entidad federativa ya que es multi tribunal, en este vale la pena hacerlo igual???*/
  `id_autoridad_responsable` int PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE `jel_cat_tipo_expediente` (
  `n_id_tipo_expediente` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);

CREATE TABLE `jel_cat_involucrados` (
  `n_id_involucrados` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);

/*Aqui se almacenan:
- correo electrónico
- oficialía de partes
- fax
- juicio en linea
*/
CREATE TABLE `jel_cat_tipo_recepcion` (
  `n_id_tipo_recepcion` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);

CREATE TABLE `jel_cat_tipo_eleccion` (
  `n_id_tipo_eleccion` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);

/*Aqui se almacenan:
- n_id_demanda
- n_id_documento_original
- n_id_documento_firmado
- pruebas
- comprobante_representante
*/
CREATE TABLE `jel_cat_tipo_documento_electronico` (
  `n_id_tipo_documento_electronico` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(100)
);


CREATE TABLE `jel_ciudadano` (
  `n_id_ciudadano` int PRIMARY KEY AUTO_INCREMENT,
  `n_id_usuario` int,
  `s_curp` varchar(20),
  `s_rfc` varchar(12),
  `nombre` varchar(70),
  `apellido1` varchar(70),
  `apellido2` varchar(70),
  `email` varchar(256),
  `fh_nac` datetime,
  `sexo` varchar(1),
  `telefono` varchar(10),
  `calle_numero` varchar(100),
  `colonia` varchar(256),
  `cp` varchar(256),
  `estado` varchar(256),
  `ciudad` varchar(256),
  `tipo_identificacion` int(2),
  `aceptacion_terminos_uso` int(1)
);

/*
- Guardado
- Enviado
*/
CREATE TABLE `jel_cat_estatus_medio_impugnacion` (
  `n_id_estatus_medio_impugnacion` int PRIMARY KEY AUTO_INCREMENT,
  `desc_estatus` varchar(100),
  `codigo_estatus` varchar(100)
);


/*Qué es el aviso de Interposición*/


CREATE TABLE `jel_medio_impugnacion` (/*Demandas, interposición, impugnación, cual es la diferenca */
    `n_id_medio_impugnacion` int PRIMARY KEY AUTO_INCREMENT,
    /*PRESENTACIÓN DE DEMANDA a nombre propio o en representación de otra persona*/
    `b_jel_en_representacion` int(1),
    `s_jel_promovente` varchar(100),
    `s_jel_representa` varchar(100),

    `n_id_entidad_federativa` int, /*Entidad, nota: en el video aparece como tribunal del estado x*/
    `n_id_tipo_medio` int,

    `s_desc_acto_impugnado` varchar(100),

    /*DEMANDA*/
    `b_forma_presentacion_demanda`int(1),/* 1 -> Cargar archivo, 0 -> generar documento*/
    /*si se genera el documento se llenan los siguientes campos de la plantilla*/
    `s_jel_hechos` varchar(100),
    `s_jel_agravios` varchar(100),
    `s_jel_normas` varchar(100),/*Este campo es opcional*/

    `n_id_estatus_medio_impugnacion` int,

    `n_folio` int,

    /*El número de expediente se asocia en el sistema de SISGAE*/
    `s_num_expediente` varchar(100),/*-----> No lo vi en el sys, pero lo estoy usando como Expediente Asignado */

    /*OTROS CAMPOS QUE NO APARECEN EN JEL PERO QUE SE REGISTRAN MANUALMENTE, EN EL MÓDULO DE AVISO DE INTERPOSICIÓN*/
    `clave_tribunal` varchar(100),
    `n_anio` int(4),
    `n_id_tipo_expediente` int,
    `consecutivo_aviso` int,
    `consecutivo_expediente` int,
    /*Recepción*/
    `fecha_recepcion` datetime,
    `hora_recepcion` datetime,
    `n_id_tipo_recepcion` int,
    `n_id_tipo_eleccion` int,
    /*INVOLUCRADOS --> Es una relación muchos a muchos*/
    /*DATOS DE REMISIÓN*/
    `cargo_remite` varchar(200),
    `numero_oficio` int,
    `persona_suscribe` varchar(100),
    `fecha_oficio` datetime,
    `observaciones` varchar(100),

    `d_fecha_interposicion` varchar(100),

    /*PRUEBAS*/
    `b_pruebas` int(1),/*Si hay pruebas se agregan en la tabla transitiva jel_medio_impugnacion_pruebas*/

    /*SOLICITUD*/
    `b_firmado` int(1),/*Si ya fue firmado el escrito o no, NO ENTIENDO SI LO QUE SE FIRMA ES EL DOCUMENTO DE DEMANDA O LA SOLICITUD, esto es importante porque si es el escrityo generado de demanda se debe firmar, pero si el que carga la demanda debe firmarlo, entonces ya no hay que firmar la solciitud o en caso contrario siempre se firma la solicitud*/
    /*EN caso de que se firme la solicitud entonces aplicarian los campos de abajo*/
    
    `num_consecutivo` int,
    `tipo_derecho` varchar(256),
    `b_asignado`varchar(100),
    `n_id_tema` int,
    `acciones` varchar(100)

);

/*Aqui se almacenan:
- n_id_demanda
- n_id_documento_original => Esta es la solicitud, el documento autogenerado
- n_id_documento_firmado => Esta es la solicitud firmada, el documento autogenerado
- pruebas
- comprobante_representante
*/
CREATE TABLE `jel_expediente` (/*Antes jel_documentos*/
`n_id_expediente` int PRIMARY KEY AUTO_INCREMENT,
`n_id_medio_impugnacion` int,
`n_id_tipo_documento_electronico` int,
`s_path_documento` varchar(100),
`d_fecha_creacion` datetime
);


CREATE TABLE `jel_impugnacion_autoridad_responsables` (
`n_id` int PRIMARY KEY AUTO_INCREMENT,
`n_id_medio_impugnacion` int,
`id_autoridad_responsable` int,
`otra_autoridad_responsable` int/*Duda, cuando sea otra autoridad responsable puede ser escrito libre o solo desde un catálogo*/
);

CREATE TABLE `jel_medio_impugnacion_involucrados` (
`n_id_medio_impugnacion_involucrados` int PRIMARY KEY AUTO_INCREMENT,
`n_id_medio_impugnacion` int,
`n_id_involucrados` int,
`nombre_ciudadano` varchar(100),
`sexo` varchar(1) /*M, F*/
);

/*-------->END ADECUACIÓN 23 JULIO 2024*/


ALTER TABLE `tab_cat_tipo_documento` ADD FOREIGN KEY (`n_id_cat_area`) REFERENCES `inst_cat_areas` (`n_id_cat_area`);

ALTER TABLE `tab_notificaciones` ADD FOREIGN KEY (`id_document`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_notificaciones` ADD FOREIGN KEY (`n_id_tipo_notif`) REFERENCES `tab_cat_tipo_notificacion` (`n_id_tipo_notif`);

ALTER TABLE `tab_expedientes` ADD FOREIGN KEY (`n_id_usuario_creador`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`chain_id_document`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`n_id_tipo_destino`) REFERENCES `tab_cat_destino_documento` (`n_id_tipo_destino`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`n_id_tipo_documento`) REFERENCES `tab_cat_tipo_documento` (`n_id_tipo_documento`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`n_id_num_empleado_creador`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`n_id_usuario_creador`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`n_num_expediente`) REFERENCES `tab_expedientes` (`n_num_expediente`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`n_id_prioridad`) REFERENCES `tab_cat_prioridad` (`n_id_prioridad`);

ALTER TABLE `tab_documentos` ADD FOREIGN KEY (`s_hash_documento`) REFERENCES `pki_documento` (`s_hash_documento`);

ALTER TABLE `tab_documentos_adjuntos` ADD FOREIGN KEY (`id_document`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_docs_firmantes` ADD FOREIGN KEY (`n_id_documento`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_docs_firmantes` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `tab_docs_firmantes` ADD FOREIGN KEY (`n_id_inst_firmante`) REFERENCES `tab_cat_inst_firmantes` (`n_id_inst_firmante`);

ALTER TABLE `tab_doc_destinatarios` ADD FOREIGN KEY (`n_id_documento`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_doc_destinatarios` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `tab_doc_destinatarios` ADD FOREIGN KEY (`n_id_inst_dest`) REFERENCES `tab_cat_inst_dest` (`n_id_inst_dest`);

ALTER TABLE `tab_documento_workflow` ADD FOREIGN KEY (`id_etapa_documento`) REFERENCES `tab_cat_etapa_documento` (`id_etapa_documento`);

ALTER TABLE `tab_documento_workflow` ADD FOREIGN KEY (`id_document`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_documento_workflow` ADD FOREIGN KEY (`workflow_n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `tab_doc_config` ADD FOREIGN KEY (`n_id_documento`) REFERENCES `tab_documentos` (`n_id_documento`);

ALTER TABLE `tab_doc_config` ADD FOREIGN KEY (`n_id_doc_config`) REFERENCES `tab_cat_doc_config` (`n_id_doc_config`);

ALTER TABLE `pki_x509_ac_autorizadas` ADD FOREIGN KEY (`s_x509_emisor_serial_parent`) REFERENCES `pki_x509_ac_autorizadas` (`s_x509_emisor_serial`);

ALTER TABLE `pki_x509_registrados` ADD FOREIGN KEY (`s_x509_emisor_serial`) REFERENCES `pki_x509_ac_autorizadas` (`s_x509_emisor_serial`);

ALTER TABLE `pki_usuarios_cert` ADD FOREIGN KEY (`n_id_usuario_firma`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `pki_usuarios_cert` ADD FOREIGN KEY (`s_x509_serial_number`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `pki_log_usuarios_cert` ADD FOREIGN KEY (`s_x509_serial_number`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `pki_transaccion` ADD FOREIGN KEY (`s_x509_serial_number`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `pki_transaccion` ADD FOREIGN KEY (`s_uuid_ocsp`) REFERENCES `pki_x509_ocsp` (`s_uuid_ocsp`);

ALTER TABLE `pki_transaccion` ADD FOREIGN KEY (`s_uuid_tsp`) REFERENCES `pki_x509_tsp` (`s_uuid_tsp`);

ALTER TABLE `pki_transaccion` ADD FOREIGN KEY (`n_id_transaccion_block`) REFERENCES `pki_transaccion` (`n_id_transaccion`);

ALTER TABLE `pki_documento` ADD FOREIGN KEY (`n_id_num_empleado_creador`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `pki_documento` ADD FOREIGN KEY (`n_id_num_empleado_envio`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `pki_documento_firmantes` ADD FOREIGN KEY (`s_hash_documento`) REFERENCES `pki_documento` (`s_hash_documento`);

ALTER TABLE `pki_documento_firmantes` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `pki_documento_firmantes` ADD FOREIGN KEY (`n_id_transaccion`) REFERENCES `pki_transaccion` (`n_id_transaccion`);

ALTER TABLE `pki_documento_firmantes` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `pki_documento_firmantes` ADD FOREIGN KEY (`id_tipo_firma`) REFERENCES `pki_cat_tipo_firma` (`id_tipo_firma`);

ALTER TABLE `pki_documento_firmantes` ADD FOREIGN KEY (`id_firma_aplicada`) REFERENCES `pki_cat_firma_aplicada` (`id_firma_aplicada`);

ALTER TABLE `pki_documento_destinos` ADD FOREIGN KEY (`s_hash_documento`) REFERENCES `pki_documento` (`s_hash_documento`);

ALTER TABLE `pki_documento_destinos` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `pki_documento_destinos` ADD FOREIGN KEY (`n_id_transaccion`) REFERENCES `pki_transaccion` (`n_id_transaccion`);

ALTER TABLE `pki_documento_destinos` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `pki_documento_destinos` ADD FOREIGN KEY (`id_instruccion_doc`) REFERENCES `pki_cat_instruccion_doc` (`id_instruccion_doc`);

ALTER TABLE `pki_documento_destinos` ADD FOREIGN KEY (`id_firma_aplicada`) REFERENCES `pki_cat_firma_aplicada` (`id_firma_aplicada`);

ALTER TABLE `pki_x509_ocsp` ADD FOREIGN KEY (`s_x509_serial_number`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `pki_x509_ocsp` ADD FOREIGN KEY (`s_uuid_ocsp_block`) REFERENCES `pki_x509_ocsp` (`s_uuid_ocsp`);

ALTER TABLE `pki_x509_tsp` ADD FOREIGN KEY (`s_x509_serial_number`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `pki_x509_tsp` ADD FOREIGN KEY (`s_uuid_tsp_block`) REFERENCES `pki_x509_tsp` (`s_uuid_tsp`);

ALTER TABLE `pki_x509_documento_certificado` ADD FOREIGN KEY (`n_id_certificado_x509`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `pki_x509_jel_autorizacion` ADD FOREIGN KEY (`n_id_certificado_x509`) REFERENCES `pki_x509_registrados` (`s_x509_serial_number`);

ALTER TABLE `inst_cat_areas` ADD FOREIGN KEY (`n_id_u_adscripcion_detalle`) REFERENCES `inst_u_adscripcion_detalle` (`n_id_u_adscripcion_detalle`);

ALTER TABLE `inst_cat_areas` ADD FOREIGN KEY (`n_id_cat_area_padre`) REFERENCES `inst_cat_areas` (`n_id_cat_area`);

ALTER TABLE `inst_titular_u_adscripcion` ADD FOREIGN KEY (`n_id_u_adscripcion_detalle`) REFERENCES `inst_u_adscripcion_detalle` (`n_id_u_adscripcion_detalle`);

ALTER TABLE `inst_titular_u_adscripcion` ADD FOREIGN KEY (`n_id_empleado_puesto_area`) REFERENCES `inst_empleado_puesto_area` (`n_id_empleado_puesto_area`);
/*
ALTER TABLE `seg_roles_usuarios` ADD FOREIGN KEY (`n_id_empleado_puesto_area`) REFERENCES `inst_empleado_puesto_area` (`n_id_empleado_puesto_area`);
*/
ALTER TABLE `inst_empleado_puesto_area` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `inst_empleado_puesto_area` ADD FOREIGN KEY (`n_id_cat_area`) REFERENCES `inst_cat_areas` (`n_id_cat_area`);

ALTER TABLE `inst_empleado_puesto_area` ADD FOREIGN KEY (`n_id_cat_puesto`) REFERENCES `inst_cat_puestos` (`n_id_cat_puesto`);

ALTER TABLE `inst_empleado` ADD FOREIGN KEY (`id_sexo`) REFERENCES `inst_cat_sexo` (`id_sexo`);

ALTER TABLE `inst_empleado` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `inst_log_empleado` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);

ALTER TABLE `inst_log_empleado` ADD FOREIGN KEY (`n_session_id`) REFERENCES `seg_log_sesion` (`n_session_id`);

ALTER TABLE `seg_usuario_estado_usuario` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `seg_usuario_estado_usuario` ADD FOREIGN KEY (`n_id_estado_usuario`) REFERENCES `seg_cat_estado_usuario` (`n_id_estado_usuario`);

ALTER TABLE `seg_usuario_estado_usuario` ADD FOREIGN KEY (`n_session_id`) REFERENCES `seg_log_sesion` (`n_session_id`);

ALTER TABLE `seg_usuarios` ADD FOREIGN KEY (`n_id_estado_usuario`) REFERENCES `seg_cat_estado_usuario` (`n_id_estado_usuario`);

ALTER TABLE `seg_roles_usuarios` ADD FOREIGN KEY (`n_id_rol`) REFERENCES `seg_roles` (`n_id_rol`);

ALTER TABLE `seg_roles_usuarios` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `seg_roles_usuarios` ADD FOREIGN KEY (`n_session_id`) REFERENCES `seg_log_sesion` (`n_session_id`);

ALTER TABLE `seg_roles` ADD FOREIGN KEY (`n_id_rol_padre`) REFERENCES `seg_roles` (`n_id_rol`);

ALTER TABLE `seg_roles` ADD FOREIGN KEY (`n_session_id`) REFERENCES `seg_log_sesion` (`n_session_id`);

ALTER TABLE `seg_roles_modulos` ADD FOREIGN KEY (`n_id_rol`) REFERENCES `seg_roles` (`n_id_rol`);

ALTER TABLE `seg_roles_modulos` ADD FOREIGN KEY (`n_id_modulo`) REFERENCES `seg_modulos` (`n_id_modulo`);

ALTER TABLE `seg_roles_modulos` ADD FOREIGN KEY (`n_session_id`) REFERENCES `seg_log_sesion` (`n_session_id`);

ALTER TABLE `seg_usuarios_modulos` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `seg_usuarios_modulos` ADD FOREIGN KEY (`n_id_modulo`) REFERENCES `seg_modulos` (`n_id_modulo`);

ALTER TABLE `seg_modulos` ADD FOREIGN KEY (`n_id_nivel`) REFERENCES `seg_cat_nivel_modulo` (`n_id_nivel`);

ALTER TABLE `seg_modulos` ADD FOREIGN KEY (`n_id_modulo_padre`) REFERENCES `seg_modulos` (`n_id_modulo`);

ALTER TABLE `seg_log_sesion` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `seg_log_sesion` ADD FOREIGN KEY (`chain_n_session_id`) REFERENCES `seg_log_sesion` (`n_session_id`);

ALTER TABLE `seg_log_sistema` ADD FOREIGN KEY (`n_id_usuario_org`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `seg_log_usuario` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);
/*Actualización 5/dic-2023*/
ALTER TABLE `tab_doc_grupos_firmas` ADD FOREIGN KEY (`n_id_cat_area`) REFERENCES `inst_cat_areas` (`n_id_cat_area`);

ALTER TABLE `tab_doc_grupo_firmas_personas` ADD FOREIGN KEY (`n_id_grupo_personas`) REFERENCES `tab_doc_grupos_firmas` (`n_id_grupo_firmas`);
ALTER TABLE `tab_doc_grupo_firmas_personas` ADD FOREIGN KEY (`n_id_num_empleado`) REFERENCES `inst_empleado` (`n_id_num_empleado`);
ALTER TABLE `tab_doc_grupo_firmas_personas` ADD FOREIGN KEY (`n_id_inst_firmante`) REFERENCES `tab_cat_inst_firmantes` (`n_id_inst_firmante`);
ALTER TABLE `tab_doc_grupo_firmas_personas` ADD FOREIGN KEY (`n_id_inst_destinatario`) REFERENCES `tab_cat_inst_dest` (`n_id_inst_dest`);
/*End actualización 5/dic-2023*/


/*-------->ADECUACIÓN 23 JULIO 2024*/

ALTER TABLE `jel_ciudadano` ADD FOREIGN KEY (`n_id_usuario`) REFERENCES `seg_usuarios` (`n_id_usuario`);

ALTER TABLE `jel_impugnacion_autoridad_responsables` ADD FOREIGN KEY (`n_id_medio_impugnacion`) REFERENCES `jel_medio_impugnacion` (`n_id_medio_impugnacion`);
ALTER TABLE `jel_impugnacion_autoridad_responsables` ADD FOREIGN KEY (`id_autoridad_responsable`) REFERENCES `jel_cat_autoridad_responsable` (`id_autoridad_responsable`);

ALTER TABLE `jel_medio_impugnacion` ADD FOREIGN KEY (`n_id_entidad_federativa`) REFERENCES `jel_cat_entidad_federativa` (`n_id_entidad_federativa`);
ALTER TABLE `jel_medio_impugnacion` ADD FOREIGN KEY (`n_id_tipo_medio`) REFERENCES `jel_cat_tipo_medio` (`n_id_tipo_medio`);
ALTER TABLE `jel_medio_impugnacion` ADD FOREIGN KEY (`n_id_tipo_expediente`) REFERENCES `jel_cat_tipo_expediente` (`n_id_tipo_expediente`);
ALTER TABLE `jel_medio_impugnacion` ADD FOREIGN KEY (`n_id_tipo_recepcion`) REFERENCES `jel_cat_tipo_recepcion` (`n_id_tipo_recepcion`);
ALTER TABLE `jel_medio_impugnacion` ADD FOREIGN KEY (`n_id_tipo_eleccion`) REFERENCES `jel_cat_tipo_eleccion` (`n_id_tipo_eleccion`);
ALTER TABLE `jel_medio_impugnacion` ADD FOREIGN KEY (`n_id_estatus_medio_impugnacion`) REFERENCES `jel_cat_estatus_medio_impugnacion` (`n_id_estatus_medio_impugnacion`);

ALTER TABLE `jel_medio_impugnacion_involucrados` ADD FOREIGN KEY (`n_id_medio_impugnacion`) REFERENCES `jel_medio_impugnacion` (`n_id_medio_impugnacion`);
ALTER TABLE `jel_medio_impugnacion_involucrados` ADD FOREIGN KEY (`n_id_involucrados`) REFERENCES `jel_cat_involucrados` (`n_id_involucrados`);

ALTER TABLE `jel_expediente` ADD FOREIGN KEY (`n_id_medio_impugnacion`) REFERENCES `jel_medio_impugnacion` (`n_id_medio_impugnacion`);
ALTER TABLE `jel_expediente` ADD FOREIGN KEY (`n_id_tipo_documento_electronico`) REFERENCES `jel_cat_tipo_documento_electronico` (`n_id_tipo_documento_electronico`);



/*-------->END ADECUACIÓN 23 JULIO 2024*/