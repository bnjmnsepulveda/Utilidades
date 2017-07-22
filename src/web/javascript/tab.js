
/* global prefixCarpeta */

function agregarTab(id, titulo, contenido) {
    $('#contenido_principal').tab('crearTab', {
        id: id,
        titulo: titulo,
        contenido: contenido,
        onSelect: function (id) {
            if (id.includes(prefixCarpeta)) {
                var ultimoCarater = id.split(prefixCarpeta)[1];
                $('#carpeta_correo_sel').val(ultimoCarater);
            } else {
                $('#carpeta_correo_sel').val(-1);
            }
        },
        onDelete: function (id) {
            
        },
        onSelectAfterDelete: function (id) {
           if (id.includes(prefixCarpeta)) {
                var ultimoCarater = id.split(prefixCarpeta)[1];
                $('#carpeta_correo_sel').val(ultimoCarater);
            } else {
                $('#carpeta_correo_sel').val(-1);
            }
        }
    });
    if (!id.includes(prefixCarpeta)) {
        $('#carpeta_correo_sel').val(-1);
    }
}

function eliminarTab(id) {
    $('#contenido_principal').tab('eliminarTab', {
        id: id
    });
}

function containsTab(id) {
    var contiene = false;
    if ($('#tab-' + id).attr('id') !== undefined) {
        contiene = true;
    }
    return contiene;
}
