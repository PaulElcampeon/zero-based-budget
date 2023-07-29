function toggleForm() {
    var loginForm = document.getElementById("login-form");
    var signupForm = document.getElementById("signup-form");

    if (loginForm.style.display === "none") {
        loginForm.style.display = "block";
        signupForm.style.display = "none";
    } else {
        loginForm.style.display = "none";
        signupForm.style.display = "block";
    }
}

const signup_email = document.getElementById("signup-email")
const signup_password = document.getElementById("signup-password")
const signup_confirm_password = document.getElementById("signup-confirm-password")

const login_email = document.getElementById("login-email")
const login_password = document.getElementById("login-password")


const register_btn = document.getElementById("register-btn")
const login_btn = document.getElementById("login-btn")

register_btn.addEventListener("click", (event) => {
    event.preventDefault()
    if (!validateRegisteredFields()) {
        alert("Fill in all fields appropriately");
        return;
    }

    const url = '../api/v1/user/create';

    const data = {
        email: signup_email.value,
        password: signup_password.value
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            console.log('Response:', result);
            toggleForm()
        })
        .catch(error => {
            console.error('Error:', error);
        });
})

login_btn.addEventListener("click", (event) => {
    event.preventDefault()
    if (!validateLoginFields()) {
        alert("Fill in all fields appropriately");
        return;
    }

    const url = '../api/v1/auth/authenticate';

    const data = {
        email: login_email.value,
        password: login_password.value
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            console.log('Response:', result);
            storeValueInStorage("tokie", result.token)
            storeValueInStorage("budgets", JSON. stringify(result.budgets))
            location.href = '../create'
        })
        .catch(error => {
            console.error('Error:', error);
        });

})

function validateRegisteredFields() {
    if (!validateEmail(signup_email.value)) {
        return false;
    }

    if (!isNotEmpty(signup_password.value)) {
        return false;
    }

    if (!isNotEmpty(signup_confirm_password.value)) {
        return false;
    }

    if (signup_password.value !== signup_confirm_password.value) {
        return false;
    }

    return true;
}

function validateLoginFields() {
    if (!validateEmail(login_email.value)) {
        return false;
    }

    if (!isNotEmpty(login_password.value)) {
        return false;
    }

    return true;
}

function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function isNotEmpty(value) {
    return value.trim() !== "";
}

function storeValueInStorage(key, value) {
    localStorage.setItem(key, value)
}

function removeFromStorage(key) {
    localStorage.removeItem(key)
}

function retrieveFromStorage(key) {
    localStorage.getItem(key)
}