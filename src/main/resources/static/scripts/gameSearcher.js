/*Search*/
const categorySearchRow = document.getElementById("category-search-row");
const categorySearchInput = document.getElementById("category-search-input");
const categorySearchBox = document.getElementById("category-search-box");
const categorySearchList = document.getElementById("category-search-list");
const urlParams = new URLSearchParams(window.location.search);

let categorySearchTimer;
let categorySuggestions = [];

categorySearchInput.addEventListener('click', () => {
    categorySearchClear();
});

addEventListener('resize', () => {
    categorySearchClear();
});
addEventListener('load', () => {
    categorySearchClear();
});

function categorySearchClear() {
    categorySearchInput.value = '';
    categorySearchBox.classList.add("hidden");
}

categorySearchInput.onkeyup = (e) => {
    if (e.target.value.length >= 1) {
        let userData = e.target.value;
        clearTimeout(categorySearchTimer);
        categorySearchTimer = setTimeout(function () {
            if (categorySearchBox.classList.contains("hidden")) {
                categorySearchBox.classList.remove("hidden");
            }
            if (userData.length < 1) {
                categorySearchBox.classList.add("hidden");
            }
            getCategories(userData, navBarListBox, navBarBox, 7)
        }, 450)
    } else if (e.target.value.length === 0) {
        categorySearchClear();
    }
};
categorySearchInput.onchange = (e) => {
    if (!categorySearchBox.classList.contains("hidden")) {
        document.addEventListener("click", function (ev) {
            if (!categorySearchRow.contains(ev.target)) {
                categorySearchInput.value = '';
                categorySearchList.innerHTML = '';
                categorySearchBox.classList.add("hidden");
            } else if (categorySearchInput.value !== '') {
                categorySearchInput.value = '';
                categorySearchList.innerHTML = '';
                categorySearchBox.classList.add("hidden");
            }
        });
    }
}

function getCategories(input, navBarListBox, navBarBox, size) {
    if (categorySuggestions.length > 1) {
        showCategoriesHints(input, navBarListBox, navBarBox, size);
    } else {
        $.ajax({
            url: '/api/category/allCategories',
            type: 'GET',
            data: {
                category: urlParams.get('cat') ? urlParams.get('cat').split(',') : []
            },
            success: function (data) {
                categorySuggestions = data;
                showCategoriesHints(input, navBarListBox, navBarBox, size);
            },
            error: function (error) {
                console.error('Error: ', error);
            }
        });
    }
}

function showCategoriesHints(input, navBarListBox, navBarBox, size) {
    let categories = urlParams.get('cat') ? urlParams.get('cat').split(',') : [];
    if (categories.length < 10) {

        let filteredSuggestions = categorySuggestions.filter(suggestion => suggestion.toLowerCase().trim()
            .includes(input.trim().toLowerCase()));
        filteredSuggestions = filteredSuggestions
            .sort((a, b) => a.localeCompare(b)).slice(0, size).filter(sug => !categories.includes(sug));

        if (!input.trim()) {
            categorySearchList.innerHTML = '';
            return;
        }
        if (filteredSuggestions.length < 1) {
            categorySearchBox.classList.add("hidden")
        }
        categorySearchList.innerHTML = '';

        function highlightMatch(text, query) {
            const regex = new RegExp(`(${query})`, 'gi');
            return text.replace(regex, '<span class="search-color">$1</span>');
        }

        filteredSuggestions.forEach(suggestion => {
            let listLink = document.createElement('a');
            listLink.innerHTML = highlightMatch(suggestion, input);
            categorySearchList.appendChild(listLink);

            listLink.onclick = () => {
                if (!categories.includes(suggestion)) {
                    categories.push(suggestion);
                }
                urlParams.delete('page');
                urlParams.set('cat', categories.join(','));
                window.location.href = `${window.location.pathname}?${decodeURIComponent(urlParams.toString())}`;
            };
        });
    } else {
        categorySearchList.innerHTML = 'Limit kategorii to 10';
        categorySearchList.style.color = '#1f1f1f';
    }
}


//REMOVE PLATFORM
let chosenPlatformBox = document.getElementById('game-search-chosen-platform-container');
let gameCategory = document.getElementsByClassName('game-search-category');
let gameCategoryButton = document.getElementsByClassName('game-category-remove-button');
for (let gameCategoryButtonElement of gameCategoryButton) {
    gameCategoryButtonElement.addEventListener('click', (ev) => {
        const categoryText = ev.currentTarget.querySelector('.game-search-category').textContent;
        removeCategory(categoryText);
    })
}

function removeCategory(category) {
    const urlParams = new URLSearchParams(window.location.search);
    let categories = urlParams.get('cat');

    if (categories) {
        categories = categories.split(',').filter(cat => cat !== category);
        if (categories.length > 0) {
            urlParams.set('cat', categories.join(','));
        } else {
            urlParams.delete('cat');

        }
    }
    let newUrl;
    if (categories.length <= 0) {
        newUrl = `${window.location.pathname}`;
    } else {
        newUrl = `${window.location.pathname}?${decodeURIComponent(urlParams.toString())}`;
    }
    window.location.href = newUrl;
}