const notificationElement = document.querySelectorAll(".notification-bar");

function notificationBar() {
    notificationElement.forEach(function (notificationElement) {
        if (notificationElement) {
            setTimeout(function () {
                notificationElement.style.opacity = '0';
                notificationElement.style.transition = 'opacity 200ms';
            }, 7000);

            setTimeout(function () {
                notificationElement.style.height = '0';
                notificationElement.style.padding = '0';
                notificationElement.style.transition = 'all 200ms';
            }, 7050);
        }
    });
}

function showYear() {
    let currentYear = new Date().getFullYear();
    document.getElementById("copyright").innerHTML += " " + currentYear + " &copy; GameZone";
}

window.onload = function () {
    showYear();
    notificationBar();
};
//show password
let loginPasswordShowIcon = document.getElementById("login-show-password");
let loginPasswordHideIcon = document.getElementById("login-hide-password");
let loginPasswordInput = document.getElementById("login-password")
if (loginPasswordShowIcon) {


    loginPasswordShowIcon.addEventListener('click', () => {
        loginPasswordShowIcon.classList.add("hide-password-icon");
        loginPasswordHideIcon.classList.remove("hide-password-icon");
        if (loginPasswordInput.type === "password") {
            loginPasswordInput.type = "text";
        } else {
            loginPasswordInput.type = "password";
        }

    });
    loginPasswordHideIcon.addEventListener('click', () => {
        loginPasswordShowIcon.classList.remove("hide-password-icon");
        loginPasswordHideIcon.classList.add("hide-password-icon");
        if (loginPasswordInput.type === "text") {
            loginPasswordInput.type = "password";
        } else {
            loginPasswordInput.type = "text";
        }
    });
}
let mobileSearchButton = document.getElementById("mobile-search-input");
let mobileSearchBar = document.getElementById("mobile-search-bar-container")


mobileSearchButton.addEventListener('click', () => {
    if (mobileSearchBar.classList.contains("mobile-search-bar-container-hide")) {
        mobileSearchBar.classList.remove("mobile-search-bar-container-hide");
    } else {
        mobileSearchBar.classList.add("mobile-search-bar-container-hide");
    }
});
//NAVBAR SEARCH
const navBarSearchRow = document.getElementById("nav-bar-search-row");
const navBarSearchInput = document.getElementById("nav-bar-search-input");
const navBarBox = document.getElementById("nav-bar-box");
const navBarListBox = document.getElementById("nav-bar-list-box");

let navBarTimer;
let titleSuggestions = [];

navBarSearchInput.addEventListener('click', () => {
    clearSearch();
});
addEventListener('resize', () => {
    clearSearch();
    clearMenu();
});

function clearSearch() {
    navBarSearchInput.value = '';
    titleSuggestions = [];
    navBarBox.classList.add("company-result-box-hide");
}

navBarSearchInput.onkeyup = (e) => {
    let userData = e.target.value;
    clearTimeout(navBarTimer);
    navBarTimer = setTimeout(function () {
        if (navBarBox.classList.contains("company-result-box-hide")) {
            navBarBox.classList.remove("company-result-box-hide");
        }
        if (userData.length < 1) {
            navBarBox.classList.add("company-result-box-hide");
        }
        getGame(userData)
    }, 450)
};

navBarSearchInput.onchange = (e) => {
    if (!navBarBox.classList.contains("company-result-box-hide")) {
        document.addEventListener("click", function (ev) {
            if (!navBarSearchRow.contains(ev.target)) {
                navBarSearchInput.value = '';
                navBarListBox.innerHTML = '';
                navBarBox.classList.add("company-result-box-hide");
            } else if (navBarSearchInput.value !== '') {
                navBarSearchInput.value = '';
                navBarListBox.innerHTML = '';
                navBarBox.classList.add("company-result-box-hide");
            }
        });
    }
}

function getGame(input) {
    if (titleSuggestions.length > 1) {
        showGame(input);
    } else {
        $.ajax({
            url: '/api/games/allGames',
            type: 'GET',
            success: function (data) {
                titleSuggestions = data;
                showGame(input);
            },
            error: function (error) {
                console.error('Error: ', error);
            }
        });
    }
}


function showGame(input) {
    let filteredSuggestions = titleSuggestions.filter(suggestion => suggestion.title.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions
        .sort((a, b) => a.title.localeCompare(b.title)).slice(0, 7);

    if (!input.trim()) {
        navBarListBox.innerHTML = '';
        return;
    }
    if (filteredSuggestions.length < 1) {
        navBarBox.classList.add("company-result-box-hide")
    }
    navBarListBox.innerHTML = '';

    function highlightMatch(text, query) {
        const regex = new RegExp(`(${query})`, 'gi');
        return text.replace(regex, '<span class="search-color">$1</span>');
    }

    filteredSuggestions.forEach(suggestion => {
        let listLink = document.createElement('a');
        let gameTitle = suggestion.title.replaceAll(' ', '-');
        listLink.href = "/gry/" + gameTitle + "/" + suggestion.id;
        listLink.innerHTML = highlightMatch(suggestion.title, input);
        navBarListBox.appendChild(listLink);
    });
}

/*Display hided navbar*/
const hidedMenu = document.getElementById("extend-nav-bar");
const menuButton = document.getElementById("menu-button");
const body = document.body;
const subNavbar = document.getElementById("sub-navbar");
menuButton.addEventListener('click', () => {
    if(menuButton.classList.contains("nav-open")){
        menuButton.classList.remove("nav-open");
        hidedMenu.classList.add("hidden");
        if(subNavbar){
        subNavbar.classList.remove("hidden");
        }

    }else {
        menuButton.classList.add("nav-open");
        hidedMenu.classList.remove("hidden")
        if(subNavbar){
        subNavbar.classList.add("hidden");
        }
    }


})
function clearMenu() {
    menuButton.classList.remove("nav-open");
    hidedMenu.classList.add("hidden")
    body.classList.remove("nav-open");
}



