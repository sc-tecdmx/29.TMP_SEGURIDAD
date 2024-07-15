CREATE VIEW vista_part1 AS

SELECT
    d.n_id_documento,
    d.folio_documento,
    d.n_id_prioridad,
    d.visible,
    d.creacion_documento_fecha,
    d.s_asunto,
    w.ult_actualizacion,
    CASE 
        WHEN d.n_id_num_empleado_creador NOT IN (
            SELECT n_id_num_empleado FROM tab_docs_firmantes WHERE n_id_documento = d.n_id_documento
            UNION
            SELECT n_id_num_empleado FROM tab_doc_destinatarios WHERE n_id_documento = d.n_id_documento
        ) THEN d.n_id_num_empleado_creador
    END AS empleado_id,
    'creador' AS rol,
    w.id_etapa_documento
FROM
    tab_documentos d
LEFT JOIN tab_documento_workflow w ON d.n_id_documento = w.id_document
WHERE
    w.ult_actualizacion = (SELECT MAX(ult_actualizacion) FROM tab_documento_workflow WHERE id_document = d.n_id_documento)
    AND d.n_id_num_empleado_creador NOT IN (SELECT n_id_num_empleado FROM tab_docs_firmantes WHERE n_id_documento = d.n_id_documento)
    AND d.n_id_num_empleado_creador NOT IN (SELECT n_id_num_empleado FROM tab_doc_destinatarios WHERE n_id_documento = d.n_id_documento)

UNION

SELECT
    d.n_id_documento,
    d.folio_documento,
    d.n_id_prioridad,
    d.visible,
    d.creacion_documento_fecha,
    d.s_asunto,
    w.ult_actualizacion,
    f.n_id_num_empleado AS empleado_id,
    IF(d.n_id_num_empleado_creador = f.n_id_num_empleado, 'creador-firmante', 'firmante') AS rol,
    w.id_etapa_documento
FROM
    tab_documentos d
INNER JOIN tab_docs_firmantes f ON d.n_id_documento = f.n_id_documento
LEFT JOIN tab_documento_workflow w ON d.n_id_documento = w.id_document
WHERE
    w.ult_actualizacion = (SELECT MAX(ult_actualizacion) FROM tab_documento_workflow WHERE id_document = d.n_id_documento)
    AND f.n_id_num_empleado IS NOT NULL

UNION

SELECT
    d.n_id_documento,
    d.folio_documento,
    d.n_id_prioridad,
    d.visible,
    d.creacion_documento_fecha,
    d.s_asunto,
    w.ult_actualizacion,
    dest.n_id_num_empleado AS empleado_id,
    IF(d.n_id_num_empleado_creador = dest.n_id_num_empleado, 'creador-destinatario', 'destinatario') AS rol,
    w.id_etapa_documento
FROM
    tab_documentos d
INNER JOIN tab_doc_destinatarios dest ON d.n_id_documento = dest.n_id_documento
LEFT JOIN tab_documento_workflow w ON d.n_id_documento = w.id_document
WHERE
    w.ult_actualizacion = (SELECT MAX(ult_actualizacion) FROM tab_documento_workflow WHERE id_document = d.n_id_documento)
    AND dest.n_id_num_empleado IS NOT NULL
ORDER BY n_id_documento, empleado_id;



CREATE VIEW vista_tablero AS
SELECT DISTINCT 
	v1.n_id_documento, v1.visible, folio_documento, tced.s_desc_etapa, tcp.desc_prioridad as prioridad, creacion_documento_fecha, 
	s_asunto, empleado_id as num_empleado, rol as tipo, ult_actualizacion, tdf.n_id_inst_firmante as n_id_inst
FROM vista_part1 v1
JOIN tab_cat_etapa_documento tced on tced.id_etapa_documento = v1.id_etapa_documento 
JOIN tab_cat_prioridad tcp  on tcp.n_id_prioridad  = v1.n_id_prioridad
JOIN tab_docs_firmantes tdf on tdf.n_id_num_empleado = v1.empleado_id
WHERE NOT (
    rol = 'firmante' AND NOT EXISTS (
        SELECT 1
        FROM pki_documento_firmantes docfirmantesPKI
        INNER JOIN tab_documentos_adjuntos docadjuntosTAB 
        ON docfirmantesPKI.s_hash_documento = docadjuntosTAB.documento_hash
        WHERE docadjuntosTAB.id_document = v1.n_id_documento
          AND docfirmantesPKI.n_id_num_empleado = v1.empleado_id
    )
) AND v1.visible = 1;
