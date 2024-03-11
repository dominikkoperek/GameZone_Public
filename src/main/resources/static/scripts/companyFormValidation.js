let companyName = document.getElementById("companyName");
let companyShortDescription = document.getElementById("companyShortDescription");
let companyLongDescription = document.getElementById("companyLongDescription");
let companyListSuggestion = document.getElementById("show-suggestion-list");
let companyFormError = document.querySelector(".notification-error");
let form = document.getElementById("add-content-form");
let formNameError = document.getElementById("name-error");
let formShortDescriptionError = document.getElementById("short-description-error");
let formLongDescriptionError = document.getElementById("long-description-error");
let formCountryError = document.getElementById("country-error");
let timerCompanySuggestions;
let companySuggestions = [];
let isProducer = document.getElementById("isProducer");
let isPublisher = document.getElementById("isPublisher");
let isCompanyError = document.getElementById("is-company-error")

let companyCountry = document.getElementById("company-country");
let countryValue = document.getElementById("country-value");
let countriesListSuggestion = document.getElementById("show-countries-list");
let timerCountrySuggestions;
let countriesSuggestions = [];

companyName.addEventListener('keyup', function () {
    clearTimeout(timerCompanySuggestions);
    timerCompanySuggestions = setTimeout(function () {
        getCompanySuggestion(companyName.value);
    }, 450)
});

companyCountry.addEventListener('keyup', function () {
    clearTimeout(timerCountrySuggestions);
    timerCountrySuggestions = setTimeout(function () {
        getCountriesSuggestion(companyCountry.value);
    }, 450)
});


const validateCompanyName = async () => {
    let companyNameValue = companyName.value.trim();
    if (companyNameValue === '') {
        companyName.classList.add('error-input');
        companyFormError.innerHTML = 'Nazwa nie może być pusta!';
        return false;
    }
    if (companyNameValue.length < 3) {
        companyName.classList.add('error-input');
        companyFormError.innerHTML = 'Nazwa musi mieć przynajmniej 3 znaki';
        return false;
    }
    if (companyNameValue.length > 35) {
        companyName.classList.add('error-input');
        companyFormError.innerHTML = 'Nazwa może mieć maksymalnie 35 znaków';
        return false;
    }
    let isCompanyAvailable = await checkCompanyAvailability(companyNameValue);
    if (!isCompanyAvailable) {
        companyName.classList.add('error-input');
        companyFormError.innerHTML = 'Wydawca już istnieje';
        return false;
    }
    companyName.classList.remove('error-input');
    companyFormError.innerHTML = '';
    return true;
}
const clearCompanyForm = () => {
    form.reset();
    companyName.classList.remove('error-input');
    companyShortDescription.classList.remove('error-input');
    countryValue.classList.remove('error-input');
    companyLongDescription.classList.remove('error-input');
    isProducer.classList.remove('error-input');
    isPublisher.classList.remove('error-input');

    isCompanyError.innerHTML = '';
    companyFormError.innerHTML = '';
    formNameError.innerHTML = '';
    formShortDescriptionError.innerHTML = '';
    formLongDescriptionError.innerHTML = '';
    companyListSuggestion.innerHTML = '';
    countriesListSuggestion.innerHTML = '';
    formCountryError.innerHTML = '';

}
const validateCountry = () => {
    let countryValueTrim = countryValue.value.trim();
    if (countryValueTrim === '') {
        countryValue.classList.add('error-input');
        formCountryError.innerHTML = 'Kraj nie może być pusty'
        return false;
    }
    if (countryValueTrim.length < 4) {
        countryValue.classList.add('error-input');
        formCountryError.innerHTML = 'Kraj musi mieć przynajmniej 4 znaki'
        return false;
    }
    if (countryValueTrim.length > 33) {
        countryValue.classList.add('error-input');
        formCountryError.innerHTML = 'Kraj może mieć maksymalnie 33 znaki'
        return false;
    }
    if (countryValueTrim !== companyCountry.value) {
        countryValue.classList.add('error-input');
        formCountryError.innerHTML = 'Wartośći w obu polach muszą być takie same';
        return false;
    }
    formCountryError.innerHTML = '';
    countryValue.classList.remove('error-input');
    return true;
}
const validateCompanyShortDescription = () => {
    let companyShortDescriptionValue = companyShortDescription.value.trim();

    if (companyShortDescriptionValue === '') {
        companyShortDescription.classList.add('error-input');
        formShortDescriptionError.innerHTML = 'Opis nie może być pusty'
        return false;
    }
    if (companyShortDescriptionValue.length < 100) {
        companyShortDescription.classList.add('error-input');
        formShortDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 100 znaków'
        return false;
    }
    if (companyShortDescriptionValue.length > 1000) {
        companyShortDescription.classList.add('error-input');
        formShortDescriptionError.innerHTML = 'Opis może mieć maksymalnie 1000 znaków'
        return false;
    }
    formShortDescriptionError.innerHTML = '';
    companyShortDescription.classList.remove('error-input');
    return true;
}

