/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * valida los inputs vacios.
 * @param {type} array Array de objetos inputs a validar.
 * @returns {Boolean} true cuando todos los inputs son validados.
 */
function validarInputs(array) {
    for (x = 0; x < array.length; x++) {
        if (array[x].val().length === 0) {
            array[x].focus();
            array[x].notify('Campo vacio');
            return false;
        }
    }
    return true;
}

function validarInputsNumero(array) {
    var numero;
    for (x = 0; x < array.length; x++) {
        numero = array[x].val();
        if (isNaN(numero)) {
            array[x].focus();
            array[x].notify('Campo debe ser numerico');
            return false;
        }
    }
    return true;
}


/**
 * Valida direcciones de email enviando un notify en el input a validar.
 * @param {type} emailInput
 * @returns {undefined}
 */
function validarEmailAddress(emailInput) {
    expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!expr.test(emailInput.val())) {
        emailInput.focus();
        emailInput.notify('Formato email no valido');
        return false;
    }
    return true;
}

/**
 * Transforma una cantidad de segundos en formato HORA:MINUTO:SEGUNDOS
 * @param {type} segundos
 * @returns {String}
 */
function formatearSegundos(segundos) {
    var hours = Math.floor(segundos / 3600);
    var minutes = Math.floor((segundos % 3600) / 60);
    var seconds = Number((segundos % 60).toFixed(0)) + '';
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    seconds = seconds < 10 ? '0' + seconds : seconds;
    return hours + ':' + minutes + ':' + seconds;
}

/**
 * funcion para mostrar u ocultar elemento
 * @param {type} id
 * @returns {undefined}
 */
function mostrarOcultar(id) {
    var x = document.getElementById(id);
    if (x.style.display === 'none') {
        x.style.display = 'inline';
    } else {
        x.style.display = 'none';
    }
} 