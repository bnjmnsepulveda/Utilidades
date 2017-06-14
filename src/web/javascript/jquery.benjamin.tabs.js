(function ($) {
    'use strict';
    function crearContainerTab(conf) {
        var flechaIzquierda = '<div id="back-' + conf.id.replace('#', '') + '" class="flecha-izquierda"><img src="img/llamada/flecha-izquierda.svg" width="10" height="10"></div>';
        var flechaDerecha = '<div id="next-' + conf.id.replace('#', '') + '" class="flecha-derecha"><img src="img/llamada/flecha-derecha.svg" width="10" height="10"></div>';
        var container = '<div class="container-header-tab">' + flechaIzquierda + '<div id="' + conf.id.replace('#', '') + '-tab-scroll" class="contenedor-tabs" ><div id="' + conf.cabezeraTab.replace('#', '') + '"></div></div>' + flechaDerecha + '</div>' +
                '<div id="' + conf.contenidoTab.replace('#', '') + '" ></div>';

        if ($(conf.id + ' div ' + conf.cabezeraTab).html() === undefined) {
            $(conf.id).html(container);
            $(conf.cabezeraTab).width(0);
            $('#back-' + conf.id.replace('#', '')).on('click', function () {
                $(conf.id + '-tab-scroll').scrollTo('-=250px', 50);
            });
            $('#next-' + conf.id.replace('#', '')).on('click', function () {
                $(conf.id + '-tab-scroll').scrollTo('+=250px', 50);
            });
        }
    }

    function crearTab(conf, id, titulo, contenido) {
        crearContainerTab(conf);

        if ($('#tab-' + id).attr('id') !== undefined) {
            seleccionarTab(conf, id);
            return;
        }
        if (titulo.length > 30) {
            titulo = titulo.substring(0, 25) + '...';
        }
        var head = '<div id="tab-' + id + '" class="tab-header tab-active" >\n\
                        <span>' + titulo + '</span>\n\
                        <img src="img/email/cerrar-pestana.svg" class="icono-cerrar-tab" />\n\
                </div>';
        var content = '<div id="tab-contenido-' + id + '" class="tab-contenido contenido-active" >' + contenido + '</div>';
        var tabHead = $(conf.cabezeraTab);//cabezera contenedora de tabs
        var tabContenido = $(conf.contenidoTab);
        var tabs = tabHead.children('.tab-header');//tabs 
        if (tabs.length > 0) {
            var size = tabs.length;
            tabs.each(function (index, element) {
                if (index === (size - 1)) {
                    seleccionarTab(conf, $(this).attr('id'));
                }
            });
        }

        tabHead.children('.flecha-derecha').remove();
        tabHead.children('div').each(function () {
            $(this).removeClass('tab-active');
        });
        tabHead.append(head);

        tabContenido.children('div').each(function () {
            $(this).removeClass('contenido-active');
        });

        $('#tab-' + id + ' span').on('click', function () {
            seleccionarTab(conf, id);
            conf.onSelect(id);
        });
        $('#tab-' + id + ' img').on('click', function () {
            eliminarTab(conf, id);
            conf.onDelete(id);
        });
        tabContenido.append(content);
        $(conf.cabezeraTab).width($(conf.cabezeraTab).width() + conf.largoTab);
        tabHead.children('.tab-header').children('span').width(conf.largoTab - 60);
        $(conf.id + '-tab-scroll').scrollTo('100%', 10);
    }

    function eliminarTab(conf, id) {
        var tabs = $(conf.cabezeraTab).children('.tab-header');
        var tab = $('#tab-' + id);
        var index = 0;
        if (tab.hasClass('tab-active')) {
            var id2;
            var isFirst = false;
            tabs.each(function (i, item) {
                if ($(this).attr('id').replace('tab-', '') === id) {
                    index = i - 1;
                    return false;
                }
            });
            tabs.each(function (i, item) {
                if (isFirst) {
                    id2 = $(this).attr('id').replace('tab-', '');
                    $(conf.id + '-tab-scroll').scrollTo('0%', 10);
                    return false;
                }
                if (index === -1) {
                    isFirst = true;
                }
                if (i === index) {
                    id2 = $(this).attr('id').replace('tab-', '');
                    return false;
                }
            });
        }
        tab.remove();
        $('#tab-contenido-' + id).remove();
        $(conf.cabezeraTab).width($(conf.cabezeraTab).width() - conf.largoTab);
        seleccionarTab(conf, id2);
        if (id2 !== undefined) {
            conf.onSelectAfterDelete(id2);
        } else {
            var id3 = $(conf.cabezeraTab).children('.tab-active').attr('id');
            id3 = id3.split('tab-')[1];
            conf.onSelectAfterDelete(id3);
        }
    }

    function seleccionarTab(conf, id) {
        if (id !== undefined) {
            var tabHead = $(conf.cabezeraTab);
            var tabContenido = $(conf.contenidoTab);
            tabHead.children('.tab-header').each(function () {
                $(this).removeClass('tab-active');
            });
            tabContenido.children('div').each(function () {
                $(this).removeClass('contenido-active');
            });
            $('#tab-' + id).addClass('tab-active');
            $('#tab-contenido-' + id).addClass('contenido-active');
        }
    }

    $.fn.tab = function (metodo, opc) {
        var elementId = this.attr('id');
        var container = {
            id: '#' + elementId,
            cabezeraTab: '#' + elementId + '-cabezera-tab',
            contenidoTab: '#' + elementId + '-contenido-tab',
            largoTab: 250,
            onSelect: opc.onSelect,
            onDelete: opc.onDelete,
            onSelectAfterDelete: opc.onSelectAfterDelete
        };
        switch (metodo) {
            case 'crearTab':
                crearTab(container, opc.id, opc.titulo, opc.contenido);
                return this;
                break;
            case'eliminarTab':
                eliminarTab(container, opc.id);
                return this;
                break;
            case 'seleccionarTab':
                seleccionarTab(container, opc.id);
                return this;
                break;

        }
        return this;
    };
})(jQuery);