//VALIDATE LOGIN
let registerLogin = document.getElementById("login")
let registerLoginError = document.getElementById("login-error");

const validateRegisterLogin = async () => {
    let format = /[!@#$%^&*()+=\[\]{};'`:"\\|,.<>\/?~]/;
    let registerLoginValue = registerLogin.value;
    if (registerLoginValue === '') {
        registerLogin.classList.add('error-input');
        registerLoginError.innerHTML = 'Login nie może być pusty!'
        return false;
    }
    if (registerLoginValue.includes(' ')) {
        registerLogin.classList.add('error-input');
        registerLoginError.innerHTML = 'Login nie może zawierać spacji!'
        return false;
    }
    if (registerLoginValue.length < 3) {
        registerLogin.classList.add('error-input');
        registerLoginError.innerHTML = 'Login musi mieć przynajmniej 3 znaki'
        return false;
    }
    if (registerLoginValue.length > 20) {
        registerLogin.classList.add('error-input');
        registerLoginError.innerHTML = 'Login może mieć maksymalnie 20 znaków'
        return false;
    }
    if (format.test(registerLoginValue)) {
        registerLogin.classList.add('error-input');
        registerLoginError.innerHTML = 'Dozwolone znaki specjalne to _ opraz -'
        return false;
    }
    let isLoginAvailable = await checkLoginAvailability(registerLoginValue);
    if (!isLoginAvailable) {
        registerLogin.classList.add('error-input');
        registerLoginError.innerHTML = 'Login jest już zajęty'
        return false;
    }

    registerLogin.classList.remove('error-input');
    registerLoginError.innerHTML = ''
    return true;
}

function checkLoginAvailability(login) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/user/name/availability',
            type: 'GET',
            data: {name: login},
            success: function (data) {
                if (!data) {
                    resolve(false)
                } else {
                    resolve(true)
                }
            },
            error: function (error) {
                console.log('Wystąpił błąd: ', error)
                reject(error);
            }
        });
    });
}

//VALIDATE EMAIL
let registerEmail = document.getElementById("email")
let registerEmailError = document.getElementById("email-error");
const validateEmail = async () => {
    let validRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    let registerEmailValue = registerEmail.value;
    if (registerEmailValue === '') {
        registerEmail.classList.add('error-input');
        registerEmailError.innerHTML = 'Email nie może być pusty!'
        return false;
    }
    if (registerEmailValue.length < 5) {
        registerEmail.classList.add('error-input');
        registerEmailError.innerHTML = 'Email jest za krótki'
        return false;
    }
    if (registerEmailValue.length > 50) {
        registerEmail.classList.add('error-input');
        registerEmailError.innerHTML = 'Email jest za długi'
        return false;
    }
    if (!registerEmailValue.includes('@')) {
        registerEmail.classList.add('error-input');
        registerEmailError.innerHTML = 'Email nie zawiera @'
        return false;
    }
    if (!registerEmailValue.match(validRegex)) {
        registerEmail.classList.add('error-input');
        registerEmailError.innerHTML = 'Email wydaje się nie poprawny'
        return false;
    }
    let checkIsEmailAvailable = await checkEmailAvailability(registerEmailValue);
    if (!checkIsEmailAvailable) {
        registerEmail.classList.add('error-input');
        registerEmailError.innerHTML = 'Email jest już zajęty'
        return false;
    }
    registerEmail.classList.remove('error-input');
    registerEmailError.innerHTML = ''
    return true;
}

function checkEmailAvailability(email) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/user/email/availability',
            type: 'GET',
            data: {name: email},
            success: function (data) {
                if (!data) {
                    resolve(false)
                } else {
                    resolve(true)
                }
            },
            error: function (error) {
                console.log('Wystąpił błąd: ', error)
                reject(error);
            }
        });
    });
}

//SHOW REGISTER INFO BOX
let loginInfoIcon = document.getElementById("login-info");
let loginInfoBox = document.getElementById("login-info-box");

loginInfoIcon.addEventListener('mouseenter', () => {
    loginInfoBox.classList.remove("hide-info-box");
});
loginInfoIcon.addEventListener('mouseleave', () => {
    loginInfoBox.classList.add("hide-info-box");
});
let emailInfoIcon = document.getElementById("email-info");
let emailInfoBox = document.getElementById("email-info-box");

