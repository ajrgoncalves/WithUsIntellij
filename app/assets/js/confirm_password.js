/**
 * Created by Toz on 17/05/2016.
 */
function validateRegister(form) {

    //name
    if (form.name.value === "") {
        alert("Error: O campo nome não pode ser vazio!");
        form.name.focus();
        return false;
    }
    re = /[^a-z|^A-Z]/;
    if (re.test(form.name.value)) {
        alert("Error: Nome deve conter apenas letras!");
        form.name.focus();
        return false;
    }

    //LastName
    if (form.lastName.value === "") {
        alert("Error: O campo ultimo nome não pode ser vazio!");
        form.lastName.focus();
        return false;
    }
    re = /[^a-z|^A-Z]/;
    if (re.test(form.lastName.value)) {
        alert("Error: Ultimo nome deve conter apenas letras e não aceita caracteres especiais!");
        form.lastName.focus();
        return false;
    }

    //EMAIL
    if (form.email.value === "") {
        alert("Error: O campo email não pode ser vazio!");
        form.email.focus();
        return false;
    }
    re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2})+$/;
    if (!re.test(form.email.value)) {
        form.email.focus();
        alert("Error: O email tem de ter uma estrutura valida");
    } else if (form.email.value.indexOf("@withus.pt", form.email.value.length - "@withus.pt".length) == -1) {

        form.email.focus();
        alert("Error: O email tem de terminar em @withus.pt");
        return false;
    }

    //Age

    if (new Date(form.age.value).getFullYear().toString() === "") {
        alert("Error: O campo idade não pode ser vazio!");
        form.age.focus();
        return false;
    }

    var date = new Date();
    var year = date.getFullYear();
    var yearForm = new Date(form.age.value).getFullYear();
    var age = (year - yearForm);

    if (age <= 16 || age > 75 || isNaN(age)) {
        alert("Error: A data de nascimento deve corresponder a idades entre os 16 e os 75 anos");
        form.age.focus();
        return false;
    }

    //Morada

    if (form.homeAddress.value === "") {
        alert("Error: O campo da morada não pode ser vazio!");
        form.homeAddress.focus();
        return false;

    } else {

        re = /[^a-zA-Z 0-9]+/;
        if (re.test(form.homeAddress.value)) {
            alert("Error: A sua morada não pode conter caracteres especiais");
            form.homeAddress.focus();
            return false;
        }
    }

    //Pais

    if (form.countryId.value === "") {
        alert("Error: O campo do Pais não pode ser vazio!");
        form.countryId.focus();
        return false;
    }

    //Contacto
    if (form.phoneNumber.value === "") {
        alert("Error: O campo de contacto não pode ser vazio!");
        form.phoneNumber.focus();
        return false;
    }
    re = /[0-9]+/;
    if (re.test(form.phoneNumber.value) && form.phoneNumber.value.length != 9) {
        alert("Error: Deve colocar um contacto valido", re.test(form.phoneNumber.value));
        form.phoneNumber.focus();
        return false;
    }

// alert("pass: "+ form.password.value + " - confirm pass : "+ form.confirm_password.value);
    // PASSWORD

    if (form.password.value !== "" && (form.password.value === form.confirmPassword.value)) {
        if (form.password.value.length < 6) {
            alert("Error: A sua password deve conter no minimo 6 caracteres!");
            form.password.focus();
            return false;
        }
        if (form.password.value === form.name.value) {
            alert("Error: Password deve ser diferente do nome!");
            form.password.focus();
            return false;
        }
        re = /[0-9]/;
        if (!re.test(form.password.value)) {
            alert("Error: password deve conter pelo menos um numero (0-9)!");
            form.password.focus();
            return false;
        }
        re = /[a-z]/;
        if (!re.test(form.password.value)) {
            alert("Error: password deve conter pelo menos uma letra minuscula(a-z)!");
            form.password.focus();
            return false;
        }
        re = /[A-Z]/;
        if (!re.test(form.password.value)) {
            alert("Error: password deve conter pelo menos uma letra maiuscula (A-Z)!");
            form.password.focus();
            return false;
        }
    } else {
        alert("Error: A password e a password de confirmação têm que ser iguais!");
        form.password.focus();
        return false;
    }

    alert("valor do contacto" + form.phoneNumber.value());
    alert("valor da password" + form.password.value() + "Valor da pass de confirmação" + form.confirmPassword.value());

    alert("Registo concluido");
    return true;


}



