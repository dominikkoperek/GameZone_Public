
let categoryListError = document.getElementById("notification-bar-categories");
let timerShowSuggestions;
let categorySuggestions = [];


//VALIDATE CATEGORY NAME
let categoryName = document.getElementById("category-name");
const categoryNameError = document.getElementById("category-name-error");
const validateCategoryName = async () => {

    let format = /[!@#$%^&*()_+=\[\]{};':"\\|,.<>\/?~]/;
    let categoryNameValue = categoryName.value.trim();

    if (categoryNameValue === '') {
        categoryName.classList.add('error-input');
        categoryNameError.innerHTML = 'Nazwa nie może być pusta!';
        return false;
    }
    if (categoryNameValue.length < 3) {
        categoryName.classList.add('error-input');
        categoryNameError.innerHTML = 'Nazwa musi mieć przynajmniej 3 znaki';
        return false;
    }
    if (categoryNameValue.length > 20) {
        categoryName.classList.add('error-input');
        categoryNameError.innerHTML = 'Nazwa może mieć maksymalnie 20 znaków';
        return false;
    }
    if (format.test(categoryNameValue)) {
        categoryName.classList.add('error-input')
        categoryNameError.innerHTML = "Nazwa nie może zawierac znakow !@#$%^&*()_+=[]{};:'\"\\\\|,.<>/?~";
        return false;
    }

    let isCategoryAvailable = await checkCategoryAvailability(categoryNameValue);
    if (!isCategoryAvailable) {
        categoryName.classList.add('error-input');
        categoryNameError.innerHTML = 'Kategoria jest już zajęta!';
        return false;
    }
    categoryNameError.innerHTML = '';
    categoryName.classList.remove('error-input')
    return true;
}
// SHOW CATEGORY NAME SUGGESTIONS
categoryName.addEventListener('keyup', function (env) {
    clearTimeout(timerShowSuggestions);
    timerShowSuggestions = setTimeout(function () {
        getSuggestions(categoryName.value);
    }, 450)
})
//CLEAR CATEGORY FORM BUTTON
const clearCategoryForm = () => {
    categoryForm.reset();
    categoryName.classList.remove('error-input');
    categoryDescription.classList.remove('error-input');
    categoryDescription.classList.remove('error-input');
    categoryListError.innerHTML = '';
    categoryNameError.innerHTML = '';
    categoryDescriptionError.innerHTML = '';
}

//VALIDATE CATEGORY DESCRIPTION
const categoryDescription = document.getElementById("categoryShortDescription");
const categoryDescriptionError = document.getElementById("category-description-error");

const validateCategoryDescription = () => {
    let categoryDescriptionValue = categoryDescription.value.trim();

    if (categoryDescriptionValue === '') {
        categoryDescription.classList.add('error-input');
        categoryDescriptionError.innerHTML = 'Opis nie może być pusty'
        return false;
    }
    if (categoryDescriptionValue.length < 50) {
        categoryDescription.classList.add('error-input');
        categoryDescriptionError.innerHTML = 'Opis musi mieć przynajmniej 50 znaków'
        return false;
    }
    if (categoryDescriptionValue.length > 800) {
        categoryDescription.classList.add('error-input');
        categoryDescriptionError.innerHTML = 'Opis może mieć maksymalnie 800 znaków!'
        return false;
    }
    categoryDescriptionError.innerHTML = '';
    categoryDescription.classList.remove('error-input');
    return true;
};

async function checkFromIsGood() {
    let categoryName = await validateCategoryName();
    let descriptionName = validateCategoryDescription();

    return categoryName && descriptionName;
}
//DISABLE FORM BEFORE VALIDATION
const categoryForm = document.getElementById("add-content-form");
const categoryFormButton = document.getElementById("form-button");
categoryForm.addEventListener("submit", function (ev) {
    ev.preventDefault();
})

function validateCategoryFrom() {
    checkFromIsGood().then(result => {
        if (result) {
            categoryForm.submit();
        } else {
            categoryNameError.classList.add("button-error");
            categoryDescriptionError.classList.add("button-error");
            categoryFormButton.classList.add("button-error");
        }
        setTimeout(function () {
            categoryNameError.classList.remove("button-error");
            categoryDescriptionError.classList.remove("button-error");
            categoryFormButton.classList.remove("button-error");
        }, 500)
    }).catch(error => {
        console.error('Wystąpił błąd podczas walidacji nazwy kategorii:', error);
    });
}

function checkCategoryAvailability(category) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/category/availability',
            type: 'GET',
            data: {category: category},
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
        });
    });
}


function getSuggestions(input) {
    $.ajax({
        url: '/api/category/allCategories',
        type: 'GET',
        success: function (data) {
            categorySuggestions = data;
            showSuggestions(input);
            if (categoryListError.children.length > 0 && categoryName.value !== '') {
                categoryListError.classList.remove("hide-notification")
                categoryListError.classList.add("show-notification")
            }else {
                categoryListError.classList.remove("show-notification");
                categoryListError.classList.add("hide-notification");
            }

        },
        error: function (error) {
            console.error('Wystąpił błąd:', error);
        }
    });
}

function showSuggestions(input) {
    let filteredSuggestions = categorySuggestions.filter(suggestion => suggestion.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions.sort((a, b) => a.localeCompare(b));
    if (!input) {
        categoryListError.innerHTML = '';
        return;
    }
    categoryListError.innerHTML = '';
    filteredSuggestions.forEach(suggestion => {
        categoryListError.innerHTML += '<li class="hint-listing">' + suggestion + '</li>';
    });
}
