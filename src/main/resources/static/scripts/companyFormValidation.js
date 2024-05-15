let companyListSuggestion = document.getElementById("show-suggestion-list");
let timerCompanySuggestions;
let companySuggestions = [];


//VALIDATE COMPANY NAME
const companyName = document.getElementById("company-name");
const companyNameError = document.getElementById("name-error");

validateCompanyName = async () => {
    const companyNameValue = companyName.value.trim();
    if (companyNameValue === '') {
        companyName.classList.add('error-input');
        companyNameError.innerHTML = 'Nazwa nie może być pusta!';
        return false;
    }
    if (companyNameValue.length < 3) {
        companyName.classList.add('error-input');
        companyNameError.innerHTML = 'Nazwa musi mieć przynajmniej 3 znaki';
        return false;
    }
    if (companyNameValue.length > 35) {
        companyName.classList.add('error-input');
        companyNameError.innerHTML = 'Nazwa może mieć maksymalnie 35 znaków';
        return false;
    }
    let isCompanyAvailable = await checkCompanyAvailability(companyNameValue);

    if (!isCompanyAvailable) {
        companyName.classList.add('error-input');
        companyNameError.innerHTML = 'Firma już istnieje';
        return false;
    }
    companyName.classList.remove('error-input');
    companyNameError.innerHTML = '';
    return true;
}
//SHOW USED NAMES FOR COMPANY

companyName.addEventListener('keyup', function () {
    clearTimeout(timerCompanySuggestions);
    timerCompanySuggestions = setTimeout(function () {
        getCompanySuggestion(companyName.value);
    }, 450)
});

//VALIDATE COUNTRY
const companyCountry = document.getElementById("company-country");
const companyCountryError = document.getElementById("country-error");
const validateCountry = () => {
    let countryValueTrim = companyCountry.value.trim();
    if (countryValueTrim === '') {
        companyCountry.classList.add('error-input');
        companyCountryError.innerHTML = 'Kraj nie może być pusty'
        return false;
    }
    if (countryValueTrim.length < 4) {
        companyCountry.classList.add('error-input');
        companyCountryError.innerHTML = 'Kraj musi mieć przynajmniej 4 znaki'
        return false;
    }
    if (countryValueTrim.length > 33) {
        companyCountry.classList.add('error-input');
        companyCountryError.innerHTML = 'Kraj może mieć maksymalnie 33 znaki'
        return false;
    }
    companyCountryError.innerHTML = '';
    companyCountry.classList.remove('error-input');
    return true;
}
//VALIDATE COMPANY SHORT DESCRIPTION
const companyShortDescription = document.getElementById("company-short-description");
const companyShortDescriptionError = document.getElementById("short-description-error");
const validateCompanyShortDescription = () => {
    let companyShortDescriptionValue = companyShortDescription.value.trim();

    if (companyShortDescriptionValue === '') {
        companyShortDescription.classList.add('error-input');
        companyShortDescriptionError.innerHTML = 'Opis nie może być pusty'
        return false;
    }
    if (companyShortDescriptionValue.length < 100) {
        companyShortDescription.classList.add('error-input');
        companyShortDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 100 znaków'
        return false;
    }
    if (companyShortDescriptionValue.length > 1000) {
        companyShortDescription.classList.add('error-input');
        companyShortDescriptionError.innerHTML = 'Opis może mieć maksymalnie 1000 znaków'
        return false;
    }
    companyShortDescriptionError.innerHTML = '';
    companyShortDescription.classList.remove('error-input');
    return true;
}
//VALIDATE LONG DESCRIPTION
let companyLongDescription = document.getElementById("company-long-description");
let companyLongDescriptionError = document.getElementById("long-description-error");
const validateCompanyLongDescription = () => {
    let companyLongDescriptionValue;
    try {
        companyLongDescriptionValue = tinymce.get("companyLongDescription").getContent();
    } catch (e) {
        if (e instanceof ReferenceError) {
            companyLongDescriptionValue = companyLongDescription.value.trim();
        }

    }

    if (companyLongDescriptionValue === '') {
        companyLongDescription.classList.add('error-input');
        companyLongDescriptionError.innerHTML = 'Opis nie może być pusty'
        return false;
    }
    if (companyLongDescriptionValue.length < 200) {
        companyLongDescription.classList.add('error-input');
        companyLongDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 200 znaków'
        return false;
    }

    if (companyLongDescriptionValue.length > 105_000) {
        companyLongDescription.classList.add('error-input');
        companyLongDescriptionError.innerHTML = 'Opis może mieć maksymalnie 105000 znaków'
        return false;
    }
    if (!(companyLongDescriptionValue.includes('<h2>') && companyLongDescriptionValue.includes('</h2>'))) {
        companyLongDescription.classList.add('error-input');
        companyLongDescriptionError.innerHTML = "Opis musi zawierać przynajmniej 1 nagłówek &lt;h2&gt; &lt;/h2&gt; ";
        return false;
    }
    if (companyLongDescriptionValue.includes('<h1>') || companyLongDescriptionValue.includes('<h3>') ||
        companyLongDescriptionValue.includes('<h4>') || companyLongDescriptionValue.includes('<h5>') ||
        companyLongDescriptionValue.includes('<h6>')) {
        companyLongDescription.classList.add('error-input');
        companyLongDescriptionError.innerHTML = "Opis może zawierac tylko nagłówki &lt;h2&gt; ";
        return false;
    }
    if (companyLongDescriptionValue.includes("<script>") ||
        companyLongDescriptionValue.includes("<iframe>")) {
        companyLongDescription.classList.add('error-input');
        companyLongDescriptionError.innerHTML = 'Opis zawiera niedozwolone frazy';
        return false;
    }

    companyLongDescriptionError.innerHTML = '';
    companyLongDescription.classList.remove('error-input');
    return true;
}
//VALIDATE COMPANY POSTER

