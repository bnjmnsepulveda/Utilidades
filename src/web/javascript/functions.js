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
