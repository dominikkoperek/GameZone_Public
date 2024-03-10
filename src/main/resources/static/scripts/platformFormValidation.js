let platformName = document.getElementById("platformName");
let platformListSuggestion = document.getElementById("show-suggestion-list");
let platformDescription = document.getElementById("platformDescription");
let platformLogoAddress = document.getElementById("platformLogoAddress");
let platformNameError = document.getElementById("name-error");
let platformDescriptionError = document.getElementById("description-error");
let platformLogoAddressError = document.getElementById("logoAddress-error");
let formErrorNotification = document.querySelector(".notification-error");
let form = document.getElementById("add-content-form");
let svgFile = document.getElementById("svg-file");
let fileErrorMessage = document.getElementById("file-error-message");
let formButton = document.getElementById("form-button");
let userSvgUpload = document.getElementById("user-svg-upload");
let userSvgName = document.getElementById("user-svg-name");
let testSvgLogo = document.getElementById("test-svg-logo");
let copySvg = document.querySelectorAll('.copy-svg');
let copySvgContainer = document.querySelectorAll('.platform-element');

let timerPlatformSuggestions;
let platformSuggestions = [];

formButton.addEventListener("click", function (ev) {
    ev.preventDefault();

})
copySvgContainer.forEach(copySvgContainer => {
    copySvgContainer.addEventListener("click", function (ev) {
        let clickedElement = ev.currentTarget;
        let svgElement = clickedElement.querySelector('.copy-svg svg');
        if (svgElement) {
            platformLogoAddress.value = svgElement.outerHTML;
            platformLogoAddress.innerHTML = svgElement.outerHTML;
            validatePlatformLogoAddress();
        }
    })
})


const validatePlatformName = async () => {
    let platformNameValue = platformName.value.trim();

    if (platformNameValue !== '') {
        userSvgName.innerHTML = platformName.value;
    } else {
        userSvgName.innerHTML = 'Test';
    }
    if (platformNameValue !== '' && platformLogoAddress.value !== '') {
        validatePlatformLogoAddress();
    }

    if (platformNameValue === '') {
        platformName.classList.add('error-input');
        formErrorNotification.innerHTML = 'Nazwa nie może być pusta!';
        return false;
    }
    if (platformNameValue.length < 2) {
        platformName.classList.add('error-input');
        formErrorNotification.innerHTML = 'Nazwa musi mieć przynajmniej 2 znaki';
        return false;
    }
    if (platformNameValue.length > 10) {
        platformName.classList.add('error-input');
        formErrorNotification.innerHTML = 'Nazwa może mieć maksymalnie 10 znaków';
        return false;
    }
    let isPlatformAvailable = await checkPlatformAvailability(platformNameValue);
    if (!isPlatformAvailable) {
        platformName.classList.add('error-input');
        formErrorNotification.innerHTML = 'Platforma jest już zajęta!';
        return false;
    }
    formErrorNotification.innerHTML = '';
    platformName.classList.remove('error-input');
    return true;
}
const validatePlatformDescription = () => {
    let platformDescriptionValue = platformDescription.value.trim();
    if (platformDescriptionValue === '') {
        platformDescription.classList.add('error-input');
        platformDescriptionError.innerHTML = 'Opis nie może być pusty'
        return false;
    }
    if (platformDescriptionValue.length < 50) {
        platformDescription.classList.add('error-input');
        platformDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 50 znaków';
        return false;
    }
    if (platformDescriptionValue.length > 800) {
        platformDescription.classList.add('error-input');
        platformDescriptionError.innerHTML = 'Opis może mieć maksymalnie 800 znaków';
        return false;
    }

    platformDescription.classList.remove('error-input');
    platformDescriptionError.innerHTML = '';
    return true;
}
const validatePlatformLogoAddress = () => {
    let platformLogoAddressValue = platformLogoAddress.value.trim();
    if (platformLogoAddressValue === '') {
        platformLogoAddress.value = '';
        userSvgUpload.innerHTML = '';
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Kod svg nie może być pusty!';
        return false;
    }
    if (platformLogoAddressValue.length < 100) {
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Kod svg musi mieć przynajmniej 100 znaków';
        return false;
    }
    if (platformLogoAddressValue.length > 30000) {
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Kod svg może mieć maksymalnie 30.000 znaków';
        return false;
    }
    if (!platformLogoAddressValue.startsWith('<svg class="platform-logo"')) {
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Kod svg musi się zaczynać od &lt;svg class="platform-logo" ';
        return false;
    }
    if (!platformLogoAddressValue.endsWith('</svg>')) {
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Kod svg musi się kończyć na &lt/svg&gt';
        return false;
    }
    if (platformLogoAddressValue.includes('<script>') || platformLogoAddressValue.includes('script')) {
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Kod zawiera niedozwolone frazy! &ltscript&gt';
        return false;
    }
    platformLogoAddress.value = platformLogoAddressValue;
    userSvgUpload.innerHTML = platformLogoAddressValue;

    if (testSvgLogo.offsetWidth > 100) {
        platformLogoAddress.classList.add('error-input');
        platformLogoAddressError.innerHTML = 'Logo jest za duże!';
        return false;
    }


    platformLogoAddress.classList.remove('error-input');
    platformLogoAddressError.innerHTML = '';
    return true
}

