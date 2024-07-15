SET FOREIGN_KEY_CHECKS=0;
DROP TABLE tab_cat_destino_documento;
DROP TABLE tab_cat_tipo_documento;
DROP TABLE tab_cat_prioridad;
DROP TABLE tab_cat_tipo_notificacion;
DROP TABLE tab_notificaciones;
DROP TABLE tab_expedientes;
DROP TABLE tab_documentos;
DROP TABLE tab_documentos_adjuntos;
DROP TABLE tab_cat_inst_firmantes;
DROP TABLE tab_cat_inst_dest;
DROP TABLE tab_doc_destinatarios;
DROP TABLE tab_cat_etapa_documento;
DROP TABLE tab_documento_workflow;
DROP TABLE tab_cat_doc_config;
DROP TABLE tab_doc_config;
DROP TABLE tab_docs_firmantes;
DROP TABLE tab_doc_grupos_firmas;
DROP TABLE tab_doc_grupo_firmas_personas;

DROP TABLE pki_x509_ac_autorizadas;
DROP TABLE pki_x509_registrados;
DROP TABLE pki_usuarios_cert;
DROP TABLE pki_log_usuarios_cert;
DROP TABLE pki_transaccion;
DROP TABLE pki_documento;
DROP TABLE pki_documento_firmantes;
DROP TABLE pki_cat_tipo_firma;
DROP TABLE pki_cat_firma_aplicada;
DROP TABLE pki_documento_destinos;
DROP TABLE pki_cat_instruccion_doc;
DROP TABLE pki_x509_ocsp;
DROP TABLE pki_x509_tsp;
DROP TABLE pki_x509_documento_certificado;
DROP TABLE pki_x509_jel_autorizacion;

DROP table inst_u_adscripcion_detalle;
DROP TABLE inst_cat_areas;
DROP TABLE inst_cat_sexo;
DROP TABLE inst_cat_puestos;
DROP TABLE inst_empleado;
DROP TABLE inst_empleado_puesto_area;
DROP TABLE inst_titular_u_adscripcion;
DROP TABLE inst_log_empleado;


DROP TABLE seg_cat_estado_usuario;
DROP TABLE seg_cat_nivel_modulo;
DROP TABLE seg_usuario_estado_usuario;
DROP TABLE seg_usuarios;
DROP TABLE seg_roles_usuarios;
DROP TABLE seg_roles;
DROP TABLE seg_roles_modulos;
DROP TABLE seg_usuarios_modulos;
DROP TABLE seg_modulos;
DROP TABLE seg_log_sesion;
DROP TABLE seg_log_sistema;
DROP TABLE seg_log_usuario;


DROP TABLE jel_renapo_curps;
DROP TABLE jel_persona_jel;
SET FOREIGN_KEY_CHECKS=1;