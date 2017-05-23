
function agregarTab(id, titulo, contenido) {
    $('#contenido_principal').tab('crearTab', {
        id: id,
        titulo: titulo,
        contenido: contenido,
        onSelect: function (id) {
            alert(id);
        },
        onDelete: function (id) {
            alert(id);
        }
    });
}

