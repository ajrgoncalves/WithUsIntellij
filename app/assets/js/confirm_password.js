/**
 * Created by Toz on 17/05/2016.
 */
function validateRegist(form){

    // var password = form.password.value();//document.getElementById("password");
    // var confirm_password =  form.confirm_password.value();//document.getElementById("confirm_password");

    // alert("INNN");
    // if(password.value != confirm_password.value) {
    //     alert("invalid pass");
    //     return false;
    //     //confirm_password.setCustomValidity("Passwords Don't Match");
    // } else {
    //     //enable button
    //     alert("valid pass");
    //     return true;
    //     //confirm_password.setCustomValidity('');
    // }


    //name
    if(form.name.value === "") {
        alert("Error: O campo nome não pode ser vazio!");
        form.name.focus();
        return false;
    }
    re = /[^a-z|^A-Z]/;
    if(re.test(form.name.value)) {
        alert("Error: Nome deve conter apenas letras!");
        form.name.focus();
        return false;
    }

    //LastName
    if(form.lastName.value === "") {
        alert("Error: O campo ultimo nome não pode ser vazio!");
        form.lastName.focus();
        return false;
    }
    re = /[^a-z|^A-Z]/;
    if(re.test(form.lastName.value)) {
        alert("Error: Ultimo nome deve conter apenas letras!");
        form.lastName.focus();
        return false;
    }

    //EMAIL
    if(form.email.value === "") {
        alert("Error: O campo email não pode ser vazio!");
        form.email.focus();
        return false;
    }
    re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(!re.test(form.email.value)) {
        alert("Error: O email tem de ter uma estrutura valida");
        form.email.focus();
        return false;
    }

// alert("pass: "+ form.password.value + " - confirm pass : "+ form.confirm_password.value);
    // PASSWORD
    if(form.password.value !== "" && form.password.value == form.confirm_password.value ) {
        if(form.password.value.length < 6) {
            alert("Error: A sua password deve conter no minimo 6 caracteres!");
            form.password.focus();
            return false;
        }
        if(form.password.value === form.name.value) {
            alert("Error: Password deve ser diferente do nome!");
            form.password.focus();
            return false;
        }
        re = /[0-9]/;
        if(!re.test(form.password.value)) {
            alert("Error: password deve conter pelo menos um numero (0-9)!");
            form.password.focus();
            return false;
        }
        re = /[a-z]/;
        if(!re.test(form.password.value)) {
            alert("Error: password deve conter pelo menos uma letra minuscula(a-z)!");
            form.password.focus();
            return false;
        }
        re = /[A-Z]/;
        if(!re.test(form.password.value)) {
            alert("Error: password deve conter pelo menos uma letra maiuscula (A-Z)!");
            form.password.focus();
            return false;
        }
    } else {
        alert("Error: A password e a password de confirmação têm que ser iguais!");
        form.password.focus();
        return false;
    }

    alert("Inseriu uma password valida: " + form.password.value);
    return true;


}