emailInfoIcon.addEventListener('mouseenter', () => {
    emailInfoBox.classList.remove("hide-info-box");
});
emailInfoIcon.addEventListener('mouseleave', () => {
    emailInfoBox.classList.add("hide-info-box");
});


//SHOW PASSWORD
let passwordShowIcon = document.getElementById("show-password");
let passwordHideIcon = document.getElementById("hide-password");
let passwordInput = document.getElementById("password")

passwordShowIcon.addEventListener('click', () => {
    passwordShowIcon.classList.add("hide-password-icon");
    passwordHideIcon.classList.remove("hide-password-icon");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }

});
passwordHideIcon.addEventListener('click', () => {
    passwordShowIcon.classList.remove("hide-password-icon");
    passwordHideIcon.classList.add("hide-password-icon");
    if (passwordInput.type === "text") {
        passwordInput.type = "password";
    } else {
        passwordInput.type = "text";
    }
});
//VALIDATE PASSWORD
let registerPasswordError = document.getElementById("password-error");

let passwordFirstBar = document.getElementById("first-password-param");
let passwordSecondBar = document.getElementById("second-password-param");
let passwordThirdBar = document.getElementById("third-password-param");
const validatePassword = () => {
    let passwordValue = passwordInput.value;
    if (passwordValue === '') {
        passwordInput.classList.add("error-input");
        registerPasswordError.innerHTML = "Hasło nie może być puste"
        return false;
    }
    if (passwordValue.length > 200) {
        passwordInput.classList.add("error-input");
        registerPasswordError.innerHTML = "Hasło może mieć maksymalnie 200 znaków"
        return false;
    }
    if (passwordValue.length < 8) {
        passwordInput.classList.add("error-input");
        registerPasswordError.innerHTML = "Hasło musi mieć przynajmniej 8 znaków"
        return false;
    }
    if (!/\d/.test(passwordValue)) {
        passwordInput.classList.add("error-input");
        registerPasswordError.innerHTML = "Hasło musi zawierać 1 cyfre"
        return false;
    }

    if (!/([A-Z])/.test(passwordValue)) {
        passwordInput.classList.add("error-input");
        registerPasswordError.innerHTML = "Hasło musi zawierać 1 wielką litere"
        return false;
    }

    passwordInput.classList.remove("error-input");
    registerPasswordError.innerHTML = ""
    return true;
}
passwordInput.addEventListener('input', function () {
    addOrRemoveClassToPasswordParam();
});

function addOrRemoveClassToPasswordParam() {
    let conditions = [
        passwordInput.value.length >= 8,
        /\d/.test(passwordInput.value),
        /([A-Z])/.test(passwordInput.value)
    ];

    let bars = [passwordFirstBar, passwordSecondBar, passwordThirdBar];
    let num = 0;
    let lastNum = 2;
    for (let i = 0; i < bars.length; i++) {
        if (conditions[i]) {
            bars[num].classList.add("password-param-good");
            bars[num] = '';
            num++;
        } else {
            bars[lastNum].classList.remove("password-param-good");
            lastNum--;
        }
    }
}
//SEND FORM
let registerForm = document.getElementById("register-form")
registerForm.addEventListener("submit",function (ev){
    ev.preventDefault();
});
async function checkFormInputs(){
    let login = await validateRegisterLogin();
    let email =await validateEmail();
    let password = validatePassword();
    return login && email && password;
}
let registerButton = document.getElementById("register-button");
function senRegisterForm() {
    checkFormInputs().then(result => {
        if (result) {
            registerForm.submit();
        } else {
            registerLoginError.classList.add("button-error");
            registerEmailError.classList.add("button-error");
            registerPasswordError.classList.add("button-error");
            registerButton.classList.add("button-error");

        }
        setTimeout(function () {
            registerLoginError.classList.remove("button-error");
            registerEmailError.classList.remove("button-error");
            registerPasswordError.classList.remove("button-error");
            registerButton.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji', error)
    })
}
