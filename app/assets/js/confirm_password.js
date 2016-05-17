/**
 * Created by Toz on 17/05/2016.
 */
alert("Confirm pwd");

function validatePassword(form){

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

    if(form.name.value === "") {
        alert("Error: name cannot be blank!");
        form.name.focus();
        return false;
    }
    re = /[^a-z|^A-Z]/;
    if(re.test(form.name.value)) {
        alert("Error: name must contain only letters!");
        form.name.focus();
        return false;
    }
    if(form.lastName.value === "") {
        alert("Error: lastName cannot be blank!");
        form.lastName.focus();
        return false;
    }
    re = /[^a-z|^A-Z]/;
    if(re.test(form.lastName.value)) {
        alert("Error: lastName must contain only letters!");
        form.lastName.focus();
        return false;
    }

    if(form.password.value !== "" && form.password.value === form.confirm_password.value) {
        if(form.password.value.length < 6) {
            alert("Error: Password must contain at least six characters!");
            form.password.focus();
            return false;
        }
        if(form.password.value === form.name.value) {
            alert("Error: Password must be different from name!");
            form.password.focus();
            return false;
        }
        re = /[0-9]/;
        if(!re.test(form.password.value)) {
            alert("Error: password must contain at least one number (0-9)!");
            form.password.focus();
            return false;
        }
        re = /[a-z]/;
        if(!re.test(form.password.value)) {
            alert("Error: password must contain at least one lowercase letter (a-z)!");
            form.password.focus();
            return false;
        }
        re = /[A-Z]/;
        if(!re.test(form.password.value)) {
            alert("Error: password must contain at least one uppercase letter (A-Z)!");
            form.password.focus();
            return false;
        }
    } else {
        alert("Error: Please check that you've entered and confirmed your password!");
        form.password.focus();
        return false;
    }

    alert("You entered a valid password: " + form.password.value);
    return true;


}