async function checkIsFormValid() {
    let platformName = await validatePlatformName();
    let platformDescription = validatePlatformDescription();
    let platformLogoAddress = validatePlatformLogoAddress();
    return platformName && platformDescription && platformLogoAddress;
}

function validatePlatformForm() {
    checkIsFormValid().then(result => {
        if (result) {
            form.submit();
        } else {
            platformNameError.classList.add("button-error");
            platformDescriptionError.classList.add("button-error");
            platformLogoAddressError.classList.add("button-error");
            formButton.classList.add("button-error");

        }
        setTimeout(function () {
            platformNameError.classList.remove("button-error");
            platformDescriptionError.classList.remove("button-error");
            platformLogoAddressError.classList.remove("button-error");
            formButton.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji', error)
    })
}


function addFile() {
    let file = svgFile.files[0];

    if (file) {
        let reader = new FileReader();
        reader.onload = function (e) {
            let fileNameExtension = file.name.split('.').pop();
            if (fileNameExtension === 'svg') {
                let contents = e.target.result;
                let svgStart = contents.indexOf('<svg');
                contents = contents.substring(svgStart);
                contents = contents.replace(/(<\/svg>)[^>]*$/, '$1');
                contents = contents.replace(/<svg/, '<svg class="platform-logo"');
                platformLogoAddress.value = contents;
                userSvgUpload.innerHTML = contents;
                validatePlatformLogoAddress();
            } else {
                fileErrorMessage.innerHTML = 'Obsługiwany typ to SVG';
                setTimeout(() => {
                    fileErrorMessage.innerHTML = '';
                    svgFile.value = '';
                }, 2500);
            }

        };
        reader.readAsText(file);
    }
}


platformName.addEventListener('keyup', function () {
    clearTimeout(timerPlatformSuggestions);
    timerPlatformSuggestions = setTimeout(function () {
        getPlatformSuggestion(platformName.value);
    }, 450)
});

function getPlatformSuggestion(input) {
    $.ajax({
        url: '/api/platform/allPlatforms',
        type: 'GET',
        success: function (data) {
            platformSuggestions = data;
            showSuggestions(input);
            if (platformListSuggestion.children.length > 0 && platformName.value !== '') {
                platformListSuggestion.classList.add("show-notification")
                platformListSuggestion.classList.remove("hide-notification")
            } else {
                platformListSuggestion.classList.remove("show-notification")
                platformListSuggestion.classList.add("hide-notification")
            }

        },
        error: function (error) {
            console.error('Wystąpił błąd:', error);
        }
    });
}

function showSuggestions(input) {
    let filteredSuggestions = platformSuggestions.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));

    if (!input) {
        platformListSuggestion.innerHTML = '';
        return;
    }
    platformListSuggestion.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        platformListSuggestion.innerHTML += '<li class="hint-listing">' + suggestion + '</li>';
    });
}


function checkPlatformAvailability(platform) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/platform/availability',
            type: 'GET',
            data: {platform: platform},
            success: function (data) {
                if (!data) {
                    resolve(false);
                } else {
                    resolve(true);
                }
            },
            error: function (error) {
                console.error('Wystąpił błąd:', error);
                reject(error);
            }
        })
    })
}

const clearPlatformForm = () => {
    form.reset();
    platformName.classList.remove('error-input');
    platformNameError.innerHTML = '';
    platformListSuggestion.innerHTML = '';
    platformDescription.classList.remove('error-input');
    platformDescriptionError.innerHTML = '';
    platformLogoAddress.classList.remove('error-input');
    platformLogoAddressError.innerHTML = '';
    platformLogoAddress.value = '';
    svgFile.value = '';
    userSvgUpload.innerHTML = '';
    userSvgUpload.value = '';
    userSvgName.innerHTML = 'Test';
}