const companyPoster = document.getElementById("poster");
const companyPosterError = document.getElementById("company-poster-error");
const companyImageProportionHeight = document.getElementById("company-image-proportion-height");
const companyImageProportionWidth = document.getElementById("company-image-proportion-width");
const validateCompanyPoster = async () => {
    companyImageProportionHeight.innerHTML = "";
    companyImageProportionWidth.innerHTML = "";
    let size;
    let extension;
    let width;
    let height;
    if (companyPoster.value !== "") {
        size = ((companyPoster.files[0].size / 1024) / 1024).toString().slice(0, 4);
        extension = companyPoster.files[0].type;

        const img = new Image();
        img.src = window.URL.createObjectURL(companyPoster.files[0]);
        await new Promise((resolve) => {
            img.onload = () => {
                width = img.width;
                height = img.height;
                resolve();
            };
        });
    }
    let proportionHeightWidth = height / width;
    let slicedProportionHeightWidth = proportionHeightWidth.toString().slice(0, 4);
    let proportionWidthHeight = width / height;
    let slicedProportionWidthHeight = proportionWidthHeight.toString().slice(0, 4);

    if (companyPoster.value === "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Dodaj obraz";
        removeUpload();
        return false;
    }
    if (width > 1200 && companyPoster.value !== "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Szerokość obrazu jest za duża " + width + "px"+" (max 1200px)";
        return false;
    }
    if (width < 300 && companyPoster.value !== "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Szerokość obrazu jest za mała " + width + "px" +" (min 300px)";
        return false;
    }
    if (height > 1200 && companyPoster.value !== "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Wysokość obrazu jest za duża " + height + "px"+" (max 1200px)";
        return false;
    }
    if (height < 300 && companyPoster.value !== "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Wysokość obrazu jest za mała " + height + "px"+" (min 300px)";
        return false;
    }


    if (proportionHeightWidth < 0.9 && proportionHeightWidth < 1.1) {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Proporcje wysokośc/szerokość powinna być 0.9-1.1 jest " + slicedProportionHeightWidth;
        return false;
    }
    if (proportionWidthHeight < 0.9 && proportionWidthHeight < 1.1) {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Proporcje szerokośc/wysokość powinna być 0.9-1.1 jest " + slicedProportionWidthHeight;
        return false;
    }

    if (size > 1 && companyPoster.value !== "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Obraz jest za duży " + size + "Mb";
        return false;
    }
    if (extension !== "image/jpeg" && extension !== "image/png" && extension !== "image/jpg" && companyPoster.value !== "") {
        companyPoster.classList.add("error-input");
        companyPosterError.innerHTML = "Nieobsługiwany format pliku!";
        removeUpload();
        return false;
    }

    companyImageProportionHeight.innerHTML = "wysokość/szerokość = " + slicedProportionHeightWidth;
    companyImageProportionWidth.innerHTML = "szerokość/wysokość = " + slicedProportionWidthHeight;
    companyPoster.classList.remove("error-input");
    companyPosterError.innerHTML = "";
    return true;
}

