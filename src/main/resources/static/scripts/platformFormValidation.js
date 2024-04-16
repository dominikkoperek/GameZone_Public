let timerPlatformSuggestions;
let platformSuggestions = [];

//DISABLE FORM BEFORE VALIDATION
const platformFormButton = document.getElementById("platform-form-button");
const platformForm = document.getElementById("add-content-form");
platformForm.addEventListener("submit", function (ev) {
    ev.preventDefault();
})

//VALIDATE PLATFORM NAME
const platformName = document.getElementById("platform-name");
const platformListSuggestion = document.getElementById("show-suggestion-list");
const platformNameError = document.getElementById("platform-name-error");
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
        platformNameError.innerHTML = 'Nazwa nie może być pusta!';
        return false;
    }
    if (platformNameValue.length < 2) {
        platformName.classList.add('error-input');
        platformNameError.innerHTML = 'Nazwa musi mieć przynajmniej 2 znaki';
        return false;
    }
    if (platformNameValue.length > 10) {
        platformName.classList.add('error-input');
        platformNameError.innerHTML = 'Nazwa może mieć maksymalnie 10 znaków';
        return false;
    }
    let isPlatformAvailable = await checkPlatformAvailability(platformNameValue);
    if (!isPlatformAvailable) {
        platformName.classList.add('error-input');
        platformNameError.innerHTML = 'Platforma jest już zajęta!';
        return false;
    }
    platformNameError.innerHTML = '';
    platformName.classList.remove('error-input');
    return true;
}
//VALIDATE PLATFORM DESCRIPTION
const platformDescription = document.getElementById("platform-description");
const platformDescriptionError = document.getElementById("platform-description-error");
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
    let reg = "<\\w+\\s*[^>]*>";
    if (platformDescriptionValue.match(reg)) {
        platformDescription.classList.add('error-input');
        platformDescriptionError.innerHTML = "Opis nie może zawierac tagów html!";
        return false;
    }
    if (platformDescriptionValue.includes("<script>") || platformDescriptionValue.includes("<iframe>")) {
        platformDescription.classList.add('error-input');
        platformDescriptionError.innerHTML = 'Opis zawiera niedozwolone frazy';
        return false;
    }

    platformDescription.classList.remove('error-input');
    platformDescriptionError.innerHTML = '';
    return true;
}
//VALIDATE SVG CODE
const svgFile = document.getElementById("svg-file");
const userSvgUpload = document.getElementById("user-svg-upload");
const userSvgName = document.getElementById("user-svg-name");
const testSvgLogo = document.getElementById("test-svg-logo");
const copySvgContainer = document.querySelectorAll('.platform-element');

const platformLogoAddress = document.getElementById("platform-logo-address");
const platformLogoAddressError = document.getElementById("logo-address-error");
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
    if (platformLogoAddressValue.includes('<script>') || platformLogoAddressValue.includes('script') ||
        platformLogoAddressValue.includes('<iframe>')) {
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

// COPY SVG IMAGE FROM SHOWED BY CLICK
copySvgContainer.forEach(copySvgContainer => {
    copySvgContainer.addEventListener("click", function (ev) {
        let clickedElement = ev.currentTarget;
        let svgElement = clickedElement.querySelector('.copy-svg svg');
        if (svgElement) {
            platformLogoAddress.value = svgElement.outerHTML;
            platformLogoAddress.innerHTML = svgElement.outerHTML;
            validatePlatformLogoAddress();
        }
    });
});

//VALIDATE ALL FORM INPUTS
async function checkIsFormValid() {
    let platformName = await validatePlatformName();
    let platformDescription = validatePlatformDescription();
    let platformLogoAddress = validatePlatformLogoAddress();
    return platformName && platformDescription && platformLogoAddress;
}

//VALIDATE INPUTS AND SEND FORM OR SHOW ERROR
function validatePlatformForm() {
    checkIsFormValid().then(result => {
        if (result) {
            platformForm.submit();
        } else {
            platformNameError.classList.add("button-error");
            platformDescriptionError.classList.add("button-error");
            platformLogoAddressError.classList.add("button-error");
            platformFormButton.classList.add("button-error");

        }
        setTimeout(function () {
            platformNameError.classList.remove("button-error");
            platformDescriptionError.classList.remove("button-error");
            platformLogoAddressError.classList.remove("button-error");
            platformFormButton.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji', error)
    })
}

//ADD SVG FILE
const fileErrorMessage = document.getElementById("fileErrorMessage");

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

// DISPLAY NAMES HINTS
platformName.addEventListener('keyup', function () {
    clearTimeout(timerPlatformSuggestions);
    timerPlatformSuggestions = setTimeout(function () {
        getPlatformSuggestion(platformName.value);
    }, 450)
});

function getPlatformSuggestion(input) {
    if (platformSuggestions.length > 1) {
        showSuggestions(input);
    } else {
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
}

function showSuggestions(input) {
    let filteredSuggestions = platformSuggestions.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase())).slice(0, 8);

    if (!input.trim()) {
        platformListSuggestion.innerHTML = '';
        return;
    }
    platformListSuggestion.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        let listItem = document.createElement("li");
        listItem.className = 'hint-listing hint-listing-platform';
        listItem.textContent = suggestion;
        platformListSuggestion.appendChild(listItem);
    });
}

//CHECK NAME AVAILABILITY
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

//CLEAR FORM
const clearPlatformForm = () => {
    platformForm.reset();
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