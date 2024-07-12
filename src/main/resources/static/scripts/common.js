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
    clearMobileSearch();
    hideMobileNav();
});
addEventListener('load', () => {
    clearSearch();
    clearMenu();
    clearMobileSearch();
    hideMobileNav();
});
function clearMobileSearch(){
    navBarSearchInputMobile.value = '';
    titleSuggestionsMobile = [];
}

function clearMenu() {
    menuButton.classList.remove("nav-open");
    hidedMenu.classList.add("hidden")
    body.classList.remove("nav-open");
}

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
        getGame(userData,navBarListBox,navBarBox,7)
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

function getGame(input,navBarListBox,navBarBox,size) {
    if (titleSuggestions.length > 1) {
        showGame(input,navBarListBox,navBarBox,size);
    } else {
        $.ajax({
            url: '/api/games/allGames',
            type: 'GET',
            success: function (data) {
                titleSuggestions = data;
                showGame(input,navBarListBox,navBarBox,size);
            },
            error: function (error) {
                console.error('Error: ', error);
            }
        });
    }
}


function showGame(input,navBarListBox,navBarBox,size) {
    let filteredSuggestions = titleSuggestions.filter(suggestion => suggestion.title.toLowerCase().trim()
        .includes(input.trim().toLowerCase()));
    filteredSuggestions = filteredSuggestions
        .sort((a, b) => a.title.localeCompare(b.title)).slice(0, size);

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

/*Search mobile menu/*/
const navBarSearchRowMobile = document.getElementById("nav-bar-search-row-mobile");
const navBarSearchInputMobile = document.getElementById("nav-bar-search-input-mobile");
const navBarBoxMobile = document.getElementById("nav-bar-box-mobile");
const navBarListBoxMobile = document.getElementById("nav-bar-list-box-mobile");

let navBarTimerMobile;
let titleSuggestionsMobile = [];

navBarSearchInputMobile.addEventListener('click', ()=>{
    clearMobileSearch();
});

navBarSearchInputMobile.onkeyup = (e) => {
    let userData = e.target.value;
    clearTimeout(navBarTimerMobile);
    navBarTimerMobile = setTimeout(function () {
        if (navBarBoxMobile.classList.contains("company-result-box-hide")) {
            navBarBoxMobile.classList.remove("company-result-box-hide");
        }
        if (userData.length < 1) {
            navBarBoxMobile.classList.add("company-result-box-hide");
        }
        getGame(userData,navBarListBoxMobile,navBarBoxMobile,10)
    }, 450)
};

navBarSearchInputMobile.onchange = (e) => {
    if (!navBarBoxMobile.classList.contains("company-result-box-hide")) {
        document.addEventListener("click", function (ev) {
            if (!navBarSearchRowMobile.contains(ev.target)) {
                navBarSearchInputMobile.value = '';
                navBarListBoxMobile.innerHTML = '';
                navBarBoxMobile.classList.add("company-result-box-hide");
            } else if (navBarSearchInputMobile.value !== '') {
                navBarSearchInputMobile.value = '';
                navBarListBoxMobile.innerHTML = '';
                navBarBoxMobile.classList.add("company-result-box-hide");
            }
        });
    }
}








/*Display hided navbar*/
const hidedMenu = document.getElementById("extend-nav-bar");
const menuButton = document.getElementById("menu-button");
const body = document.body;
const subNavbar = document.getElementById("sub-navbar");
menuButton.addEventListener('click', () => {
    if (menuButton.classList.contains("nav-open")) {
        menuButton.classList.remove("nav-open");
        hidedMenu.classList.add("hidden");
        if (subNavbar) {
            subNavbar.classList.remove("hidden");
        }

    } else {
        menuButton.classList.add("nav-open");
        hidedMenu.classList.remove("hidden")
        if (subNavbar) {
            subNavbar.classList.add("hidden");
        }
    }
})

/*Show/hide mobile menu*/
let mobileCloseButton = document.getElementById('close-mobile');
let mobileMenuSticky = document.getElementById('mobile-menu-sticky');
let mobileShowButton = document.getElementById('mobile-show-button');
let mobileMenuContainer = document.getElementById('mobile-menu-container');

mobileCloseButton.addEventListener('click', () => {
    hideMobileNav();
})
mobileMenuSticky.addEventListener('click', () => {
    hideMobileNav();
});
mobileMenuContainer.addEventListener('click',ev=>{
    ev.stopPropagation();

});


if (mobileMenuContainer.classList.contains('hide-stats-container')) {
    mobileShowButton.addEventListener('click', () => {
        mobileMenuContainer.classList.remove("hide-stats-container");
        mobileMenuContainer.classList.add("mobile-nav-animation");
        mobileMenuSticky.classList.remove("hide-stats-container");
        mobileMenuSticky.classList.add("rate-opacity-animation");
        body.style.overflow='hidden';
    });
}
function hideMobileNav(){
    mobileMenuContainer.classList.add("hide-stats-container");
    mobileMenuSticky.classList.add("hide-stats-container");
    mobileMenuContainer.classList.remove("mobile-nav-animation");
    mobileMenuSticky.classList.remove("rate-opacity-animation");
    body.style.overflow=null;
}


function loadPage(pageNo) {
    $.ajax({
        url: '?page=' + pageNo,
        success: function (data) {
            setTimeout(function () {
                $('#game-list-container').html($(data).find('#game-list-container').html());
                window.scrollTo(0, $("#game-list-container").offset().top)
                window.history.pushState({page: pageNo}, '', '?page=' + pageNo)
                window.location.reload();
            }, 25)

        }
    });
}