//VALIDATE IS PRODUCER AND IS PUBLISHER
const isProducer = document.getElementById("is-producer");
const isPublisher = document.getElementById("is-publisher");
const isCompanyError = document.getElementById("is-company-error")

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

//DISABLE DEFAULT FORM BEFORE VALIDATION
const companyForm = document.getElementById("add-content-form");
const formButton = document.getElementById("form-button");
companyForm.addEventListener("submit", function (ev) {
    ev.preventDefault();
})

//VALIDATE EACH FIELD IN FORM BEFORE SEND
async function checkIsFormValid() {
    let companyName = await validateCompanyName();
    let companyShortDescription = validateCompanyShortDescription();
    let companyLongDescription = validateCompanyLongDescription();
    let country = validateCountry();
    let isProducerOrPublisher = validateIsProducerOrIsPublisher();
    let isPosterValid = validateCompanyPoster();

    return companyName && companyShortDescription && companyLongDescription
        && country && isProducerOrPublisher && isPosterValid;
}

//VALIDATE COMPANY FORM AND SEND IT OR SHOW ERROR
function validateCompanyForm() {
    checkIsFormValid().then(result => {
        if (result) {
            companyForm.submit();
        } else {
            companyNameError.classList.add("button-error");
            companyShortDescriptionError.classList.add("button-error");
            companyLongDescriptionError.classList.add("button-error");
            formButton.classList.add("button-error");
            companyCountryError.classList.add("button-error");
            isCompanyError.classList.add("button-error");
            companyPosterError.classList.add("button-error");
        }
        setTimeout(function () {
            formButton.classList.remove("button-error");
            companyNameError.classList.remove("button-error");
            companyShortDescriptionError.classList.remove("button-error");
            companyLongDescriptionError.classList.remove("button-error");
            companyCountryError.classList.remove("button-error");
            isCompanyError.classList.remove("button-error");
            companyPosterError.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji ', error)
    })
}


function getCompanySuggestion(input) {
    if (companySuggestions.length > 1) {
        showSuggestions(input);
    } else {
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
}

function showSuggestions(input) {
    let filteredSuggestions = companySuggestions.filter(suggestion => suggestion.name.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions.sort((a, b) => a.name.localeCompare(b.name)).slice(0, 8);
    if (!input.trim()) {
        companyListSuggestion.innerHTML = '';
        return;
    }
    companyListSuggestion.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        let listItem = document.createElement('li');
        listItem.className = 'hint-listing';
        listItem.textContent = suggestion.name;
        companyListSuggestion.appendChild(listItem);
    });
}


function checkCompanyAvailability(name) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/company/availability',
            type: 'GET',
            data: {name: name},
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

//CLEAR FORM BUTTON
function clearCompanyForm() {
    companyForm.reset();
    companyName.classList.remove('error-input');
    companyNameError.innerHTML = '';
    companyCountry.classList.remove("error-input");
    companyCountryError.innerHTML = '';
    companyShortDescription.classList.remove('error-input');
    companyShortDescriptionError.innerHTML = '';


    companyLongDescription.classList.remove('error-input');
    isProducer.classList.remove('error-input');
    isPublisher.classList.remove('error-input');
    companyPoster.classList.remove("error-input");

    companyPosterError.innerHTML = '';
    isCompanyError.innerHTML = '';
    companyLongDescriptionError.innerHTML = '';
    companyListSuggestion.innerHTML = '';
    removeUpload();

}

//POSTER UPLOAD/DELETE
function previewCompanyPoster(id) {
    document.querySelector("#" + id).addEventListener("change", function (e) {
        let file = e.target.files[0];
        if (file != null) {
            let url = URL.createObjectURL(file);
            if (file.type === "image/jpeg" || file.type === "image/png" || file.type === "image/jpg") {
                document.querySelector("#" + id + "-preview div").innerText = file.name;
                document.querySelector("#" + id + "-preview img").src = url;
            }
        }
    });
}

function removeUpload(id = "poster") {
    document.querySelector("#" + id + "-preview div").innerHTML = "<span>+</span>";
    document.querySelector("#" + id + "-preview img").src = '/img/notfound.jpg';
}

companyPoster.addEventListener("input", function () {
    previewCompanyPoster("poster");
})