const validateCompanyLongDescription = () => {
    let companyLongDescriptionValue = tinymce.get("companyLongDescription").getContent();

    if (companyLongDescriptionValue === '') {
        companyLongDescription.classList.add('error-input');
        formLongDescriptionError.innerHTML = 'Opis nie może być pusty'
        return false;
    }
    if (companyLongDescriptionValue.length < 200) {
        companyLongDescription.classList.add('error-input');
        formLongDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 200 znaków'
        return false;
    }

    if (companyLongDescriptionValue.length > 105_000) {
        companyLongDescription.classList.add('error-input');
        formLongDescriptionError.innerHTML = 'Opis może mieć maksymalnie 100000 znaków'
        return false;
    }
    formLongDescriptionError.innerHTML = '';
    companyLongDescription.classList.remove('error-input');
    return true;
}

function validateIsProducerOrIsPublisher() {
    if (!isPublisher.checked && !isProducer.checked) {
        isProducer.classList.add('error-input');
        isPublisher.classList.add('error-input');
        isCompanyError.innerHTML = 'Wybierz przynajmniej 1 opcje!'
        return false;
    }
    isCompanyError.innerHTML = '';
    isProducer.classList.remove('error-input');
    isPublisher.classList.remove('error-input');
    return true;

}

const formButton = document.getElementById("form-button");
formButton.addEventListener("click", function (ev) {
    ev.preventDefault();
})

async function checkIsFormValid() {
    let companyName = await validateCompanyName();
    let companyShortDescription = validateCompanyShortDescription();
    let companyLongDescription = validateCompanyLongDescription();
    let country = validateCountry();
    let isProducerOrPublisher = validateIsProducerOrIsPublisher();

    return companyName && companyShortDescription && companyLongDescription && country && isProducerOrPublisher;
}

function validateCompanyForm() {
    checkIsFormValid().then(result => {
        if (result) {
            form.submit();
        } else {
            formNameError.classList.add("button-error");
            formShortDescriptionError.classList.add("button-error");
            formLongDescriptionError.classList.add("button-error");
            formButton.classList.add("button-error");
            formCountryError.classList.add("button-error");
            isCompanyError.classList.add("button-error");
        }
        setTimeout(function () {
            formButton.classList.remove("button-error");
            formNameError.classList.remove("button-error");
            formShortDescriptionError.classList.remove("button-error");
            formLongDescriptionError.classList.remove("button-error");
            formCountryError.classList.remove("button-error");
            isCompanyError.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji ', error)
    })
}


function getCompanySuggestion(input) {
    $.ajax({
        url: '/api/company/allCompanies',
        type: 'GET',
        success: function (data) {
            companySuggestions = data;
            showSuggestions(input);
            if (companyListSuggestion.children.length > 0 && companyName.value !== '') {
                companyListSuggestion.classList.add("show-notification")
                companyListSuggestion.classList.remove("hide-notification")
            } else {
                companyListSuggestion.classList.add("hide-notification")
                companyListSuggestion.classList.remove("show-notification")
            }

        },
        error: function (error) {
            console.error('Error: ', error);
        }
    });
}

function showSuggestions(input) {
    let filteredSuggestions = companySuggestions.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions.sort((a, b) => a.localeCompare(b));
    if (!input) {
        companyListSuggestion.innerHTML = '';
        return;
    }
    companyListSuggestion.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        let listItem = document.createElement('li');
        listItem.className = 'hint-listing';
        listItem.textContent = suggestion;
        companyListSuggestion.appendChild(listItem);
    });
}

function getCountriesSuggestion(input) {
    $.ajax({
        url: '/api/country/allCountries',
        type: 'GET',
        success: function (data) {
            countriesSuggestions = data;
            showCountries(input);
            if (countriesListSuggestion.children.length > 0 && companyCountry.value !== '') {
                countriesListSuggestion.classList.add("show-notification")
                countriesListSuggestion.classList.remove("hide-notification")
            } else {
                countriesListSuggestion.classList.remove("show-notification")
                countriesListSuggestion.classList.add("hide-notification")
            }

        },
        error: function (error) {
            console.error('Error: ', error);
        }
    });
}

function showCountries(input) {
    let filteredSuggestions = countriesSuggestions.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions.sort((a, b) => a.localeCompare(b)).slice(0, 9);
    if (!input.trim()) {
        countriesListSuggestion.innerHTML = '';
        return;
    }
    countriesListSuggestion.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        let listItem = document.createElement('li');
        listItem.className = 'hint-listing hint-listing-country';
        let listButton = document.createElement('button');
        listButton.className = 'hint-element';
        listButton.type = 'button';
        listButton.value = suggestion;
        listButton.textContent = suggestion;
        listItem.appendChild(listButton);
        countriesListSuggestion.appendChild(listItem);

        listButton.addEventListener('click', () => {
            companyCountry.value = listButton.value;
            countryValue.value = (listButton.value);
            validateCountry();
        })
    });

}


function checkCompanyAvailability(company) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/company/availability',
            type: 'GET',
            data: {company: company},
            success: function (data) {
                if (!data) {
                    resolve(false);
                } else {
                    resolve(true);
                }
            },
            error: function (error) {
                console.error('Error: ', error);
                reject(error);
            }
        })
    })

}

